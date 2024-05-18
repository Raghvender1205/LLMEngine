import uvicorn
from fastapi import FastAPI, HTTPException
from models.prompt import PromptRequest, PromptResponse
from handlers.model_type_handler import *

app = FastAPI()


@app.post("/api/v1/prompt", response_model=PromptResponse)
async def process_prompt(request: PromptRequest):
    if not request.prompt:
        raise HTTPException(status_code=404, detail="Prompt cannot be empty")

    model_type = request.model_type
    prompt = request.prompt

    if model_type == ModelType.llama3:
        response = process_with_llama3(prompt)
    elif model_type == ModelType.phi3:
        response = process_with_phi3(prompt)
    elif model_type == ModelType.gemini:
        response = process_with_gemini(prompt)
    elif model_type == ModelType.gemma2b:
        response = process_with_gemma2b(prompt)
    else:
        raise HTTPException(status_code=404, detail="Invalid Model Type")

    return PromptResponse(response=response)

if __name__ == "__main__":
    uvicorn.run(app, host='0.0.0.0', port=8000)