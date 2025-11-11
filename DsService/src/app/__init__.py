from flask import Flask
from flask import request, jsonify
from app.service.messageService import MessageService   # Changed to relative import
from kafka import KafkaProducer
import json
import os
from dotenv import load_dotenv


load_dotenv()
messageService = MessageService()

kafka_host = os.getenv('KAFKA_HOST', 'localhost')
kafka_port = os.getenv('KAFKA_PORT', '9092')
app = Flask(__name__)
app.config.from_pyfile('config.py')
kafka_bootstrap_server = f"{kafka_host}:{kafka_port}"
producer = KafkaProducer(bootstrap_servers=kafka_bootstrap_server, 
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

@app.route('/v1/ds/message/',methods=['POST'])
def handle_message():
    # Get message and user_id from request body
    data = request.json
    message = data.get('message')
    user_id = request.headers.get('X-User-ID')
    
   
    
    result = messageService.process_message(message)
    result.user_id = user_id
    print(result)
     
    serialized_result = result.model_dump_json()

    producer.send('expense_service',serialized_result)
    return jsonify(result.model_dump() if result else {})

@app.route('/v1/health/',methods=['GET'])
def health_check():
    return jsonify({"status":"ok"})


if __name__ == "__main__":
    app.run(host="localhost",port="9000",debug=True)