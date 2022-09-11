import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback,cl,clearcl;
	
	ArrayList<String> allnames = new ArrayList<String>();
	
	
	Server(Consumer<Serializable> call,Consumer<Serializable> clists,Consumer<Serializable> clears){
	
		//to modify the listviews
		callback = call;
		cl=clists;
		clearcl=clears;
		
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
			synchronized(this) {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		    	ClientThread c = new ClientThread(mysocket.accept(), count);
		    	
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				System.out.println("client has connected to server: " + "client #" + count);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
		}
			}//end of while
		}
	
		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			String name;
			int ccounter =0;
			
			ClientThread(Socket s, int count)
			{
				this.connection = s;
				this.count = count;	
			}
			
			
			
			
			public void updateClients2(sendinfo message) 
			{
				synchronized(this) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					 out.reset();
					}
					catch(Exception e) {}
				}
				}
			}
			
			public void updateClients(sendinfo message,String[] n) 
			{
				synchronized(this) 
				{
				for(int j=0;j<n.length;j++) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					if(t.name.equals(n[j].toString())) {
					try {
					 t.out.writeObject(message);
					 out.reset();
					}
					catch(Exception e) {System.out.println("error in updatelcients");}
				}
				}
			}
			}
			}
			
			public void updateclist()
			{
				synchronized(this) {
				String temp="";
				allnames.clear();
				clearcl.accept(temp);
				for(int i=0; i<clients.size();i++) 
				{
					ClientThread t = clients.get(i);
					
					allnames.add(t.name);
					cl.accept(t.name);
				}
				}
				sendinfo cli=new sendinfo();
				cli.type=1;
				cli.cclist=allnames;
				updateClients2(cli);
				
			}
			
			
			
			public String parsemsg(String str) 
			{
				try {
				String[] p=str.split(":",2);
				str=p[1];
				}
				catch(Exception e) {}
				return str;
			}
			
			public String parsenames(String str) 
			{
				try {
					String[] p=str.split(":",2);
					
					str=p[0];
					return str;
					}
					catch(Exception e) {}
				String str1="all";
				return str1;
			}
			
			
			public String[] parsecom(String str)
			{
				String[]p;
				if(str!="all" && str!="All")
				{
					try {
					p=str.split(",");
					return p;
					}
				
					catch(Exception e)
					{
						
					}
					
				}
				else {
				p=new String[1];
				p[0]="all";
				return p;
				}
				
				p=new String[1];
				p[0]=str;
				
				return p;
			}
			
		
			public void run(){
				synchronized(this) {
					
				try {
					
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
					this.name=in.readObject().toString();
					cl.accept(this.name);
					allnames.add(this.name);
					sendinfo sender3=new sendinfo();
					sender3.type=1;
					sender3.cclist=allnames;
					updateClients2(sender3);
					
					
					
					sendinfo sender1=new sendinfo();
					sender1.type=2;
					sender1.msg="client #"+count+" is also known as "+this.name;
					callback.accept("client #" + count + " is also known as " + this.name);
			    	updateClients2(sender1);
					System.out.println("Streams open");
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				
				while(true) {
					
					    try {
					    	String data = in.readObject().toString();
					    	
					    	String n = parsenames(data);
					        data =parsemsg(data);
					    	
					        
					        if(n!="all"||n!="All"){n+=","+this.name;}
					    	String[] namesl=parsecom(n);
					    	
					    	
					    	
					    	
					    	callback.accept(this.name + " sent: " + data);
					    	sendinfo sender2=new sendinfo();
					    	sender2.type=2;
					    	sender2.msg=this.name+" said: "+data;
					    	if(namesl[0].toString().equals("all")) 
					    	{
					    	updateClients2(sender2);
					    	}
					    	else {
					    	updateClients(sender2,namesl);	
					    		
					    	}
					    	
					    	}
					    	
					    	catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count +" "+ name + "....closing down!");
					    	sendinfo sender4=new sendinfo();
					    	sender4.type=2;
					    	sender4.msg=this.name+" has left the server!";
					    	updateClients2(sender4);
					    	clients.remove(this);
					    	updateclist();
					    	break;
					    }
				
				 	}
			}
				}//end of run
			
			
			
		}//end of client thread
}


	
	

	
