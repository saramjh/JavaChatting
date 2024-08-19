import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient1 {
    private static final String SERVER_ADDRESS = "localhost"; // 서버 주소 (로컬호스트)
    private static final int PORT = 12345; // 서버가 사용하는 포트 번호
    private Socket socket; // 서버와의 소켓
    private PrintWriter out; // 서버로 메시지를 보내기 위한 PrintWriter

    public static void main(String[] args) {
        new ChatClient().start(); // ChatClient 객체를 생성하고 start 메서드 호출
    }

    public void start() { // 클라이언트 시작 메서드
        try {
            socket = new Socket(SERVER_ADDRESS, PORT); // 서버에 연결
            out = new PrintWriter(socket.getOutputStream(), true); // 서버로 메시지를 보내기 위한 PrintWriter 초기화
            new Thread(new IncomingMessageHandler(socket)).start(); // 수신 메시지를 처리할 스레드 시작
            Scanner scanner = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 초기화

            // 클라이언트에서 닉네임 입력
            System.out.print("닉네임을 입력하세요: ");
            String nickname = scanner.nextLine(); // 사용자로부터 닉네임 입력받기
            out.println(nickname); // 닉네임을 서버로 전송

            System.out.println("채팅 프로그램에 오신 것을 환영합니다!"); // 환영 메시지 출력

            while (true) { // 무한 루프를 통해 사용자 입력을 계속 받음
                String message = scanner.nextLine(); // 사용자로부터 메시지를 입력받음
                out.println(message); // 입력받은 메시지를 서버로 전송
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
        }
    }

    private static class IncomingMessageHandler implements Runnable { // 수신 메시지를 처리할 스레드 클래스
        private Socket socket; // 서버와의 소켓
        private BufferedReader in; // 서버로부터 메시지를 받기 위한 BufferedReader

        public IncomingMessageHandler(Socket socket) { // 생성자에서 소켓을 초기화
            this.socket = socket; // 전달받은 소켓을 멤버 변수에 저장
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 서버 입력 스트림 초기화
            } catch (IOException e) {
                e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            }
        }

        public void run() { // 스레드에서 실행될 메서드
            String message; // 메시지를 저장할 변수
            try {
                while ((message = in.readLine()) != null) { // 서버로부터 메시지를 읽음
                    System.out.println(message); // 받은 메시지를 콘솔에 출력
                }
            } catch (IOException e) {
                e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            }
        }
    }
}
