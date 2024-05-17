from enum import Enum


class ModelType(str, Enum):
    mistral = "mistral"
    llama3 = "llama3"
    phi3 = "phi3"
    openai = "openai"
    gemma = "gemma:2b"
    gemini = "gemini-pro"
