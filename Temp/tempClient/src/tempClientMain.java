import java.io.IOException;

public class tempClientMain {
    public static void main(String[] args) {
        try {
            tempClientRunner CR = new tempClientRunner();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
