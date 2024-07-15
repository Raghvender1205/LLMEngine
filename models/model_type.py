from enum import Enum


class ModelType(str, Enum):
    mixtral = "mixtral-8x7b-32768"
    llama3 = "llama3"
    phi3 = "phi3"
    openai = "openai"
    gemma2b = "gemma2b"
    gemma7b = "gemma-7b"
    gemini = "gemini-pro"
