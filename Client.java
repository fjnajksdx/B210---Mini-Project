import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Server {
	
	SerialPort sp;
	final String COM = "COM6";
	final int BYTE_NUMBER = 2;
	
	final String HOST = "192.168.38.100";//your ip address
	final int PORT = 6666;
	ServerSocket ss;
	Socket conn;
	OutputStream os;
	
	public Server() {
		
		// Create serial port object
		sp = new SerialPort(COM);
	
		// Initialize socket server and connection socket
		try {
			ss = new ServerSocket();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData() {
		
		try {
			ss.bind(new InetSocketAddress(HOST, PORT));// create listening socket
			System.out.println("[+] Server listening on " + ss.getInetAddress() + ":" + ss.getLocalPort());
			ss.setReuseAddress(true);//
			conn = ss.accept(); // wait until a client connects
			System.out.println("[+] Connection received from " + conn.getInetAddress() + ":" + conn.getPort());
			os = conn.getOutputStream(); // to be able to write bytes through the socket
			
			while(System.in.available() == 0) {
				byte[] data = sp.readBytes(BYTE_NUMBER);
				/*int a = data[0] & 0xff;
				int b = data[1] & 0xff;
				int iData = a * 256 + b;
				System.out.println(iData);*/
				os.write(data); // send through the internet
			}
			
			sp.closePort();
			conn.close();
			ss.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public void startSerialPort() {
		
		try {
			sp.openPort();
			sp.setParams(SerialPort.BAUDRATE_9600,
			        SerialPort.DATABITS_8,
			        SerialPort.STOPBITS_1,
			        SerialPort.PARITY_NONE);
			sp.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException, InterruptedException, SerialPortException {
		
		Server server = new Server();
		server.startSerialPort();
		server.sendData();
		
		/*SerialPort sp = new SerialPort("COM13");
		sp.openPort();
		sp.setParams(SerialPort.BAUDRATE_9600,
		        SerialPort.DATABITS_8,
		        SerialPort.STOPBITS_1,
		        SerialPort.PARITY_NONE);
		sp.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                SerialPort.FLOWCONTROL_RTSCTS_OUT);
		while(System.in.available() == 0) {
			byte[] data = sp.readBytes(1);
			System.out.println(new String(data));
		}
		
		sp.closePort();*/
	}
		
}

	
