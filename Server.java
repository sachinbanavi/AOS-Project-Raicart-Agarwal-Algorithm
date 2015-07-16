import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;


public class Server implements Runnable {

	
	public static ArrayList<Socket> connection = new ArrayList<Socket>();
	public static ArrayList<String> nodes = new ArrayList<String>();
	
	public void run()  {

		try{
			 int port = 0;
			
			  String filename= "Configuration";
			  FileReader inputFile = new FileReader(filename);
	          BufferedReader bufferReader = new BufferedReader(inputFile);
	          String line;
			
	          while ((line = bufferReader.readLine()) != null) {
	        	  int po=Integer.parseInt(line.substring(0, 1));
	        	  if(po==Caller.serial)
	        	  {
	        		  String[] parts = line.split(" ");
	        		  port = Integer.parseInt(parts[2]);
	        		  //System.out.println("port " +port);
	        		  break;
	        	  }
	          }
	          
	          bufferReader.close();
	          inputFile.close();
	          
	          if(port == 0)
	          {
	        	  System.out.println("error occured while initializing port number");
	        	  System.exit(1);
	          }
	          
			ServerSocket server = new ServerSocket(port);
			
			while(true)
			{
				Socket sock = server.accept();
				connection.add(sock);
				//System.out.println("client connected "+sock.getLocalAddress().getHostAddress());
				/*DataInputStream dis=new DataInputStream(sock.getInputStream());  
				String  str=(String)dis.readUTF();  
				System.out.println("message= "+str); 
				int key = Integer.parseInt(str);
				Caller.ips.put(key, sock);
				
				
				DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
				String str2="Server "+port+" is connected to "+key;
				dout.writeUTF(str2); */
				
				
				//Adduser(sock);
				
				//create a thread which handles ricart agarwal 
				Listen listener = new Listen(sock);
				Thread l1 = new Thread(listener);
				l1.start();
				
			}
		}
		catch(IOException E) {
			System.out.println("Caought IO Exception in Main Server.java"+E);
		}

	}

	/*public static void Adduser(Socket sock) throws IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner user = new Scanner(sock.getInputStream());
		String nodename = user.nextLine();
		nodes.add(nodename);
		
	}*/

}
