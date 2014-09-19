/////////////////////////////////////////////////////////////////
// CS640 - Project 1
// Pinger.java
// Description: Pings stuff (working desc)
/////////////////////////////////////////////////////////////
public class pinger {

	public static void main(String[] args) {
		//Accept args from the command line
		//adds descriptions to args here
		int localPort = 0;			//determines our port
		int remotePort = 0;			//determines destination port
		String hostname = "";		//determines the name of the remote port
		int pktCount = 0;			//determines the amount of packets we send
		boolean clientMode = true;	//if true = client; false = server
		
		//Check to see if there is either 9 params for client or 3 for 
		//TODO: Make sure java uses the class name as an argument
		if (args.length != 9 || args.length != 3)
			System.out.println("Error: missing or additional arguments");
		
		//assign params to class variables
		for (int i = 0; i < args.length; i++){
			if (args[i] == "-l")
				localPort = Integer.parseInt(args[i+1]);
			if (args[i] == "-h")
				hostname = args[i+1];
			if (args[i] == "-r")
				remotePort = Integer.parseInt(args[i+1]);
			if (args[i] == "-c")
				pktCount = Integer.parseInt(args[i+1]);
		}
		return;
	}

}
