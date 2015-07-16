import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class broadcast {
	
	public void broadsend(String string) throws IOException, InterruptedException {
	//	System.out.println("Hello");
		
		if(string.contains("REQ"))
		{
		  Iterator it = Caller.ips.entrySet().iterator();
		  Ricart.setTimer(System.currentTimeMillis());
		  System.out.println(" TIMER ************* "+Ricart.getTimer());
		  if(Caller.isFirst == true)
		  {  
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        Socket sock = (Socket) pair.getValue();
		        Ricart.req_count++; // to keep count of all requests sent. so that we can track of how many reply to receive
		        Caller.count++;
		        DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
				String str2="REQ "+Ricart.getTimer()+" "+Caller.serial+"";
				System.out.println("******** "+str2+"*********");
				dout.writeUTF(str2); 
		        
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    Caller.isFirst=false;
		  }
		  else
		  {
			  System.out.println("*********************");
			  System.out.println("Sending request based on array list");
			  System.out.println("*********************");
			  for(Integer d:Ricart.al) {
		            System.out.println("Content of arraylist "+d); }
			  
			  if(Ricart.al.size() == 0)
			  {
				  System.out.println("Content of arraylist is zero "+Ricart.al.size());
				  Ricart.ALcount=true;
				  if(Ricart.ALcount == true && Ricart.rep_count ==0 && Ricart.req_count == 0)
					{
						System.out.println("Calling critical section as arraylist count is zero");
						Ricart.CriticalSection(Caller.serial);
					}
			  }
			  
			  Iterator it1 = Ricart.al.iterator( );
			  while ( it1.hasNext( ) ) {
				  int i = (Integer) it1.next();
			        Socket sock = (Socket) Caller.ips.get(i);
			        Ricart.req_count++; // to keep count of all requests sent. so that we can track of how many reply to receive
			        System.out.println("Request count in broadcast "+Ricart.req_count);
			        Caller.count++;
			        DataOutputStream dout1=new DataOutputStream(sock.getOutputStream());
					String str2="REQ "+Ricart.getTimer()+" "+Caller.serial+" arraylist";
					System.out.println("******** "+str2+"*********");
					dout1.writeUTF(str2);
					it1.remove();
					System.out.println("Removed element "+i);
				  }
		  }
		}
		
		else if(string.contains("start"))
		{
		 Iterator it = Caller.ips.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        //System.out.println(pair.getKey() + " = " + pair.getValue());
		        //System.out.println("Sent data");
		        Socket sock = (Socket) pair.getValue();		        
		        DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
				String str2="start "+"from "+Caller.serial;
				dout.writeUTF(str2); 
		        
		       // it.remove(); // avoids a ConcurrentModificationException
		    }
		}
	}

}