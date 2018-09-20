import java.net.*;
import java.io.*;
import java.util.*;

public class EchoServer
{
	public static void main(String[] args)
	{
		int port = 9090;
		
		try {
	   	ServerSocket server = new ServerSocket(port);
		
			while(true) {
				System.out.println("Waiting for clients on port " + port);
				Socket client = server.accept();
				
       			System.out.println("Got connection from "+client.getInetAddress()+":"+client.getPort());
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter	writer = new PrintWriter(client.getOutputStream());
					
				writer.println("Welcome to my server");
				writer.flush();
					
				String	message = reader.readLine(); 
					
				while (!(message == null || message.equalsIgnoreCase("exit"))) {
					System.out.println("MessageReceived: "+message);
					writer.println(message);
					writer.flush();
						
					message = reader.readLine();
				}
				client.close();
			}
		} catch(Exception ex) {
			System.out.println("Connection error: "+ex);
		}
	}
}

