from enum import Enum


class ModelType(str, Enum):
    mistral = "mistral"
    llama3 = "llama3"
    phi3 = "phi3"
    openai = "openai"
    gemma2b = "gemma2b"
    gemma7b = "gemma-7b"
    gemini = "gemini-pro"
