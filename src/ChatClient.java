import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost"; // server address
    private static final int PORT = 12345; // port number for connection
    private Socket socket; // socket for connection to server
    private PrintWriter out; // PrintWriter to send message to server

    public static void main(String[] args) {
        new ChatClient().start(); // initiate ChatClient instance and start
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT); // connection socket to server
            out = new PrintWriter(socket.getOutputStream(), true); // initiate PrintWriter
            new Thread(new IncomingMessageHandler(socket)).start(); // start thread for message
            Scanner scanner = new Scanner(System.in); // initiate scanner for user prompt

            System.out.print("Your Nickname: "); // asking nickname for chat
            String nickname = scanner.nextLine(); // scanner prompt for inputting nickname
            out.println(nickname); // transfer nickname to server

            System.out.println("Start chatting");

            while (true) { // iterate waiting for user's inputting message
                String message = scanner.nextLine(); // message inputted by user
                out.println(message); // transfer message to server
            }
        } catch (IOException e) {
            e.printStackTrace(); // exception handling
        }
    }

    private static class IncomingMessageHandler implements Runnable { // thread for incoming message
        private Socket socket; // socket for server connection
        private BufferedReader in; // BufferedReader to read message from server

        public IncomingMessageHandler(Socket socket) {
            this.socket = socket; // store socket
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // initiate input stream
            } catch (IOException e) {
                e.printStackTrace(); // exception handling
            }
        }

        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) { // retrieval message from server
                    System.out.println(message); // display message from server
                }
            } catch (IOException e) {
                e.printStackTrace(); // exception handling
            }
        }
    }
}
