import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Ricart {
	public static HashMap<Integer,Long> defer = new HashMap<Integer,Long>();
	private static long timer;
	public static boolean isRequest=false;
	public static boolean hasEnteredCS;
	public static boolean inCS=true;
	public static int rep_count,req_count;
	public static boolean ALcount;
	public static ArrayList<Integer> al = new ArrayList<Integer>();
	
	
	public static long getTimer() {
		return timer;
	}

	public static void setTimer(long timer) {
		Ricart.timer = timer;
	}

	public void agarwal() throws IOException, InterruptedException  {
		System.out.println(" Process started for CS");
		Caller.flag= false;
		Timer();
		Request();	
	}

	public void Request() throws IOException, InterruptedException {
		isRequest=true;
		req_count=0;
		hasEnteredCS=false;
		broadcast b1 = new broadcast();
		b1.broadsend("REQ ");
		/*if(req_count == 0)
		{
			System.out.println("Entering critical section as request count is zero in arraylist");
			CriticalSection(Caller.serial);
		}*/
	}

	public void Timer() throws InterruptedException {
		int min = 5 , max = 10 ;
		int time = 5 + (int)(Math.random() * ((max - min) + 1));
		System.out.println("Entering random time "+time);
		//System.out.println("Time : ");
		for(int i =0 ;i < time ; i++)
		{
		Thread.sleep(100);
		//System.out.print(i+"\t");
		}
	}

	public static  void Send_reply(int node) throws IOException {
		Socket i = Caller.ips.get(node);
		DataOutputStream dout=new DataOutputStream(i.getOutputStream());  
		dout.writeUTF("REP "+Caller.serial+"");  
		System.out.println("sent REPLY from "+Caller.serial+" to "+node);
		System.out.println();
	}

	public static void CriticalSection(int serial) throws InterruptedException, IOException {
		inCS=false;
		System.out.println("******* Node "+Caller.serial+" has entered critical section******");
		System.out.println("Resetting isFirst and isRequest to false and request count and reply count to zero");
		Caller.isFirst=false;
		
		rep_count=0;
		req_count=0;
		for(int i=1 ; i<=3 ; i++)
		{
			System.out.println("Time : "+i);
			Thread.sleep(100);
		}
		
		System.out.println("************* Sending Deferred replies**************");
		
		Iterator it2 = defer.entrySet().iterator();
		    while (it2.hasNext()) {
		        Map.Entry pair = (Map.Entry)it2.next();
		        System.out.println(" pair content "+pair);
		        int tim = (Integer) pair.getKey();
		        System.out.println(" tim content "+tim);
		        Socket sock = (Socket) Caller.ips.get(tim);
		        DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
				dout.writeUTF("REP "+Caller.serial+"");  
				System.out.println("sent deferred REPLY from "+Caller.serial+" to "+pair);
				System.out.println();
				it2.remove();
				System.out.println("Removed element from defer list "+tim);
		    }

		
		
	/*	
		Iterator<Integer> it = al.iterator();
		while(it.hasNext())
		{
		    int i = it.next();
		   // Send_reply(i,"defer");
		    Socket soc = Caller.ips.get(i);
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream());  

		}*/
		System.out.println("************* sent deferred reply **************");
		System.out.println();
		System.out.println("************ Exiting Critical SEction *************");
		System.out.println();
		Caller.flag=true;
		isRequest=false;
		timer=0;
		ALcount=false;
		inCS=true;
		//Ricart r2 = new Ricart();
		//r2.agarwal();
	}

	public void agarwal(int i1) throws InterruptedException, IOException {
		System.out.println(" Process started for CS");
		Caller.flag= false;
		if(i1 == 1)
		{
			System.out.println(" Wait time range 5-10 as node is "+Caller.serial);
		Timer();
		}
		else
		{
			System.out.println(" Wait time range 45-50 as node is "+Caller.serial);
			Times();
		}
		Request();	
	}

	public void Times() throws InterruptedException {
		int min = 45 , max = 50 ;
		int time = 45 + (int)(Math.random() * ((max - min) + 1));
		System.out.println("Entering random time "+time);
		//System.out.println("Time : ");
		for(int i =0 ;i < time ; i++)
		{
		Thread.sleep(100);
		//System.out.print(i+"\t");
		}
	}

	/*public static void Send_reply(int i, String string) throws IOException {
		Socket soc = Caller.ips.get(i);
		DataOutputStream dout=new DataOutputStream(soc.getOutputStream());  
		dout.writeUTF("REP "+Caller.serial+"");  
		System.out.println("sent deferred REPLY from "+Caller.serial+" to "+i);
		System.out.println();
	}*/
}
