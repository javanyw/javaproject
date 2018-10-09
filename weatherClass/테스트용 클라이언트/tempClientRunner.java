import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.io.IOException;
import java.nio.charset.Charset;


public class tempClientRunner {
    tempClientRunner() throws IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            System.out.println("연결 요청");
            StringBuilder data = null;
            try{
                FileInputStream fis = new FileInputStream("/ip.txt");
                int dataend;

                while((dataend = fis.read())!=-1){
                    data.append(Integer.toString(dataend));

                }
                fis.close();
                assert data != null;
                socketChannel.connect(new InetSocketAddress(data.toString(), Integer.parseInt("5001")));
                System.out.println("연결 성공");
            }
            catch (FileNotFoundException e){
                //ip.txt 파일 없을 경우 default로 localhost 연결

                    socketChannel.connect(new InetSocketAddress("localhost", Integer.parseInt("5001")));
                    System.out.println("연결 성공");

            }
            catch (AssertionError a){
                //data가 null일 경우
            }
            catch(ConnectException e){
                //연결 실패
            }
            finally {
            //위에서 안 걸러지는 오류
            }




        } catch (IOException e) {
            //파일 읽기 쓰기 실패
            e.printStackTrace();
        }
        System.out.println("[연결 성공]");
        ByteBuffer byteBuffer = null;
        Charset charset = Charset.forName("UTF-8");
        byteBuffer = charset.encode("부산-날씨-2018,10,15");//지역-날씨-년도,월,일 반드시 이 형식으로 해야 함 서버내 코드를 바꾸면 형식을 바꿀 수도 있음
        socketChannel.write(byteBuffer);
        System.out.println("[데이터 보내기 성공]");
        byteBuffer = ByteBuffer.allocateDirect(2000);
        int byteCount = socketChannel.read(byteBuffer);
        byteBuffer.flip();
        String message = charset.decode(byteBuffer).toString();
        System.out.println("[데이터 받기 성공]: " + message);
        if(socketChannel.isOpen()){
            try{
                socketChannel.close();
            } catch(ClosedChannelException e ){

            } catch (IOException e){

            }
        }

    }

}
