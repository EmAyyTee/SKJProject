import java.net.*;
import java.util.*;
import java.io.*;

public class DAS {
    private final int port, number;
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
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true){
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            int recivedValue = 0;

            if (message.contains(".")){
                System.out.println("Recived broadcasted average: " + message);
                continue;
            }else {
                try {
                    recivedValue = Integer.parseInt(message);
                } catch (NumberFormatException e){
                    System.out.println("Invalid integer recived: " + message);
                    continue;
                }
            }
            if (recivedValue == 0){
                calculateAverage();
            } else if (recivedValue == -1){
                broadcastMessage("-1");
                break;
            } else {
                System.out.println("Recived: " + recivedValue);
                recivedNumbers.add(recivedValue);
            }
        }
        socket.close();
    }

    private void calculateAverage() throws IOException{
        if (recivedNumbers.isEmpty()) return;

        double sum = recivedNumbers.stream().mapToInt(Integer::intValue).sum();
        int average = (int) (sum / recivedNumbers.size());
        System.out.println("Calculated average: " + average);
        broadcastMessage(String.valueOf(average));
    }

    private void broadcastMessage(String message) throws IOException{
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), port);
        socket.send(packet);
        System.out.println("Broadcasted: " + message);
    }

    private void slave() throws IOException{
        DatagramSocket socket = new DatagramSocket();
        byte[] data = String.valueOf(number).getBytes();

        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), port);
        socket.send(packet);
        System.out.println("Sent to master: " + number);
        socket.close();
    }
}
