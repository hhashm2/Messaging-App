import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;



public class Client extends Thread{

	
	Socket socketClient;
	
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private Consumer<Serializable> cl;
	private Consumer<Serializable> clear;
	
	Client(Consumer<Serializable> call,Consumer<Serializable> clist,Consumer<Serializable> clearing){
	
		callback = call;
		cl=clist;
		clear=clearing;
	}
	
	public void run() {
		synchronized(this) {
		try {
		socketClient= new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
	    System.out.println("Conencted to Server");
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			sendinfo d = (sendinfo)in.readObject();
			if(d.type==2) {
			String message = d.msg;
			callback.accept(message);
			}
			else if(d.type==1) 
			{
				clear.accept(5);
				putincl(d.cclist);
				
			}
			}
			catch(Exception e) 
			{
				Platform.exit();
			}
		}
	}
	
    }
	
	
	public void putincl(ArrayList<String>x) 
	{
		
		int co=0;
		while(co<x.size())
		{
			cl.accept(x.get(co));
			co++;
			
		}
		
	}
	
	
	public void send(String data) {
		
		try {
	
			out.writeObject(data);
			out.reset();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Didnt send anything");
			//e.printStackTrace();
			
		}
	
	}


}
