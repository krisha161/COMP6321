package Helloapl;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.ORB;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import dsd.Event_Interface;

public class Mtl_server
{
	private ORB orb;
	static int unique_id = 0;
		
	HashMap<String,HashMap<String,Integer>> MTLEvents = new HashMap<>();
	
	HashMap<String,Integer> Conference=new HashMap<>();
	HashMap<String,Integer> TradeShows=new HashMap<>();
	HashMap<String,Integer> Seminars=new HashMap<>();
	
	HashMap<String,ArrayList<String>> Mtl_Con=new HashMap<>();
	HashMap<String,ArrayList<String>> Mtl_Sem=new HashMap<>();
	HashMap<String,ArrayList<String>> Mtl_Ts=new HashMap<>();
	
	ArrayList<String> Manager=new ArrayList<>();
	HashMap<String,ArrayList<Integer>>Counter=new HashMap<>();
	
	Logger log=null;
	FileHandler file;
	
	int port;

	private String msg;
	
	public void inimain()
	{
		
		System.out.println("Inside Montreal Initailize main.....");
		
	/*	try
        {
            
            log=Logger.getLogger("Mtl_server");
            file=new FileHandler("D:/Shubham/Concordia/Distributed System/assignment/dsd/dsd/Logs/Mtl_server.log",true);
            log.addHandler(file);
            log.setUseParentHandlers(false);
            SimpleFormatter sf=new SimpleFormatter();
            file.setFormatter(sf);
        
        }
		catch(IOException e)
		{
        	e.printStackTrace();
        }
        
		*/
		Seminars.put("MTLM010119",5);
		Seminars.put("MTLA010119",7);
		Seminars.put("MTLE010119",8);
		MTLEvents.put("SEM",Seminars);
		
		Conference.put("MTLM020119",10);
		Conference.put("MTLA020119",4);
		Conference.put("MTLE020119",6);
		MTLEvents.put("CON",Conference);
		
		TradeShows.put("MTLM030119",3);
		TradeShows.put("MTLA030119",6);
		TradeShows.put("MTLE030119",7);
		MTLEvents.put("TS",TradeShows);
		
		Manager.add("MTLM1234");
		Manager.add("MTLM2345");
		Manager.add("MTLM3456");
		Manager.add("MTLM9000");
		
		ArrayList<String> a = new ArrayList<>();
		a.add("MTLM230519");
		a.add("MTLA240519");
		a.add("MTLE220519");
		Mtl_Con.put("MTLC3456",a);					
		
		ArrayList<String> b = new ArrayList<>();
		b.add("MTLM230519");
		b.add("MTLA240519");
		b.add("MTLE220519");
		Mtl_Sem.put("MTLC3456",b);
		
		ArrayList<String> c = new ArrayList<>();
		c.add("MTLM230519");
		c.add("MTLA240519");
		c.add("MTLE220519");
		Mtl_Ts.put("MTLC3456",c);
		
		ArrayList<Integer>c1 = new ArrayList<>();
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		c1.add(0);
		Counter.put("MTLC1",c1);
		

		ArrayList<Integer>c2= new ArrayList<>();
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		c2.add(0);
		Counter.put("MTLC2",c2);
		

		ArrayList<Integer>c3 = new ArrayList<>();
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		c3.add(0);
		Counter.put("MTLC3",c3);
		
	}
	
  private String call_other(String purpose, String customerID,String eventID, String eventType, int port) {
		System.out.println("In call other method");
		String msg="NULL";
			try {
			
			
			DatagramSocket dgs=new DatagramSocket();
			byte[] mpurpose=new byte[10000];
			byte[] meventID=new byte[10000];
			byte[] meventType=new byte[10000];
			byte[] mcustomerID=new byte[10000];
			
			InetAddress inet = InetAddress.getByName("localhost");
			
			mpurpose=purpose.getBytes();
			meventID=eventID.getBytes();
			meventType=eventType.getBytes();
			mcustomerID=customerID.getBytes();
			String packet1 = new String(new String(mpurpose)+","+new String(meventID)+","+new String(mcustomerID)+","+new String(meventType));
            byte[] packet = packet1.getBytes();
			DatagramPacket dp=new DatagramPacket(packet,packet.length,inet,port);
			dgs.send(dp);
			System.out.println("Packet Sent...");

			byte[] recmsg=new byte[10000];
			DatagramPacket dpr=new DatagramPacket(recmsg,recmsg.length);
			dgs.receive(dpr);
			
		    msg = new String(dpr.getData());
			msg=msg.trim().toString();
			System.out.println("Message received: "+msg );
			return msg;
			
		}
		
		catch(Exception e)
		  {
			System.out.println(e.getMessage());
		  }
			return msg;
	}

	

    private String call_other1(String purpose, String eventID, String eventType, int port) {
		System.out.println("In call other1 method");
		String msg="NULL";
			try {
			
			
			DatagramSocket dgs=new DatagramSocket();
			byte[] mpurpose=new byte[10000];
			byte[] meventID=new byte[10000];
			byte[] meventType=new byte[10000];		
		
			InetAddress inet = InetAddress.getByName("localhost");
			
			mpurpose=purpose.getBytes();
			meventID=eventID.getBytes();
			meventType=eventType.getBytes();
			
			String packet1 = new String(new String(mpurpose)+","+new String(meventID)+","+new String(meventType));
            byte[] packet = packet1.getBytes();
			DatagramPacket dp=new DatagramPacket(packet,packet.length,inet,port);
			dgs.send(dp);
			System.out.println(new String(packet1));
			System.out.println("Packet Sent...");

			byte[] recmsg=new byte[10000];
			DatagramPacket dpr=new DatagramPacket(recmsg,recmsg.length);
			dgs.receive(dpr);
			System.out.println("receive");
		
		   msg = new String(dpr.getData());
		   return msg;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
				return msg;
	}
	

	void receive() 
	  {
		System.out.println("In montreal receive");
		try {
			String msg="";
			DatagramSocket dgs=new DatagramSocket(4001);
			while(true) {
					byte[] buf = new byte[10000];
					DatagramPacket dp=new DatagramPacket(buf,buf.length);
					dgs.receive(dp);
					String str = new String(dp.getData(),0,dp.getLength());
					String[] receivepacket = str.split(",");
						String purpose = new String(receivepacket[0]);
						if("BookEvent".equalsIgnoreCase(purpose)) {
							String eventID = new String(receivepacket[1]);
							String customerID = new String(receivepacket[2]);
							String eventType = new String(receivepacket[3]);						
							System.out.println("going to book event");
							msg=book(eventID.trim(),customerID.trim(),eventType.trim());
						     }
						else if("ListEventAvailability".equalsIgnoreCase(purpose)) {
						    	String eventType = new String(receivepacket[2]);
						    	System.out.println("going to list availability");
						    	msg=listEventAvailability1(eventType.trim());
						    }
						else if("Cancel".equalsIgnoreCase(purpose))
						  {
						
							String eventID = new String(receivepacket[1]);
							String eventType = new String(receivepacket[2]);						
							System.out.println("going to cancel event");
							msg=cancelEvent1(eventID.trim(),eventType.trim());
							
					      }
						else if("RemoveEvent".equalsIgnoreCase(purpose)) {
							String eventID = new String(receivepacket[1]);
							String eventType = new String(receivepacket[2]);						
							System.out.println("going to REMOVE event");
							msg=removeEvent1(eventID.trim(),eventType.trim());
						}
						byte[] msgs=new byte[10000];
						msgs=msg.getBytes();
						DatagramPacket dps=new DatagramPacket(msgs,msgs.length,dp.getAddress(),dp.getPort());
						dgs.send(dps);
				}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	 }
	

 public synchronized String addEvent(String ManagerID, String eventID, String eventType, int bookingCapacity)  
 {
	String msg="";
	System.out.println("inside addEvent");
	System.out.println(ManagerID+" "+eventID+" "+eventType+" "+bookingCapacity);
        
	if(Manager.contains(ManagerID.trim()))
	  {
		if(MTLEvents.get(eventType).containsKey(eventID))
        {
        	MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)+bookingCapacity);
            
            msg="EVENT ADDED SUCCESSFULLY.";
          
        }
        else
        {
        	if(eventID.startsWith("MTL"))
        	 {
	            System.out.println("2");
	            MTLEvents.get(eventType).put(eventID, bookingCapacity); 
	            msg="EVENT ADDED SUCCESSFULLY.";
               // log.log(Level.INFO, "Manager with ID: {0} Add Event {1}", new Object[]{ManagerID, eventID});

        	 }
        	else
        		msg="ENTER VALID EVENTID.";
        }
	  }
	else
	 { 
		msg="THIS MANAGERID IS NOT REGISTERED.";
	 }
    return msg;
 }

public synchronized String removeEvent(String eventID, String eventType) 
   {
		String msg = null;
		 System.out.println("in remove event");
		if(eventID.substring(0,3).equalsIgnoreCase("MTL"))
		{
			if(MTLEvents.get(eventType).containsKey(eventID)) 
			{
				if(MTLEvents.get(eventType).remove(eventID,MTLEvents.get(eventType).get(eventID)))
				{
					if(eventType.equalsIgnoreCase("TS")) 
					{
						System.out.println(Mtl_Ts);
						for (Map.Entry<String, ArrayList<String>> entry : Mtl_Ts.entrySet())
						 {
							if(Mtl_Ts.get(entry.getKey()).contains(eventID)) 
							 {
							   Mtl_Ts.get(entry.getKey()).remove(eventID.trim().toString());
							 }					
				         }
						String purpose="Remove";
						String Otwmsg=call_other1(purpose,eventID,eventType,6001);
						String Tormsg=call_other1(purpose,eventID,eventType,5001);
						System.out.println(Mtl_Ts);
						System.out.println(Otwmsg);
						System.out.println(Tormsg);
						msg= "EVENT HAS BEEN REMOVED."; 
						System.out.println(msg);
	                  //  log.log(Level.INFO, "Event{0} Removed EventType {1}", new Object[]{ eventID, eventType});
	                    return msg;
				    }
					else if(eventType.equalsIgnoreCase("SEM")) 
					{
						System.out.println(Mtl_Sem);
						for (Map.Entry<String, ArrayList<String>> entry : Mtl_Sem.entrySet()) 
						{
							if(Mtl_Sem.get(entry.getKey()).contains(eventID)) 
							  {
								Mtl_Sem.get(entry.getKey()).remove(eventID.trim().toString());
							  }
						}	
						String purpose="Remove";
						String Otwmsg=call_other1(purpose,eventID,eventType,6001);
						String Tormsg=call_other1(purpose,eventID,eventType,5001);
						System.out.println(Mtl_Sem);
						System.out.println(Otwmsg);
						System.out.println(Tormsg);
						msg="EVENT HAS BEEN REMOVED."; 
						System.out.println(msg);
	                    //log.log(Level.INFO, "Event{0} Removed EventType {1}", new Object[]{ eventID, eventType});
	                    return msg;
						
					}
					else if(eventType.equalsIgnoreCase("CON"))
					{
						System.out.println(Mtl_Con);
						for (Map.Entry<String, ArrayList<String>> entry : Mtl_Con.entrySet()) 
						{
							if(Mtl_Con.get(entry.getKey()).contains(eventID)) 
							{
							  Mtl_Con.get(entry.getKey()).remove(eventID.trim().toString());
							}	
						}
						String purpose="Remove";
						String Otwmsg=call_other1(purpose,eventID,eventType,6001);
						String Tormsg=call_other1(purpose,eventID,eventType,5001);
						System.out.println(Mtl_Con);
						System.out.println(Otwmsg);
						System.out.println(Tormsg);
						msg="EVENT HAS BEEN REMOVED."; 
						System.out.println(msg);
	                    //log.log(Level.INFO, "Event{0} Removed EventType {1}", new Object[]{ eventID, eventType});
	                    return msg;
					}
					else
					{
						msg="ENTER THE VALID EVENTID.";
						return msg;
					}
				}
				else
				{
					msg="THIS EVENT IS NOT REGISTERED.";	
					return msg;
				}

             }
			else
			{
				msg="THIS EVENT IS NOT REGISTERED.";	
				return msg;
			}
		}
	
		else
		{
			msg="ENTER THE VALID EVENTID.";
			return msg;
		}
	}  
    
    private String removeEvent1(String eventID, String eventType) {
		String msg=null;
		String month=eventID.substring(6,8);
		int m=Integer.parseInt(month);
		if(eventType.equalsIgnoreCase("TS")) {
			System.out.println(Mtl_Ts);
			for (Map.Entry<String, ArrayList<String>> entry : Mtl_Ts.entrySet()) {
				if(Mtl_Ts.get(entry.getKey()).contains(eventID)) {
					Mtl_Ts.get(entry.getKey()).remove(eventID.trim().toString());
				System.out.println(Counter);
				Counter.get(entry.getKey()).set(m-1, Counter.get(entry.getKey()).get(m-1)-1);
				System.out.println(Counter);
				}					
		}
			System.out.println(Mtl_Ts);
			msg="EVENT HAS BEEN REMOVED.";
	}
		else if(eventType.equalsIgnoreCase("SEM")) {
			System.out.println(Mtl_Sem);
			for (Map.Entry<String, ArrayList<String>> entry : Mtl_Sem.entrySet()) {
				if(Mtl_Sem.get(entry.getKey()).contains(eventID)) {
					Mtl_Sem.get(entry.getKey()).remove(eventID.trim().toString());
					System.out.println(Counter);
					Counter.get(entry.getKey()).set(m-1, Counter.get(entry.getKey()).get(m-1)-1);
					System.out.println(Counter);
					}
				}	
			System.out.println(Mtl_Sem);
			msg="EVENT HAS BEEN REMOVED.";
			
		}
		else if(eventType.equalsIgnoreCase("CON")){
			System.out.println(Mtl_Con);
			for (Map.Entry<String, ArrayList<String>> entry : Mtl_Con.entrySet()) {
				if(Mtl_Con.get(entry.getKey()).contains(eventID)) {
					Mtl_Con.get(entry.getKey()).remove(eventID.trim().toString());
					System.out.println(Counter);
					Counter.get(entry.getKey()).set(m-1, Counter.get(entry.getKey()).get(m-1)-1);
					System.out.println(Counter);
				}	
			}
			System.out.println(Mtl_Con);
			msg="EVENT HAS BEEN REMOVED.";
		}
		else
		{
			msg="ENTER THE VALID EVENTTYPE.";
		}

		return msg;
	}


	public String listEventAvailability(String eventType) {

		String rly="";
		String msg="";
		System.out.println("Inside list availability");
		if(eventType.matches("SEM"))
	      {
			for(Entry<String, HashMap<String, Integer>> map2:MTLEvents.entrySet())
			 {
				 msg=MTLEvents.get("SEM").toString();
				
			 }
			 System.out.println("tor");
			 String Torevent = call_other1("ListEventAvailability","null",eventType,5001);
			 System.out.println("otw");
			 String Otwevent = call_other1("ListEventAvailability","null",eventType,6001);
			System.out.println(msg);
			rly = msg+"\n"+Torevent+"\n"+Otwevent;
			rly.trim();
			System.out.println(rly);
		  }
		else if(eventType.matches("CON"))
		  {
			for(Entry<String, HashMap<String, Integer>> map:MTLEvents.entrySet())
			 {
				 msg=MTLEvents.get("CON").toString();
			 }
			 String Torevent = call_other1("ListEventAvailability","null",eventType,5001);
			 String Otwevent = call_other1("ListEventAvailability","null",eventType,6001);
			 rly = msg+"\n"+Torevent+"\n"+Otwevent;
			 rly.trim();
			 System.out.println(rly);
		  }
		else if(eventType.matches("TS"))
          {
    		for(Entry<String, HashMap<String, Integer>> map1:MTLEvents.entrySet()) 
    		{
   			 msg=MTLEvents.get("TS").toString();
    		}
    		String Torevent = call_other1("ListEventAvailability","null",eventType,5001);
 			String Otwevent = call_other1("ListEventAvailability","null",eventType,6001);
 			rly = msg+"\n"+Torevent+"\n"+Otwevent;
 			rly.trim();
 			System.out.println(rly);
		  }
		else
		{
			rly="ENTER THE VALID EVENTTYPE.";
			System.out.println(rly);
		}

			
		return rly;
	}
	
	private String listEventAvailability1(String eventType) {

		String rly="";
		String msg="";
		System.out.println("Inside list availability1");
		if(eventType.matches("SEM"))
	      {
			for(Entry<String, HashMap<String, Integer>> map2:MTLEvents.entrySet())
			 {
				 msg=MTLEvents.get("SEM").toString();
			 }			 
			 rly = msg;
		  }
		else if(eventType.matches("CON"))
		  {
			for(Entry<String, HashMap<String, Integer>> map:MTLEvents.entrySet())
			 {
				 msg=MTLEvents.get("CON").toString();
			 }
			 rly = msg;
		  }
		else if(eventType.matches("TS"))
          {
    		for(Entry<String, HashMap<String, Integer>> map1:MTLEvents.entrySet()) 
    		{
   			 msg=MTLEvents.get("TS").toString();
    		}
 			rly = msg;
		  }
		else
		{
			rly="ENTER THE VALID EVENTTYPE.";
		}
			
		return rly;
	}


	
	public synchronized String bookEvent(String customerID, String eventID, String eventType)
	{
		String rly="";
		System.out.println("In Montreal book Event");
		if(eventID.substring(0,3).equalsIgnoreCase("MTL"))
		{
			System.out.println("1");
			if(eventID.substring(0,4).equalsIgnoreCase("MTLE")||eventID.substring(0,4).equalsIgnoreCase("MTLM")|| eventID.substring(0,4).equalsIgnoreCase("MTLA"))
			{
				System.out.println("2");
			  if(customerID.substring(0,4).equalsIgnoreCase("MTLC"))
			  {
				  System.out.println("3");
				if(eventType.equalsIgnoreCase("CON")) 
				{
					if(alreadyPresent(customerID,eventID,eventType))
					{
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
					}
					else 
					{
						if(MTLEvents.get(eventType).containsKey(eventID)) 
						{
							System.out.println("ddddddddddd");
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0) 
							{
								Counter.putIfAbsent(customerID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0)));
									Mtl_Con.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Con.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									rly="BOOKING IS SUCCESSFUL.";
                                 //   log.log(Level.INFO, "Customer with ID: {0} book in Event {1} For {2}", new Object[]{customerID, eventID, eventType});

									System.out.println(Mtl_Con);
									System.out.println(MTLEvents);
									System.out.println(Mtl_Con.get(customerID).contains(eventID));
									return rly;
							}
							else
							{
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else {
							rly="ENTER THE VALID EVENTID.";
							return rly;
					    }   
			     }					
			 }
				else if(eventType.equalsIgnoreCase("TS")) 
				{
					if(alreadyPresent(customerID,eventID,eventType))
					{
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
				     }
					else {
						if(MTLEvents.get(eventType).containsKey(eventID)) {
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0) 
							{
							    Counter.putIfAbsent(customerID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0)));
									Mtl_Ts.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Ts.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									System.out.println(MTLEvents);
									rly="BOOKING IS SUCCESSFUL.";
									return rly;
							}
							else
							{
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else {
							rly="ENTER THE VALID EVENTID.";
							return rly;
					       }
					}
					
				}
				else if(eventType.equalsIgnoreCase("SEM")) {
					System.out.println("4");
					if(alreadyPresent(customerID,eventID,eventType)){
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
					}
					else {
						System.out.println("5");
						if(MTLEvents.get(eventType).containsKey(eventID)) {
							System.out.println("6");
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0)
							{
								System.out.println("7");
									Mtl_Sem.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Sem.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									System.out.println(MTLEvents);
									rly="BOOKING IS SUCCESSFUL.";
									return rly;
							}
							else
							{
								System.out.println("8");
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else 
						{
							System.out.println("9");
							rly="ENTER THE VALID EVENTID.";
							return rly;
						}
					}					
				}
				else
				{
					System.out.println("10");
					rly="ENTER THE VALID EVENTID.";
					return rly;
				}
			}
			else 
			{
				rly="ENTER THE VALID CUSTOMERID.";
				return rly;
			}
		}
			else {
				rly="ENTER THE VALID EVENTID.";
				return rly;
			}
			
		
	}
	
		else if(eventID.substring(0,3).equalsIgnoreCase("OTW") || (eventID.substring(0,3).equalsIgnoreCase("TOR"))) {
			System.out.println("going to different Server 3");
			Counter.putIfAbsent(customerID,new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0)));
			String month=eventID.substring(6,8);
			int m=Integer.parseInt(month);
			int count=Counter.get(customerID).get(m-1);
			System.out.println("Count: "+count);
			if(count>2) {
				rly="YOU HAVE ALREADY REGISTERED 3 EVENTS OUTSIDE MONTREAL.";
				return rly;
			}
			if(eventID.charAt(0)=='O')
			{
				port=6001;
				String purpose="BookEvent";
				System.out.println("Sending request from montreal to ottawa");
				String msg=call_other(purpose,customerID,eventID,eventType,port);
				System.out.println(msg);
				if(msg.equalsIgnoreCase("Enrolled"))
				{
					System.out.println("Enroll: "+msg);
					Counter.get(customerID).set(m-1, Counter.get(customerID).get(m-1)+1);
					System.out.println(Counter);
					if(eventType.equalsIgnoreCase("SEM")) 
					 {
						Mtl_Sem.putIfAbsent(customerID, new ArrayList<>());
						Mtl_Sem.get(customerID).add(eventID);
						System.out.println(Mtl_Sem);
					 }
					if(eventType.equalsIgnoreCase("CON")) 
					 {
							Mtl_Con.putIfAbsent(customerID, new ArrayList<>());
							Mtl_Con.get(customerID).add(eventID);
							System.out.println(Mtl_Con);
					  }
					if(eventType.equalsIgnoreCase("TS")) 
					 {
								Mtl_Ts.putIfAbsent(customerID, new ArrayList<>());
								Mtl_Ts.get(customerID).add(eventID);
								System.out.println(Mtl_Ts);
					 }
					rly="BOOKING IS SUCCESSFUL.";
					return rly;	
				}
				else {
					rly="FAILED TO BOOK THE EVENT.";
					return rly;
				}
				
			}
			if(eventID.charAt(0)=='T')
			{
				port=5001;
				String purpose="BookEvent";
				System.out.println("Sending request from montreal to ottawa");
				String msg=call_other(purpose,customerID,eventID,eventType,port);
				System.out.println(msg);
				if(msg.equalsIgnoreCase("Enrolled")) 
				{
					System.out.println("Enroll: "+msg);
					Counter.get(customerID).set(m-1, Counter.get(customerID).get(m-1)+1);
					System.out.println(Counter);
					if(eventType.equalsIgnoreCase("SEM")) 
					  {
						System.out.println(Mtl_Sem);
						Mtl_Sem.putIfAbsent(customerID, new ArrayList<>());
						System.out.println(Mtl_Sem);
						Mtl_Sem.get(customerID).add(eventID);
						System.out.println(Mtl_Sem);
					  }
					if(eventType.equalsIgnoreCase("CON")) 
					 {
							System.out.println(Mtl_Con);
							Mtl_Con.putIfAbsent(customerID, null);
							Mtl_Con.get(customerID).add(eventID);
					 		System.out.println(Mtl_Con);
					 }
				   if(eventType.equalsIgnoreCase("TS"))
				    {
								System.out.println(Mtl_Ts);
								Mtl_Ts.putIfAbsent(customerID, null);
								Mtl_Ts.get(customerID).add(eventID);
								System.out.println(Mtl_Ts);
				       }
				   rly="BOOKING IS SUCCESSFUL.";
					return rly;	
		}
		else {
			rly="FAILED TO BOOK THE EVENT.";
			return rly;
		}
		
	}
}
else
{
	rly="ENTER THE VALID EVENTID.";
	return rly;
    }
	rly="ENTER THE VALID EVENTID.";
	return rly;
}

	private String book(String eventID, String customerID, String eventType) {
		String rly="";
		System.out.println("In montreal book Event");
				
				if(eventType.equalsIgnoreCase("CON"))
				{
					if(alreadyPresent(customerID,eventID,eventType)){
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
					}
					else 
					{
						if(MTLEvents.get(eventType).containsKey(eventID))
						{
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0)
							{
									Mtl_Con.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Con.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									rly="BOOKING IS SUCCESSFUL.";
									System.out.println(MTLEvents);
									return rly;
							}
							else
							{
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else
						{
							rly="ENTER THE VALID EVENTID.";
							return rly;
						}
					}
				}
				else if(eventType.equalsIgnoreCase("TS")) {
					if(alreadyPresent(customerID,eventID,eventType)){
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
					}
					else {
						if(MTLEvents.get(eventType).containsKey(eventID)) 
						{
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0) {
									Mtl_Con.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Con.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									rly="BOOKING IS SUCCESSFUL.";
									System.out.println(MTLEvents);
									return rly;
							}
							else
							{
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else
						{
							rly="ENTER THE VALID EVENTID.";
							return rly;
						}
					}
					
				}
				else if(eventType.equalsIgnoreCase("SEM")) {
					if(alreadyPresent(customerID,eventID,eventType)){
						rly="YOU HAVE ALREADY BOOKED THIS EVENT.";
						return rly;
					}
					else {
						if(MTLEvents.get(eventType).containsKey(eventID)) {
							int eve=MTLEvents.get(eventType).get(eventID);
							if(eve>0) {
									Mtl_Con.putIfAbsent(customerID, new ArrayList<>());
									Mtl_Con.get(customerID).add(eventID);
									MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)-1);
									rly="BOOKING IS SUCCESSFUL.";
									System.out.println(MTLEvents);
									return rly;
							}
							else
							{
								rly="THIS EVENT IS FULL.";
								return rly;
							}
						}
						else
						{
							rly="ENTER THE VALID EVENTID.";
							return rly;
						}
					}
					
				}
				else
					rly="ENTER THE VALID EVENTID.";
				
		return rly;
	}
	
	
	private boolean alreadyPresent(String customerID, String eventID, String eventType) {
		for(Map.Entry<String, ArrayList<String>> pair:Mtl_Con.entrySet()) {
			if(pair.getKey().equals(customerID)&&pair.getValue().contains(eventID)) {
				return true;
			}
		}
		for(Map.Entry<String, ArrayList<String>> pair : Mtl_Ts.entrySet()) {
			if(pair.getKey().equals(customerID)&&pair.getValue().contains(eventID)) {
				return true;
			}
		}
		for(Map.Entry<String, ArrayList<String>> pair : Mtl_Sem.entrySet()) {
				if(pair.getKey().equals(customerID)&&pair.getValue().contains(eventID)) {
					return true;
				}
		}
			return false;
	}
	
	public String getBookingSchedule(String customerID) 
	{
System.out.println("In method");
	    String msg="EVENTS BOOKED : \nConferences: "+Mtl_Con.get(customerID)+	"\nSeminars: " +Mtl_Sem.get(customerID)+   	"\nTradeShows: " +Mtl_Ts.get(customerID);
	    msg=msg.replace("null", "[]");
	    return msg;
	}


public synchronized String cancelEvent(String customerID, String eventID, String eventType)
{
		String msg = null;
	if(Mtl_Sem.containsKey(customerID)||Mtl_Ts.containsKey(customerID)||Mtl_Con.containsKey(customerID)) 
	{
		System.out.println("in cancel event");
		if(eventID.charAt(0)=='T')
        {   
           port=5001;
           String purpose="Cancel";  
			String rly=call_other1(purpose,eventID,eventType,port);
			System.out.println(rly);
			if(rly.trim().toString().equalsIgnoreCase("Y"))
			{      	   
				String month=eventID.substring(6,8);
				int m=Integer.parseInt(month);
				if(eventType.equalsIgnoreCase("SEM")) {
					Mtl_Sem.get(customerID).remove(eventID);
				}
				else if(eventType.equalsIgnoreCase("CON")) {
					Mtl_Con.get(customerID).remove(eventID);
				}
				else if(eventType.equalsIgnoreCase("TS")) {
					Mtl_Ts.get(customerID).remove(eventID);	
				}
					Counter.get(customerID).set(m-1, Counter.get(customerID).get(m-1)-1);
					System.out.println(Counter);
					msg="EVENT HAS BEEN CANCELED.";
                    //log.log(Level.INFO, "Customer with ID: {0} cancel in Event {1} For {2}", new Object[]{customerID, eventID, eventType});
                     return msg;
				}
         else{  
    	      return msg;
              }
        }
		else if(eventID.charAt(0)=='O')
        {   
			System.out.println("IN MTL- OTAWA CANCEL");
			port=6001;
			String purpose="Cancel"; 
			String rly=call_other1(purpose,eventID,eventType,port);
			System.out.println(rly);
			if(rly.trim().toString().equalsIgnoreCase("Y")){   
				System.out.println("BACK FROM OTTAWA");
				String month=eventID.substring(6,8);
				System.out.println(month);
				int m=Integer.parseInt(month);
				System.out.println(m);
				if(eventType.equalsIgnoreCase("SEM")) {
					Mtl_Sem.get(customerID).remove(eventID);
				}
				else if(eventType.equalsIgnoreCase("CON")) {
					Mtl_Con.get(customerID).remove(eventID);
				}
				else if(eventType.equalsIgnoreCase("TS")) {
					Mtl_Ts.get(customerID).remove(eventID);	
				}
					Counter.get(customerID).set(m-1, Counter.get(customerID).get(m-1)-1);
					System.out.println(Counter);
					msg="EVENT HAS BEEN CANCELED.";
					return msg;
				}
          else{ 
        	  return msg;
               }
          }
       else if(eventID.charAt(0)=='M')
       {
           if(MTLEvents.get(eventType).containsKey(eventID))
           {   
        	   if(eventType.equalsIgnoreCase("CON")) 
        	   {
               	   if(Mtl_Con.containsKey(customerID))
               	   { 
               		   if(Mtl_Con.get(customerID).contains(eventID))
               		   {
         	             Mtl_Con.get(customerID).remove(eventID);
         	             MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)+1);
         	            msg="EVENT HAS BEEN CANCELED.";
               		   }
            		   else {
            			   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
            			   }
            	   	}
            	   else {
            		   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
                      }
        	   }
        	   else if(eventType.equalsIgnoreCase("TS"))
        	   {
               	   if(Mtl_Ts.containsKey(customerID)){ 
               		   if(Mtl_Ts.get(customerID).contains(eventID)) 
               		   {
               			   Mtl_Ts.get(customerID).remove(eventID);
               			   MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)+1);
               			msg="EVENT HAS BEEN CANCELED.";
               		   }
            		   else {
            			   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
            			   }
            	   	}
            	   else {
            		   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
                      }
            }
             else if(eventType.equalsIgnoreCase("SEM"))
             {
            	   if(Mtl_Sem.containsKey(customerID)){ 
            		   if(Mtl_Sem.get(customerID).contains(eventID))
            		   {
            			   Mtl_Sem.get(customerID).remove(eventID);
            			   MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)+1);
            			   msg="EVENT HAS BEEN CANCELED.";
               		   }
            		   else {
            			   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
            			   }
            	   	}
            	   else {
            		   msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
                      }
             }
             else 
             {
            	 msg="ENTER THE VALID EVENTTYPE.";
             }
           }
           else 
           {
           msg="EVENT DOES NOT EXIST.";
           }
		}
       else
       {
    	   msg="ENTER THE VALID EVENTID.";
       }
	
		}
		else {
		msg="CUSTOMER NOT REGISTERED FOR THIS EVENT.";
		}
		return msg;
	}
	public String cancelEvent1(String eventID,String eventType)
	{
		String msg;
    	   System.out.println("Inside cancel 1");
           if(MTLEvents.get(eventType).containsKey(eventID))
           {   
         	           MTLEvents.get(eventType).put(eventID,MTLEvents.get(eventType).get(eventID)+1); 
         	           System.out.println(MTLEvents);
     	              msg="Y"; 
	                   return msg;
	             }
	    
	     else
	           {          
	    	     msg="EVENT DOES NOT EXIST.";                         
	             return msg;
	           }
	}
	
	public String sayhello() {
		return "HELLO";
	}
	
	public void setORB(ORB orb_val) {
		orb=orb_val;
		inimain();
		Thread t=new Thread(()->{
			receive();
			
		});
		t.start();
		
		
	}
	
	
	public String swapEvent(String customerID, String newEventID, String newEventType, String oldEventID, String oldEventType)
	{
		
		String msg = null;
		System.out.println("In Swap Event");
		
		msg=cancelEvent(customerID,oldEventID,oldEventType);
		System.out.println(msg);
		if(msg.equalsIgnoreCase("EVENT HAS BEEN CANCELED."))
		{
			msg=bookEvent(customerID,newEventID,newEventType);
			if(msg.equalsIgnoreCase("BOOKING IS SUCCESSFUL.")) 
			{
				msg="SWAPPING SUCCESSFUL.";
				return msg;
		    }
		    else
		    {
			  String msg1=bookEvent(customerID,oldEventID,oldEventType);
				return "SWAP FAILED.";

			}
		}
		else
		{
			msg="SWAP FAILED.";
			return msg;
		}

		
	}



     

	public static void main(String[] args) 
	{		 
		System.out.println("Mtl Server Started...");
		Mtl_server ms = new Mtl_server();
		ms.inimain();
		Thread t=new Thread(()->{
			ms.receive();
			});
			t.start();
		while(true) {
			
			  MulticastSocket ms1=null;
	       	  DatagramSocket ds1=null;
	       	  while(true)
	       	  {
	       		  System.out.println("Receiving Replica Message and sending response to FE");
	       		  try
	       		  {
	    					ms1=new MulticastSocket(1700);
	    					ds1=new DatagramSocket(3004);
	    					InetAddress ip=InetAddress.getByName("230.0.0.1");
	    					ms1.joinGroup(ip);
	    					byte []buf=new byte[1000];
	    					String msg="";
	    					String reply="";
	    					DatagramPacket dp1=new DatagramPacket(buf,buf.length);
	    					ms1.receive(dp1);
	    					msg=new String(dp1.getData());
	    					System.out.println();
	    					System.out.println("MSG from Replica Manager: "+msg);
	    					StringTokenizer st=new StringTokenizer(msg,",");
	    					ms.unique_id=Integer.parseInt(st.nextToken());
	    					String id=st.nextToken();
	    					String method=st.nextToken();
	    					System.out.println("unique id:"+ms.unique_id+" ID:"+id+" Method:"+method);
	    					
	    					if(method.equals("BOOK"))
	    					{
	    						String eventID=st.nextToken();
	    						String eventType=st.nextToken();
	    						System.out.println("customerID:"+id+"courseId:"+eventID+" eventType:"+eventType);
	    						reply = ms.bookEvent(id.trim(),eventID.trim(),eventType.trim());
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}
	    					else if(method.equals("CANCEL"))
	    					{
	    						String eventID=st.nextToken();
	    						String eventType=st.nextToken();
	    						System.out.println(msg);
	    						reply=ms.cancelEvent(id.trim(),eventID.trim(),eventType.trim());
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}
	    					else if(method.equals("SWAP"))
	    					{
	    						String newEventID=st.nextToken();
	    						String newEventType=st.nextToken();
	    						String oldEventId=st.nextToken();
	    						String oldEventType=st.nextToken();
	    						reply=ms.swapEvent(id.trim(),newEventID.trim(),newEventType.trim(),oldEventId.trim(),oldEventType.trim());
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}
	    					else if(method.trim().equals("GETSCHEDULE"))
	    					{
	    								
	    						reply=ms.getBookingSchedule(id.trim());
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    						
	    					}
	    					else if(method.equals("ADD"))
	    					{
	    						String eventID=st.nextToken();
	    						String eventType=st.nextToken().trim();
	    						int cap=Integer.parseInt(st.nextToken().trim());
	    						reply=ms.addEvent(id.trim(),eventID.trim(),eventType.trim(),cap);
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}
	    					else if(method.equals("REMOVE"))
	    					{

	    						String eventID=st.nextToken();
	    						String eventType=st.nextToken();
	    						reply=ms.removeEvent(eventID.trim(),eventType.trim());
	    						System.out.println(reply);
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}
	    					else if(method.equals("LIST"))
	    					{
	    						String eventType=st.nextToken();
	    						reply=ms.listEventAvailability(eventType.trim());
	    						InetAddress ip1=InetAddress.getByName("localhost");
	    						dp1=new DatagramPacket(reply.getBytes(),reply.length(),ip1,7002);
	    						ds1.send(dp1);
	    					}	
	       		  }
	       		  catch(Exception e)
	       		  {
	       			  System.out.println("Thread Exception: "+e.getMessage());
	       		  }
	       		  finally
	       		  {
	       			  if(ds1!=null)
	       				  ds1.close();
	       			  if(ms1!=null)
	       				  ms1.close();
	       		  }
	       	  }	
				
				
			}
	   }


}

