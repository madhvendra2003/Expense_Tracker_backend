# DataService

DataService is a small Python microservice that converts phone/SMS messages into normalized expense records. It ingests raw message text (for example, SMS notifications from banks or merchants), extracts structured fields such as amount, date, merchant, and category, and outputs a consistent expense JSON object suitable for storage or downstream accounting/analytics.

Key components
- `src/app/service/Expense.py` — expense model and normalization logic
- `src/app/service/llmService.py` — (optional) LLM-based message parsing helper
- `src/app/service/messageService.py` — message ingestion and orchestration
- `src/app/utils/messageUtil.py` — parsing helpers and utilities

Quick notes
- Install dependencies listed in `pyproject.toml` and run the service using your preferred runner.
- Feed incoming messages to the message service (e.g., via an API endpoint or a message queue) to get back standardized expense JSON.

This repo is intended as a lightweight component that can be integrated into a larger expense-tracking pipeline or used as a standalone parser for transforming SMS/phone messages into expense records.

Contributing
- Feel free to open issues or PRs to improve parsers, add tests, or wire a real API endpoint.

License
- See `pyproject.toml` for project metadata. (Add a LICENSE file if you want a specific license.)
