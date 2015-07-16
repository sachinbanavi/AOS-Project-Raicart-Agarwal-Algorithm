import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
	public void run() {
		
		
		try {
			int port;
			String ipaddr;
			int node;
			String filename = "Configuration";
			
			  FileReader inputFile = new FileReader(filename);
	          BufferedReader bufferReader = new BufferedReader(inputFile);
	          String line;

	          while ((line = bufferReader.readLine()) != null)   {
	        	  
	            String[] liner = line.split(" ");
	            node=Integer.parseInt(liner[0]);
	            ipaddr=liner[1];
	            port=Integer.parseInt(liner[2]);
	           
	            if(Caller.serial != node /*&& node > Caller.serial*/)
	            {
				Socket sock = new Socket(ipaddr, port);
				System.out.println("Node "+Caller.serial+ " is connected to node " + node);
				/*
				DataOutputStream dout=new DataOutputStream(sock.getOutputStream());  
				dout.writeUTF(String.valueOf(Caller.serial));  
				
				DataInputStream din=new DataInputStream(sock.getInputStream());  
				String str2=din.readUTF();
				System.out.println(""+str2);*/
				Caller.ips.put(node, sock);
	            }
	            
	          }
	          //Close the buffer reader
	          bufferReader.close();
	          inputFile.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}


}
