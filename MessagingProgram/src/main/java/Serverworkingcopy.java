import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Serverworkingcopy{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback,cl,clearcl;
	
	ArrayList<String> allnames = new ArrayList<String>();
	sendinfo sender;
	
	Serverworkingcopy(Consumer<Serializable> call,Consumer<Serializable> clists,Consumer<Serializable> clears){
	
		//to modify the listviews
		callback = call;
		cl=clists;
		clearcl=clears;
		
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
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
			
			public void updateClients(String message) 
			{
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					 out.reset();
					}
					catch(Exception e) {}
				}
			}
			
			public void updateclist()
			{
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
			
			
		
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
					this.name=in.readObject().toString();
					cl.accept(this.name);
					allnames.add(this.name);
					
					callback.accept("client #" + count + " is also known as " + this.name);
			    	updateClients("client #"+count+" is also known as "+this.name);
					System.out.println("Streams open");
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
				while(true) {
					
					    try {
					    	
					    	String data = in.readObject().toString();
					    	callback.accept(this.name + " sent: " + data);
					    	updateClients(this.name+" said: "+data);
					    	System.out.println(this.name+" said: "+data);
					    	}
					    	
					    	catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count +" "+ name + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
					    	clients.remove(this);
					    	updateclist();
					    	break;
					    }
				
				 	}
			
				}//end of run
			
			
			
		}//end of client thread
}


	
	

	
