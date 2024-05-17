from typing import Any, Dict, List
from models.model_type import ModelType
from langchain_community.chat_models import ChatOllama
from langchain_core.messages import HumanMessage
from dotenv import load_dotenv
import google.generativeai as genai
import os

load_dotenv('../.env')
GEMINI_API = os.getenv('GEMINI_API_KEY')


def process_with_mistral(prompt: str) -> str:
    raise NotImplementedError


def process_with_phi3(prompt: str) -> str | List[str | Dict]:
    llm = ChatOllama(model=ModelType.phi3)
    messages = [
        HumanMessage(
            content=prompt
        )
    ]
    response = llm.invoke(messages)

    return response.content


def process_with_llama3(prompt: str) -> str | list[str | dict]:
    llm = ChatOllama(model=ModelType.llama3)
    messages = [
        HumanMessage(
            content=prompt
        )
    ]
    response = llm.invoke(messages)

    return response.content


def process_with_openai(prompt: str) -> str:
    raise NotImplementedError


def process_with_gemma(prompt: str) -> str:
    raise NotImplementedError


def process_with_gemini(prompt: str) -> str:
    # genai.configure(api_key=GEMINI_API)
    # model = genai.GenerativeModel(ModelType.gemini)
    # response = model.generate_content(prompt)
    #
    # return response.text

    raise NotImplementedError