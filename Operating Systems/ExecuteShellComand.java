
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ExecuteShellComand {

	public static void main(String[] args) {

		ExecuteShellComand cmd = new ExecuteShellComand();
		
		Scanner getCmd = new Scanner(System.in);
		String command;
		while(true){
			
		System.out.println("Please input a command");
		command =  getCmd.nextLine();
		if(command.equals("exit")||command.equals("EXIT")){
			
			System.out.println("Your command is "+command);
			System.out.println("Thank you for for using this Shell!");
			String output = cmd.executeCommand(command);
			System.out.println(output);
			
			break;
		}
		else if(getCmd.hasNextLine()){
			System.err.println("Please enter only one command line at a time, exiting");
			break;
		}
	
		else{
		
		String output = cmd.executeCommand(command);
		System.out.println(output);
		
		}
		}
		getCmd.close();

	}

	private String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

}