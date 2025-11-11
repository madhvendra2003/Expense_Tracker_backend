from datetime import datetime
from typing import Optional
from pydantic import BaseModel, Field

class Expense(BaseModel):
    amount: Optional[float] = Field(
        None,
        title="Amount",
        description="The transaction amount as a floating point number",
        examples=[100.50, 1000.00, 5000.00]
    )
    
    date: Optional[str] = Field(
        None,
        title="Date",
        description="The transaction date in YYYY-MM-DD format",
        examples=["2025-10-23"]
    )
    
    merchant: Optional[str] = Field(
        None,
        title="Merchant",
        description="Name of the merchant, sender, or receiver in the transaction",
        min_length=1,
        max_length=100,
        examples=["Puru", "HDFC Bank", "Amazon"]
    )
    
    currency: Optional[str] = Field(
        "INR",  # Default to INR for Indian Rupees
        title="Currency",
        description="The currency code for the transaction",
        min_length=3,
        max_length=3,
        examples=["INR", "USD", "EUR"]
    )

    user_id: Optional[str] = Field(
        None,   description="The unique identifier for the user associated with the expense."
    )
