import java.net.*;
import java.io.*;

public class IPAddressResolver {

	public static void main (String args[]) {
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
			
			InetAddress myself = InetAddress.getLocalHost();
			System.out.println("MyAddress is :"+myself);	
			
			while (true) {
				System.out.print("host name or IP to resolve - <exit> to quit: ");
				String input = stdin.readLine();
				if (input.equalsIgnoreCase("exit"))
					break;
				
				InetAddress address = InetAddress.getByName(input);
				if (isHostName(input)) 
					System.out.println("IP Addrsss is: "+ address.getHostAddress());
				else 
					System.out.println("Host name is: "+ address.getHostName());
				
				System.out.println("addresses for "+input+ " are: ");
				InetAddress[] addresses = InetAddress.getAllByName(address.getHostName());
				for (int i = 0; i < addresses.length; i++) 
					System.out.println(addresses[i]);
			}
		}
		catch (UnknownHostException e) {
			System.out.println("Exception: "+e);
		}
		catch (Exception ex) {
			System.out.println("Exception: "+ex);
		}
	}

	private static boolean isHostName(String input) {	
		char[] ca = input.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (!Character.isDigit(ca[i]) && ca[i] != '.')
				return true;
		}
		return false;
	} 
}