import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Tell me what to do:\n1) Run test cases\n2)Run the server");
        Scanner sc = new Scanner(System.in);

        int answerFromUser = sc.nextInt();

        switch(answerFromUser) {
            case 1 -> TestCases.caseTesting();
            case 2 -> {
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
            default -> {
                System.out.println("Invalid input");
            }
        }
    }
}