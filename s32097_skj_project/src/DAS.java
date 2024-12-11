import java.net.*;
import java.util.*;
import java.io.*;

public class DAS {
    private int port, number;
    private DatagramSocket socket;

    public DAS(int port, int number) throws SocketException {
        this.port = port;
        this.number = number;
        this.socket = new DatagramSocket(port);
        this.socket.setSoTimeout(5000);

    }

    private boolean isMasterAvaliable(){
        try {
            byte[] discoveryMessage = "DISCOVER.MASTER".getBytes();
            DatagramPacket packet = new DatagramPacket(discoveryMessage, discoveryMessage.length, InetAddress.getByName("255.255.255.255"), port);
            socket.send(packet);

            byte[] buffer = new byte[256];
            DatagramPacket responce = new DatagramPacket(buffer, buffer.length);
            socket.receive(responce);

            return true;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
