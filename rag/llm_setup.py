from langchain.embeddings import HuggingFaceBgeEmbeddings
from langchain.vectorstores.chroma import Chroma
from langchain.prompts import ChatPromptTemplate
from rag.chunker import basic_chunker, read_document
from loguru import logger
import os
import shutil

CHROMA_PATH = "../chroma_db"


def save_embeddings(doc_path, doc_type):
    document = read_document(doc_path, doc_type)
    chunks = basic_chunker(document, doc_type)
    embedding_model = HuggingFaceBgeEmbeddings(
        model_name='BAAI/bge-small-en-v1.5',
        encode_kwargs={'normalize_embeddings': True}
    )

    if os.path.exists(CHROMA_PATH):
        shutil.rmtree(CHROMA_PATH)

    db = Chroma.from_documents(
        documents=chunks,
        embedding=embedding_model,
        persist_directory=CHROMA_PATH
    )
    db.persist()


def prepare_prompt(query: str) -> str:
    PROMPT_TEMPLATE = """
    Answer the question based only on the following context:
    {context}
     - -
    Answer the question based on the above context: {question}
    """

    embedding_model = HuggingFaceBgeEmbeddings(
        model_name='BAAI/bge-small-en-v1.5',
        encode_kwargs={'normalize_embeddings': True}
    )
    db = Chroma(persist_directory=CHROMA_PATH, embedding_function=embedding_model)

    # Retrieve context from db with similarity search
    results = db.similarity_search_with_relevance_scores(query, k=3)
    for i, (doc, score) in enumerate(results):
        logger.info(f"[Similarity Search] Result {i + 1}: Document: {doc.page_content[:100]}... Score: {score}")

    if len(results) == 0 or results[0][1] < 0.4:
        return "Unable to find matching results"

    # Prepare the final Prompt
    context_text = "\n\n - - \n\n".join([doc.page_content for doc, _score in results])
    prompt_template = ChatPromptTemplate.from_template(PROMPT_TEMPLATE)
    prompt = prompt_template.format(context=context_text, question=query)

    return prompt

# if __name__ == "__main__":
#     save_embeddings("../data/HKTestPdpPageWithOrderPlacement2.java", "java")
#     ans = prepare_prompt(query="What are the different methods")
#     print(ans)
