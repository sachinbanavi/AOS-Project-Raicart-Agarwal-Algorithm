import java.io.*;
import java.net.Socket;
import java.util.*;


public class Caller {

	static int serial;
	static int setup;
	static int count;
	public static boolean flag;
	public static boolean isFirst;
	public static HashMap<Integer,Socket> ips = new HashMap<Integer,Socket>();
	
	public static void main(String[] args) throws InterruptedException, IOException {
		//Reading node number from user
		System.out.println("Enter name of the node");

		Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        
        serial = Integer.parseInt(s);
        //System.out.println("Node number "+serial);
		
		
		//Creating server class thread.
		Server s1 = new Server();
		Thread t1 = new Thread(s1);
		t1.start();
		//System.out.println("Successfully created server thread");
		
		Thread.sleep(15000);
		//Calling client method1
		
		Client c1 = new Client();
		Thread t2 = new Thread(c1);
		t2.start();
		
		//Iterating through hashmap
		  /*for(Map.Entry m:ips.entrySet()){  
			   System.out.println(m.getKey()+" "+m.getValue());  
			  }  */
		  
		Thread.sleep(15000);  
		//System.out.println("Trying broadcast");
		//broadcast b1 = new broadcast();
		//b1.broadsend();
		
		Ricart r1 = new Ricart();
		
		
		while(true)
		{
			if(ips.size() != 2)
			{
				Thread.sleep(300);
			}
			else
				break;
		}
		

		if(Caller.serial != 1 &&  (ips.size() == 2))
			{
				Socket i = ips.get(1);
				DataOutputStream dout=new DataOutputStream(i.getOutputStream());  
				dout.writeUTF("setup of node "+Caller.serial+" is done.");  
			}
		
		Thread.sleep(5000);
		
		if(Caller.serial == 1){
		broadcast b2 = new broadcast();
		b2.broadsend("start");
		}
		/*boolean flag1= true;
		if(Caller.serial == 1)
		{
		while(flag1)
		{
			//System.out.println("setup "+setup);
		 if(setup == 2)
		 {
			 for(Map.Entry m:ips.entrySet()){  
					   DataOutputStream dout=new DataOutputStream(((Socket) m.getValue()).getOutputStream());  
						dout.writeUTF("start"); 
						System.out.println("start");
				  }	
			 flag1=false;
		 }
		}	
		}*/
		
		// Compute for critical section
		if(Caller.serial == 1) Thread.sleep(300);
		if(Caller.serial == 2) Thread.sleep(200);
		Thread.sleep(3000);
		//System.out.println("In caller for "+Caller.serial+" str2 has "+Listen.str2+"");
		System.out.println("\n*************************************************************************************");
		if(Listen.str3.contains("start") || Caller.serial == 1){
		//	System.out.println("In ricart caller "+Caller.serial);
			isFirst=true;
			
			System.out.println("***********************RICART AGARWAL***************************************");
			System.out.println("FIRST TIME = TRUE");
			/*for(int i=1; i <= 5 ; i++)
			{
				System.out.println(" Turn "+i);
			r1.agarwal();
			System.out.println(" Value of flag "+flag);
			while (flag == false)
			{
				Thread.sleep(100);

			}
			else
			{
				System.out.println("I am here");
				break;
			}
			
			}*/
			
			for(int i=1; i <= 5 ; i++)
			{
				System.out.println(" Turn "+i);
				int i1 = serial%2;
			r1.agarwal(i1);
			System.out.println(" Value of flag "+flag);
			while (flag == false)
			{
				Thread.sleep(50);

			}
			/*else
			{
				System.out.println("I am here");
				break;
			}*/
			
			}
			
			
			
		}
		
		/*if(flag)
		{
			for(int i=1; i <=2 ; i++)
				r1.agarwal();
		}
		*/
		System.out.println("Total msg "+count);
	}

}
