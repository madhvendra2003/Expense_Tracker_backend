FROM python:3.11.4
WORKDIR /app

COPY dist/dataservice-0.1.0.tar.gz .

RUN pip install --no-cache-dir dataservice-0.1.0.tar.gz

ENV FLASK_APP=src/app/__init__.py

EXPOSE 9000

CMD [ "flask", "run", "--host=0.0.0.0", "--port=9000" ]

