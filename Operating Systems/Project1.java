import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Project1 {
	
	
	static Scanner inputtedCmd = new Scanner(System.in);
	static String cmd;
	static String cmdLit;
	static String bckCmd;
	static boolean exitShell = false;
	static String [] commandList = new String[1];
	
	public static void main(String[] args) throws IOException {
		
		while(!exitShell){
			
			System.out.print("miak1389> ");
			cmd = inputtedCmd.nextLine();
			cmdLit = cmd;
			cmd = cmd.trim().replaceAll("\\s{2,}", " ");
			cmd = cmd.toLowerCase();
	
			if (cmd.length()<1) {
				continue;
			}	
			
			if(cmd.length()>5){
				if(cmd.substring(0,5).equals("echo ")){
					echo(cmdLit.substring(5));
					continue;
					}
				}
			else if(cmd.length()>3){
					if(cmd.equals("echo")){
						echo();		
						continue;
						}
			}
			
			if(cmd.equals("exit")){
				exitShell = true;
				continue;
			}
			else if(cmd.equals("pause")){
				pause();
				continue;
			}
			else if(cmd.equals("help")){
			help();
			}
			
			else if(cmd.equals("clear")){
				clear();
			}
			else{
				commandList[0] = cmd;
				ProcessBuilder pb;
				if(cmd.substring(cmd.length()-1).equals("@")){
					bckCmd = cmd.substring(0,cmd.length()-1);
					pb = new ProcessBuilder(bckCmd);
				}
				else{
					pb = new ProcessBuilder(cmd);
				}
					Process dummyProcess; 
				try{
					if(cmd.substring(cmd.length()-1).equals("@")){
					dummyProcess = pb.start();
					continue;
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
						continue;
				}
				
					InputStream str1 = dummyProcess.getInputStream();
					InputStreamReader strReader1 = new InputStreamReader(str1);
					BufferedReader reader1 = new BufferedReader(strReader1);
					String getOutput;
					
						while((getOutput = reader1.readLine()) != null)
						{
							System.out.println(getOutput);
						} 
						reader1.close();
					}
				}
			}
	
	static public void clear(){
		
		
		  for(int clear = 0; clear < 1000; clear++)
		  {
		     System.out.print("\n");
		  }
		  
	}
	static public void echo(){
		System.out.println();
	}
	static public void echo(String comment){
		System.out.println(comment);
	}
	static public void pause(){
		cmd = inputtedCmd.nextLine();
	}
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
	}

	