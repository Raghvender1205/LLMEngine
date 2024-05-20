from langchain.text_splitter import RecursiveCharacterTextSplitter, Language
from langchain.schema import Document
from typing import List
from loguru import logger


def read_document(filepath: str, file_type: str) -> List[Document]:
    """
    Reads a document
    :param filepath:
    :param file_type:
    :return:
    """
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    document = Document(page_content=content, metadata={'file_type': file_type, "file_path": filepath})

    return [document]


def basic_chunker(documents: List[Document], doct_type: str):
    """
    Chunker for Documents
    :param documents:
    :param doct_type:
    :return:
    """
    # TODO: Add support for other languages
    if doct_type == "java":
        text_splitter = RecursiveCharacterTextSplitter.from_language(
            language=Language.JAVA,
            chunk_size=1000,
            chunk_overlap=200
        )
    else:
        text_splitter = RecursiveCharacterTextSplitter(
            chunk_size=1000,
            chunk_overlap=200,
            length_function=len,
            add_start_index=True
        )

    chunks = text_splitter.split_documents(documents)
    document = chunks[0]
    logger.info(document.page_content)
    logger.info(document.metadata)

    return chunks


# file_path = "../data/HKTestPdpPageWithOrderPlacement2.java"
# file_type = "java"
# documents = read_document(file_path, file_type)
# chunks = basic_chunker(documents, file_type)
# print(chunks)
