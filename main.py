import uvicorn
from fastapi import FastAPI, HTTPException, File, UploadFile, Form
from fastapi.middleware.cors import CORSMiddleware
from models.prompt import PromptRequest, PromptResponse
from models.rag import RAGRequest, RAGResponse
from handlers.model_type_handler import *
from rag.llm_setup import save_embeddings, prepare_prompt

app = FastAPI()

# Middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


def process_model_response(model_type: ModelType, prompt: str) -> str:
    if model_type == ModelType.llama3:
        return process_with_llama3(prompt)
    elif model_type == ModelType.phi3:
        return process_with_phi3(prompt)
    elif model_type == ModelType.gemini:
        return process_with_gemini(prompt)
    elif model_type == ModelType.gemma2b:
        return process_with_gemma2b(prompt)
    else:
        raise HTTPException(status_code=404, detail="Invalid Model Type")


@app.post("/api/v1/prompt", response_model=PromptResponse)
async def process_prompt(request: PromptRequest):
    if not request.prompt:
        raise HTTPException(status_code=404, detail="Prompt cannot be empty")

    model_type = request.model_type
    prompt = request.prompt

    # if model_type == ModelType.llama3:
    #     response = process_with_llama3(prompt)
    # elif model_type == ModelType.phi3:
    #     response = process_with_phi3(prompt)
    # elif model_type == ModelType.gemini:
    #     response = process_with_gemini(prompt)
    # elif model_type == ModelType.gemma2b:
    #     response = process_with_gemma2b(prompt)
    # else:
    #     raise HTTPException(status_code=404, detail="Invalid Model Type")
    response = process_model_response(model_type, prompt)

    return PromptResponse(response=response)


@app.post("/api/v1/rag", response_model=RAGResponse)
async def process_rag(
        model_type: ModelType = Form(...),
        query: str = Form(...),
        file: UploadFile = File(...)
):
    if not query:
        raise HTTPException(status_code=404, detail="Query cannot be empty")

    filepath = f"tmp/{file.filename}"
    os.makedirs("tmp", exist_ok=True)
    with open(filepath, "wb") as f:
        f.write(await file.read())
    file_type = file.filename.split(".")[-1]

    try:
        save_embeddings(filepath, file_type)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    finally:
        os.remove(filepath)

    try:
        prompt = prepare_prompt(query)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    response = process_model_response(model_type, prompt)

    return RAGResponse(response=response)


if __name__ == "__main__":
    uvicorn.run(app, host='0.0.0.0', port=8000)
