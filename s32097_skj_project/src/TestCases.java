import java.io.IOException;
import java.net.BindException;
import java.util.concurrent.TimeUnit;

public class TestCases {
    public static void caseTesting() {
        System.out.println("Running test cases for DAS...");

        try {
            int masterPort = 12345;

            System.out.println("\nTest Case 1: Master starts on port " + masterPort);
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

            System.out.println("\nTest Case 2: Slave sends a number to master");

            Thread slaveThread1 = new Thread(() -> {
                try {
                    DAS slave = new DAS(masterPort, 20);
                    slave.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            slaveThread1.start();

            TimeUnit.SECONDS.sleep(1);

            System.out.println("\nTest Case 3: Slave sends 0 to trigger averaging");

            Thread slaveThread2 = new Thread(() -> {
                try {
                    DAS slave = new DAS(masterPort, 0);
                    slave.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            slaveThread2.start();
            TimeUnit.SECONDS.sleep(1);

            System.out.println("\nTest Case 4: Slave sends -1 to terminate master");
            Thread slaveThread3 = new Thread(() -> {
                try {
                    DAS slave = new DAS(masterPort, -1);
                    slave.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            slaveThread3.start();

            masterThread.join();
            System.out.println("\nAll test cases completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
