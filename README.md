# Online Chat Application in Java

## Introduction
This application is a simple online chat programme implemented using Java. It allows multiple users to connect to the server and send and receive messages.
This program was a Programming assignment.

## Preview
Server   ![Server Preview](https://github.com/user-attachments/assets/a862649e-afd6-4a3b-a5bd-f2d726033111)

Client0   ![Client0 Preview](https://github.com/user-attachments/assets/f3f93ee3-6119-47b7-b220-3fd15f3a536b)

Client1   ![Client1 Preview](https://github.com/user-attachments/assets/e74cd9e6-8207-4c76-966f-f0af3af1fe29)


## How to run.
1. Run the `ChatServer` class first to start the server.
2. Run the `ChatClient` class several times to start the client.
3. Type your nickname and a message on the client and it will be sent to other clients via the server.

## Implementation details
- The server manages multiple client connections.
- Clients send messages to the server, and the server broadcasts messages to all clients.
- This chatting is based on socket communication.
