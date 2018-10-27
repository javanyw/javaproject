import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class tempClientRunner {
    tempClientRunner() throws IOException {
        SocketChannel socketChannel = null;
        boolean success=false;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            Thread ClientThread = new Thread(new IncomingReader());
            System.out.println("연결 요청");
            StringBuilder data = null;
            try{
                data = new StringBuilder("");
                File fis = new File("d:\\ip.txt");
                if (fis.exists()) {
                    FileReader fileReader = new FileReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = null;
                    System.out.println("ip.txt를 발견했고 글로 연결한다");
                    while ((line = bufferedReader.readLine()) != null) {
                        data.append(line);

                    }
                    bufferedReader.close();
                    assert data != null;

                    socketChannel.connect(new InetSocketAddress(data.toString(), Integer.parseInt("5001")));
                    System.out.println("연결 성공");
                    success=true;
                }
            }
            catch (FileNotFoundException e){
                //ip.txt 파일 없을 경우 default로 localhost 연결

                //ip.txt 파일 없을 경우 default로 localhost 연결
                System.out.println("ip.txt가 없다 localhost로 연결한다");
                socketChannel.connect(new InetSocketAddress("localhost", Integer.parseInt("5001")));
                System.out.println("연결 성공");
                success=true;

            }

            catch(ConnectException e){
                //연결 실패
                System.out.println("연결 실패다!!");
                success = false;
                //연결 실패
            }
            finally {

            }




        } catch (IOException e) {
            //파일 읽기 쓰기 실패
            e.printStackTrace();
        }
        while(true) {
            String input;
            System.out.println("지역-년도,월,일 반드시 이 형식으로 해야 함 서버내 코드를 바꾸면 형식을 바꿀 수도 있음 X가 입력되면 while 탈출한다 ex) 부산-2018,11,02");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (input.contains("X")) {
                break;
            }
            //System.out.println("[연결 성공]");
            ByteBuffer byteBuffer = null;
            Pattern pattern = Pattern.compile("^[가-힣]{2,}-([0-9]{4}),([0-9]{1,2}),([0-9]{1,2})$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                Charset charset = Charset.forName("UTF-8");
                byteBuffer = charset.encode(input);//지역-년도,월,일 반드시 이 형식으로 해야 함 서버내 코드를 바꾸면 형식을 바꿀 수도 있음
                socketChannel.write(byteBuffer);
                System.out.println("[데이터 보내기 성공]");
                byteBuffer = ByteBuffer.allocateDirect(2000);
                int byteCount = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                String message = charset.decode(byteBuffer).toString();
                System.out.println("[데이터 받기 성공]: " + message);
            }
            else{
                System.out.println("니 잘못 입력한 거 같다");
            }
        }

        if(socketChannel.isOpen()){
            try{
                socketChannel.close();
            } catch(ClosedChannelException e ){

            } catch (IOException e){

            }
        }

    }

    private class IncomingReader implements Runnable {
        @Override
        public void run() {

        }
    }
}
