import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Client {
	
	SerialPort sp;
	final String COM = "COM9";
	final int BYTE_NUMBER = 2;
	
	final String HOST = "192.168.1.100";
	final int PORT = 6666;
	
	Socket conn;
	InputStream is;
	
	public Client() {
		
		// Create serial port object
		sp = new SerialPort(COM);
		conn = new Socket();
		
	}
	
	public void receiveData() {
		
		try {
			conn.connect(new InetSocketAddress(HOST, PORT));// opposite function of server's accept"
			System.out.println("[+] Connected to " + conn.getInetAddress() + ":" + conn.getPort());
			is = conn.getInputStream();
			byte[] data = new byte[BYTE_NUMBER];
			
			while(System.in.available() == 0) {
				is.read(data, 0, BYTE_NUMBER);
				int a = data[0] & 0xff;
				int b = data[1] & 0xff;
				int iData = a * 256 + b;
				//sp.writeBytes(data);
				sp.writeByte(data[0]);
				sp.writeByte(data[1]);
				System.out.println(iData);				
			}
		
			sp.closePort();
			conn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public void startSerialPort() {// same as server
		
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
	
	public static void main(String args[]) {
		
		Client client = new Client();
		client.startSerialPort();
		client.receiveData();
	
	}
		
}

	
