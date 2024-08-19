import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatServer {
    private static final int PORT = 12345; //  open port number for socket connect
    private static Set<ClientHandler> clientHandler = new HashSet<>(); // Hashset for storing connections

    public static void main(String[] args) {
        System.out.println("Chatting server started..."); // Server initiated message
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // initiate server socket
            while (true) { // waiting for client connection
                new ClientHandler(serverSocket.accept()).start(); // starting thread of ClientHandler instance
            }
        } catch (IOException e) {
            e.printStackTrace(); // exception handling
        }
    }

    private static class ClientHandler extends Thread { // thread class for client handling
        private Socket socket; // socket for client
        private PrintWriter out; // PrintWriter to send data to client
        private BufferedReader in; // BufferedReader to receive data from client
        private String nickname; // nickname of client that the client user named
        private String ipAddress; // client's IP address

        public ClientHandler(Socket socket) {
            this.socket = socket; // store socket of client
            this.ipAddress = socket.getInetAddress().getHostAddress(); // store IP address of client
        }

        public void run() { // thread
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // initiate input stream
                out = new PrintWriter(socket.getOutputStream(), true); // initiate output stream

                nickname = in.readLine(); // receive nickname from client
                synchronized (clientHandler) { // prevent data contamination
                    clientHandler.add(this); // add client to clientHandler hashset
                }

                String message;
                while ((message = in.readLine()) != null) { // retrieval client message
                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date()); // time format for chatting time
                    System.out.println("[" + timestamp + "] " + ipAddress + " (" + nickname + "): " + message); // output of chatting message for server
                    broadcastMessage("[" + timestamp + "] " + nickname + ": " + message); // broadcasting message with chatting time, nickname and message
                }
            } catch (IOException e) {
                e.printStackTrace(); // exception handling
            } finally {
                try {
                    socket.close(); // close client socket
                } catch (IOException e) {
                    e.printStackTrace(); // exception handling
                }
                synchronized (clientHandler) {
                    clientHandler.remove(this); // remove client from clientHandler hashset
                }
            }
        }

        private void broadcastMessage(String message) { // broadcast message to all clients
            synchronized (clientHandler) { // synchronize clientHandler hashset
                for (ClientHandler client : clientHandler) { // iterate to all clients in the clientHandler
                    client.out.println(message); // send message
                }
            }
        }
    }
}
