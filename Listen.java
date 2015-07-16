import java.io.*;
import java.net.Socket;

// Class that gets spawned in server and stays in listen state receiving messages.
public class Listen implements Runnable {

	Socket SOCK;
	static String str2,str3;
	
	public Listen(Socket sock) {
		this.SOCK = sock;
	}

	public void run() {
		try {
			str2 = "";
			str3 = "";
			while (true) {
				
				DataInputStream din = new DataInputStream(SOCK.getInputStream());
				str2 = din.readUTF();
				System.out.println("Received msg: " + str2);
								
				if(str2.contains("start"))
					str3=str2;
							
				//To check count of all setup msgs
				if(Caller.serial == 1)
				{
					String[] splitmsg = str2.split(" ");
							if(splitmsg[0].matches("setup"))
							{
								Caller.setup++;
								System.out.println("setup count "+Caller.setup);
							}
				}
				
				if(str2.contains("REQ"))
				{
					String[] times = str2.split(" ");
					long time = Long.parseLong(times[1]);
					int node = Integer.parseInt(times[2]);
					if(!Ricart.al.contains(node))
					Ricart.al.add(node);
					System.out.println("my time "+Ricart.getTimer());
					System.out.println("rec time "+time);
					if(!Ricart.isRequest || Ricart.getTimer() == 0)
					{
						System.out.println("Reply sent as either request is false or timer is 0");
						System.out.println(Ricart.isRequest);
						System.out.println(Ricart.getTimer());
						Ricart.Send_reply(node);
						//Ricart.al.add(node);
					}
					else if (time < Ricart.getTimer())
					{
						System.out.println("Reply sent as received time is less than mine");
						Ricart.Send_reply(node);
						//Ricart.al.add(node);
					}
					else if (time == Ricart.getTimer())
					{
						if(Caller.serial < node)
						{
							
							System.out.println("Deferred reply to "+node+ " as my node id is less");
							System.out.println();
							Ricart.defer.put(node, time);
							//Ricart.al.add(node);
						}
						else
						{
							System.out.println("Reply sent as my node is greater");
							Ricart.Send_reply(node);
							//Ricart.al.add(node);
						}
					}
					else
					{
						System.out.println("Deferred reply to "+node+ " as my time is less");
						System.out.println();
						Ricart.defer.put(node, time);
						//Ricart.al.add(node);
					}
				}
				
				if(str2.contains("REP"))
				{
					Ricart.rep_count++;
					Caller.count++;
					System.out.println("Reply received : "+Ricart.rep_count);
					System.out.println("Request count : "+Ricart.req_count);
					if(Ricart.req_count == Ricart.rep_count && Ricart.hasEnteredCS == false && Ricart.inCS == true){
						Ricart.inCS=false;
						System.out.println("Calling critical section for 3 seconds");
						Ricart.CriticalSection(Caller.serial);
					}
					else if(Ricart.req_count > Ricart.rep_count && Ricart.hasEnteredCS == false){
						System.out.println("Waiting for replies");
						Thread.sleep(100);
					}
				}
				
				/*if(Ricart.ALcount == true && Ricart.rep_count ==0 && Ricart.req_count == 0)
				{
					System.out.println("Calling critical section as arraylist count is zero");
					Ricart.CriticalSection(Caller.serial);
				}*/
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
