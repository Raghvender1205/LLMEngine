from typing import Any, Dict, List
from models.model_type import ModelType
from langchain_community.chat_models import ChatOllama
from langchain_groq import ChatGroq
from langchain_core.messages import HumanMessage
from loguru import logger
from dotenv import load_dotenv
import google.generativeai as genai
import os

logger.add("../logs/debug_logs.log")

"""
Environment Setup
"""
load_dotenv()

GEMINI_API = os.getenv('GEMINI_API_KEY')
HF_TOKEN = os.getenv('HF_TOKEN')
GROQ_API_KEY = os.getenv("GROQ_API_KEY")


def process_with_mistral(prompt: str) -> str | List[str | Dict]:
    llm = ChatGroq(model=ModelType.mixtral, temperature=0, groq_api_key=GROQ_API_KEY)
    messages = [
        HumanMessage(
            content=prompt
        )
    ]
    response = llm.invoke(messages)

    return response.content


def process_with_phi3(prompt: str) -> str | List[str | Dict]:
    llm = ChatOllama(model=ModelType.phi3)
    messages = [
        HumanMessage(
            content=prompt
        )
    ]
    response = llm.invoke(messages)

    return response.content


def process_with_llama3(prompt: str) -> str | List[str | Dict]:
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


def process_with_gemma2b(prompt: str) -> str | List[str | Dict]:
    llm = ChatOllama(model="gemma:2b")
    messages = [
        HumanMessage(
            content=prompt
        )
    ]
    response = llm.invoke(messages)

    return response.content


def process_with_gemini(prompt: str) -> str:
    raise NotImplementedError
