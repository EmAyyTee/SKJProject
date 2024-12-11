import java.io.IOException;
import java.net.BindException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        TestCases.caseTesting();

        //Master in case slaves are gonna be coming from somewhere else

        int masterPort = 12345;
        Thread masterThread = new Thread(() -> {
            try {
                DAS master = new DAS(masterPort, 10);
                master.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        masterThread.start();
        TimeUnit.SECONDS.sleep(1);
    }
}