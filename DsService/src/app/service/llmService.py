
from dotenv import load_dotenv, dotenv_values
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain_google_genai import ChatGoogleGenerativeAI
from pydantic import BaseModel, Field
from typing import Optional
import os
from app.service.Expense import Expense



class LLMService:
    def __init__(self):
        load_dotenv()
        self.prompt = ChatPromptTemplate.from_messages([
            (
                 "system",
                 "You are an expert extraction algorithm. Extract financial transaction details from messages."
                 "For bank transfers and transactions, extract:"
                 "- amount (as a float number)"
                 "- merchant (sender/receiver name)"
                 "- currency (default to 'INR' for Rupees if not specified)"
                 "- date (current date if not in message)"
                 "If you don't know a value, return null for that attribute."
            ),(
                "user","{text}"

            )
        ])

        self.api_key = os.getenv("GOOGLE_API_KEY")
        self.llm = ChatGoogleGenerativeAI(
            model="gemini-2.5-flash",
             api_key=self.api_key,
              temperature=1)  
        self.runnable = self.prompt | self.llm.with_structured_output(schema=Expense)
    
    def runllm(self,text):
        response = self.runnable.invoke({"text":text})
        return response
