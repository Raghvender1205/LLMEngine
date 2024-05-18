from pydantic import BaseModel
from models.model_type import ModelType


class RAGRequest(BaseModel):
    model_type: ModelType
    query: str


class RAGResponse(BaseModel):
    response: str
