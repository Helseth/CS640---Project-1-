import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

/////////////////////////////////////////////////////////////////
// CS640 - Project 1
// Pinger.java
// Description: Pings stuff (working desc)
/////////////////////////////////////////////////////////////
public class Pinger {

	public static void main(String[] args) throws Exception {
		
		int localPort = 0;			//determines our port
		int remotePort = 0;			//determines destination port
		String hostname = "";		//determines the name of the remote port
		int pktCount = 0;			//determines the amount of packets we send
		boolean clientMode = false;	//if true = client; false = server
		
		//Check to see if there is either 9 parameters for client or 3 for server
		if (args.length != 8 && args.length != 2){
			System.out.println("Error: missing or additional arguments");
			return;
		}
		
		//assign parameters to class variables
		for (int i = 0; i < args.length; i++){
			if (args[i].contains("-l"))
				localPort = Integer.parseInt(args[i+1]);
			if (args[i].contains("-h"))
				hostname = args[i+1];
			if (args[i].contains("-r"))
				remotePort = Integer.parseInt(args[i+1]);
			if (args[i].contains("-c")){
				pktCount = Integer.parseInt(args[i+1]);
				clientMode = true;
			}
		}
		
		//base next action on whether or not we are the client
		if (clientMode)
			clientPing(hostname, remotePort, pktCount);
		else
			serverPing();
		
		return;
	}
	
	///////////////////////////////////////////
	// Method for acting as the client pinger
	/////////////////////////////////////////
	static void clientPing(String hostname, int port, int pktCount) throws Exception{
		//open a datagram socket to send ping
		DatagramSocket clientSocket = new DatagramSocket();
		
		//Find the address we are sending to
 		InetSocketAddress address = new InetSocketAddress(hostname, port);
		
		//Bind the port to the remote address
 		clientSocket.bind(address);
 		
 		//Set the timeout period on the socket to 10 seconds
 		clientSocket.setSoTimeout(10000);
		
 		for (int i = 0; i < pktCount; i++){
			//create the byte strings to send on our ping
			byte[] buffer = new byte[12];
			byte[] seq = ByteBuffer.allocate(4).putInt(1+i).array();
			long outTime = System.currentTimeMillis();
	 		byte[] timeStamp = ByteBuffer.allocate(8).putLong(outTime).array();
	 		
	 		//Slot in the bytes we created to the buffer
	 		for (int j = 0 ; j < 12 ; j++){
	 			if (j < 4)
	 				buffer[j] = seq[j];
	 			else
	 				buffer[j] = timeStamp[j];
	 		}
	 		
	 		//Make our datagram packet to send
	 		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
	 		//Send the completed packet
	 		clientSocket.send(packet);
	 		
	 		//Creates a datagram packet so we can receive the server packet
	 		DatagramPacket inPkt = null;
	 		
	 		try {
	 			clientSocket.receive(inPkt);
	 			
	 			byte[] inBuffer = inPkt.getData();
	 			ByteBuffer inTimeStamp = ByteBuffer.wrap(inBuffer, 4, 8);
	 			long inTime = inTimeStamp.getLong();
	
	 			System.out.println("rtt= " + Long.toString(inTime - outTime) + " ms");
	 		} catch (SocketTimeoutException e) {
	 			System.out.println("rtt= TIMEOUT ERROR");
	 		}
 		}
 		
 		//Close the socket when we are done sending
 		clientSocket.close();
		
 		return;
	}
	
	//////////////////////////////////////////////////
	// Method for acting as the server being pinged
	////////////////////////////////////////////////
	static void serverPing(){
		
		return;
	}

}
