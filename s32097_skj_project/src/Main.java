import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2){
            int masterPort = Integer.parseInt(args[1]);
            int numberToInput = Integer.parseInt(args[2]);
            Thread masterThread = new Thread(() -> {
                try {
                    DAS master = new DAS(masterPort, numberToInput);
                    master.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            masterThread.start();
            TimeUnit.SECONDS.sleep(1);
        }else if(args.length < 2){


            System.out.println("Tell me what to do:\n1) Run test cases\n2)Run the server");
            Scanner sc = new Scanner(System.in);

            int answerFromUser = sc.nextInt();

            switch(answerFromUser) {
                case 1 -> TestCases.caseTesting();
                case 2 -> {
                    System.out.println("Give me a port number: ");
                    int numberToInput = sc.nextInt();
                    System.out.println("Give me a number to send: ");
                    int masterPort = sc.nextInt();
                    Thread masterThread = new Thread(() -> {
                        try {
                            DAS master = new DAS(masterPort, numberToInput);
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
        } else {
            System.out.println("Correct uses of application:\njavac ./main.java [port_number] [number_message]\n" +
                    "javac ./main.java");
        }
    }
}