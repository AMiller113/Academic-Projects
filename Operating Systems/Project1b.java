import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Project1b {
	static Scanner inputtedCmd = new Scanner(System.in); //The Main input device for the mock shell.
	static String cmd; // The string that takes in the original command, will be edited after taking input to avoid errors.
	static String cmdLit; //The unedited version of the original command.
	static String bckCmd; // Will take in a command ending with @ with the @ truncated to avoid errors.
	static boolean exitShell = false; // Will exit the shell when turned true.
	static String [] commandList = new String[1]; //Needed for the updated ProcessBuilder constructor. 
	
	
	public static void main(String[] args) throws IOException {
		
		while(!exitShell){ 
			
			System.out.print("miak1389> "); //Command prompt main line.
			cmd = inputtedCmd.nextLine(); //Takes in Input
			cmdLit = cmd; //stores literal for proper appearance in echo command.
			String cmdLits [] = cmd.split(";"); // Splits the unedited command for use in echo command
			cmd = cmd.trim().replaceAll("\\s{2,}", " "); //Gets rid of any whitespace before and after the command, and gets rid of any white spaces greater than 1 in between words.
			cmd = cmd.toLowerCase(); //turns command to lower case to avoid case sensitive inconsistency
			String cmds[] = cmd.split(";"); // Splits commands up by the semicolon
		
	
			/* Avoids errors for enter presses when command line is empty */
			
			for(int i = 0;i<cmds.length;i++){
				
			
			cmd = cmds[i];
			cmd = cmd.trim().replaceAll("\\s{2,}", " ");
				
			if(cmd.length()<1) { 
				continue;
			}	
			/* Checks for non-empty echo command */
			
			if(cmd.length()>5){
				if(cmd.substring(0,5).equals("echo ")){
					echo(cmdLits[i].substring(5).trim().replaceAll("\\s{2,}", " "));
					continue;
					}
				}
			/* Checks for empty echo command */
			
			else if(cmd.length()>3){
					if(cmd.equals("echo")){
						echo();		
						continue;
						}
			}
			
			/* Checks for exit command. */
			
			if(cmd.equals("exit")){
				System.out.println("Logging out.");
				exitShell = true;
				continue;
			}
			
			/* Checks for pause command. */
			
			else if(cmd.equals("pause")){
				pause();
				continue;
			}
			
			/* Checks for help command. */
			
			else if(cmd.equals("help")){
			help();
			System.out.println();
			}
			
			/* Checks for clear command. Note: I'm using "clear" instead of "clr" due to "clr" not working on my bash terminal. */
			
			else if(cmd.equals("clear")){
				clear();
			}
			
			/* Generic Case for commands */
			
			else{
				Thread t1 = new Thread(new processThread());
				t1.start();
				try {
					t1.join();
				} catch (InterruptedException e) {
					System.out.println("The program has been interrupted.");
				}
					}
				}
		}
			}
	
	/* Clears output stream by printing blank lines*/
	
	static public void clear(){
		
		
		  for(int clear = 0; clear < 1000; clear++)
		  {
		     System.out.print("\n");
		  }
		  
	}
	
	/* Echo method for empty echo command
	 * 
	 * */
	
	static public void echo(){
		System.out.println();
	}
	
	/* Echo for normal echo <comment>
	 * 
	 */
	
	static public void echo(String comment){
		System.out.println(comment);
	}
	
	/*Pauses by waiting for input from user
	 * 
	 * 
	 */
	static public void pause(){
		cmd = inputtedCmd.nextLine();
	}
	
	/*Opens readme.txt in the default path
	 * 
	 * 
	 * 
	 */
	
	static public void help(){
		FileInputStream fstream;

		try {
			fstream = new FileInputStream("readme.txt");
		} catch (FileNotFoundException e) {
			System.err.println("The file does not exist or cannot be opened. ");
			return;
		}
		
		Scanner reader = new Scanner(fstream);
		
		while(reader.hasNext()){
			System.out.println(reader.nextLine());
		}
		reader.close();	
		}
	

	static class processThread implements Runnable{
		processThread(){
			
		}
		public void run(){
			commandList[0] = cmd;
			ProcessBuilder pb;
			
			
			/* Checks for background process command. */
			
			if(cmd.substring(cmd.length()-1).equals("&")){
				bckCmd = cmd.substring(0,cmd.length()-1);
				commandList[0] = bckCmd;
				pb = new ProcessBuilder(commandList);
			}
			/* Sequential process command. */
			else{
				commandList[0] = cmd;
				pb = new ProcessBuilder(commandList);
			}
				Process dummyProcess; 
				
				/*
				 * 
				 * Try/catch block keeps the program from ending prematurely due to an invalid command.
				 * 
				 * 
				 * */
				
			try{
				if(cmd.substring(cmd.length()-1).equals("&")){
				dummyProcess = pb.start();
				return;
				}
				else{
					dummyProcess = pb.start();
					dummyProcess.waitFor();
					}
			
			}
			catch(Exception e){
				System.err.println("A command does not exist or cannot be executed. ");
				try {
					TimeUnit.NANOSECONDS.sleep(100);
				} catch (InterruptedException e1) {}
					return;
			}
			
				InputStream str1 = dummyProcess.getInputStream();
				InputStreamReader strReader1 = new InputStreamReader(str1);
				BufferedReader reader1 = new BufferedReader(strReader1);
				String getOutput;
				
					try {
						while((getOutput = reader1.readLine()) != null)
						{
							System.out.println(getOutput);
						}
					} catch (IOException e) {
	
					} 
					try {
						reader1.close();
					} catch (IOException e) {
		
					}
		}
	 }
}

		
	
	
	
