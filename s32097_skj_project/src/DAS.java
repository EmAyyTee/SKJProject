import java.net.*;
import java.util.*;
import java.io.*;

public class DAS {
    private int port, number;
    private List<Integer> recivedNumbers = new ArrayList<>();
    private DatagramSocket socket;

    public DAS(int port, int number) throws SocketException {
        this.port = port;
        this.number = number;
    }

    public void start() throws IOException{
        try{
            socket = new DatagramSocket(port);
            System.out.println("Running as master");
            master();
        } catch (BindException e) {
            System.out.println("Running as slave");
            slave();
        }
    }

    private void master() throws IOException {
        byte[] buffer = new byte[1400];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true){
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            int recivedValue = Integer.parseInt(message);

            if (recivedValue == 0) {
                calculateAverage();
            } else if (recivedValue == -1){
                broadcastMessage("-1");

            }
        }
    }

    private void calculateAverage() throws IOException{
        if (recivedNumbers.isEmpty()) return;

        double sum = recivedNumbers.stream().mapToInt(Integer::intValue).sum();
        double average = sum / recivedNumbers.size();
        System.out.println("Calculated average: " + average);
        broadcastMessage(String.valueOf(average));
    }

    private void broadcastMessage(String message) throws IOException{
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), port);
        socket.send(packet);
        System.out.println("Broadcasted: " + message);
    }
}
