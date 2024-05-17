from pydantic import BaseModel
from typing import Any, Dict
from models.model_type import ModelType


class PromptRequest(BaseModel):
    prompt: str
    model_type: ModelType


class PromptResponse(BaseModel):
    response: str
