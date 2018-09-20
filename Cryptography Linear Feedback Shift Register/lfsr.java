package ps3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class lfsr {
	static int totalRuns;
	static int tWays = 0;
	static int tOnes = 0;
	int eTaps[];
    int size;
    int tapsCount = 0;
    boolean allPerms [];
  static boolean problemString[]= {false,false,true,true,false,true,false,false,true,false};
    boolean lfsr1[];
    boolean nextBit[] = {true};
    char taps1[];
    static int tp = 0;
    static String filename = "rngOutput.txt";
   
    
    lfsr(){}
    
    lfsr(int s1,boolean initialFill[],int tap){
    	intitializeLfsr(s1,initialFill,tap);
    }
    
    lfsr(int s1,boolean initialFill[],char rd[]){
      
        intitializeLfsr(s1,initialFill,rd);
        
    }

    boolean isValid(){
    	
    	boolean count = false;
    	for(int i = 0;i<allPerms.length;i++){
    		if(allPerms[i])
    			count = true;
    	}
    	if(count)
    		return true;
    	else 
    		return false;
    	
    }

    //Method I used to solve Problem 1
    
	public void problem1(int n) {
		//Base Case where new permutation is complete in variable allPerms
		if (n <= 0) {
			//Removal of all cases of initial fills starting will 11 or 0011
			if(!(allPerms[0]==true&&allPerms[1]==true)){
				if(!(allPerms[0]==false&&allPerms[1]==false&&allPerms[3]==true&&allPerms[4]==true)){
			//Lazy way of checking for all 0's
					if(isValid()){
			lfsr lfsr2 = new lfsr(size,allPerms,taps1);
			//Running tally's of all possible ways to get the problem string
			if(areEqual(lfsr2.outputSelfShrinking(10),problemString)){
	            tWays+=1;
	            //Running total of amount of ones after the problem string
	           if(lfsr2.outputSelfShrinking(1)[0]){
	                tOnes+=1;
	                }
			}
			//The Reverse of the tap polynomial used with the same current initial fill
			lfsr2 = new lfsr(size,allPerms,taps1);
			if(areEqual(lfsr2.revOutputSelfShrinking(10),problemString)){
	            tWays+=1;
	            if(lfsr2.revOutputSelfShrinking(1)[0]){
	                tOnes+=1;
	                }
			}
		}
			}
		}
			}
		//Recursive part of the program to obtain all initial fills
		 else {
			allPerms[n - 1] = false;
			problem1(n - 1);
			allPerms[n - 1] = true;
			problem1(n - 1);
		}
	}
    
public static long exponent(long base,long exp){
	long result = 1;
	while(exp!=0){
		result = result*base;
		exp-=1;
	}
	return result;
}
    
void intitializeLfsr (int s1,boolean initialFill[],char rd[]){
    size = s1;
    eTaps = new int[s1];
    taps1 = rd;
    determineTaps(rd);
    lfsr1 = initialFill;
    allPerms = new boolean [size];
    
}
void intitializeLfsr (int s1,boolean initialFill[],int tap){
    size = s1;
    lfsr1 = initialFill;
    allPerms = new boolean [size];
    tp = tap;
}


void determineTaps(char read[]){

	
	int i = 6;
	while(read[i]!=']'){
		if(Character.isDigit(read[i])){
			if(Character.isDigit(read[i+1])){
				String mrg = "";
				mrg+=read[i];
				mrg+=read[i+1];
			eTaps[tapsCount] = Integer.parseInt(mrg);
				tapsCount+=1;
				i+=2;
				continue;
			}
			else{
				String mrg = "";
				mrg+=read[i];
			eTaps[tapsCount] = Integer.parseInt(mrg);
				tapsCount+=1;
				i+=1;
				continue;
			}
		}
		
		i+=1;
				
			}
}

boolean tap(){
 	
 boolean t1 = lfsr1[0];
 boolean t2 = lfsr1[size-eTaps[0]];
 boolean input = t1^t2;
 
 for(int i =1;i<tapsCount;i++){
	
	 t2 = lfsr1[size-eTaps[i]];
	 input = input^t2;
	 
 }
    return input;
}
boolean tap2(){
	 boolean t1 = lfsr1[0];
	 boolean t2 = lfsr1[tp];
	 boolean input = t1^t2;
	 return input;
	}

boolean tap2(int tp3,int tp4){
	 boolean t1 = lfsr1[0];
	 boolean t2 = lfsr1[tp];
	 boolean input = t1^t2;
	 input = input^lfsr1[tp3];
	 input = input^lfsr1[tp4];
	 return input;
	}

boolean revTap(){
	boolean t1 = lfsr1[0];
	 boolean t2 = lfsr1[eTaps[0]];
	 boolean input = t1^t2;
	 
	 for(int i =1;i<tapsCount;i++){
		
		 t2 = lfsr1[eTaps[i]];
		 input = input^t2;
		 }
	 
	 return input;
}
void clock(int clockAm){
    
   boolean[] next = new boolean[lfsr1.length];
    
   for(int j = 0;j<clockAm;j++){ 
        boolean tp = tap2();
    for(int i =0;i<size;i++){
        if(i==size-1){
            next[i] = tp;
        }
        else
            next[i] = lfsr1[i+1];
    }
   }
   lfsr1 = next;
}
void clock(int clockAm,int tp3,int tp4){
    
	   boolean[] next = new boolean[lfsr1.length];
	    
	   for(int j = 0;j<clockAm;j++){ 
	        boolean tp = tap2(tp3,tp4);
	    for(int i =0;i<size;i++){
	        if(i==size-1){
	            next[i] = tp;
	        }
	        else
	            next[i] = lfsr1[i+1];
	    }
	   }
	   lfsr1 = next;
	}
void revClock(int clockAm){
    
	   boolean[] next = new boolean[lfsr1.length];
	    
	   for(int j = 0;j<clockAm;j++){ 
	        boolean tp = revTap();
	    for(int i =0;i<size;i++){
	        if(i==size-1){
	            next[i] = tp;
	        }
	        else
	            next[i] = lfsr1[i+1];
	    }
	   }
	   lfsr1 = next;
	}
void print(){
	for(int i = 0;i<lfsr1.length;i++){
		System.out.print(lfsr1[i]);
	}
}
boolean[] outputSelfShrinking(int amount){
  
    int i = 0;
    int outputCount = 0;
    boolean outputString[] = new boolean[amount];
    
    while(i != amount){
        if(lfsr1[0]== true){
            outputString[outputCount] = lfsr1[1];
            outputCount+=1;
            i+=1;
            clock(2);
        }
        else{
            clock(2);
        }
    }
    return outputString;
}
boolean[] revOutputSelfShrinking(int amount){
	  
    int i = 0;
    int outputCount = 0;
    boolean outputString[] = new boolean[amount];
    
    while(i!= (amount)){
        if(lfsr1[0]== true){
            outputString[outputCount] = lfsr1[1];
            outputCount+=1;
            i+=1;
            revClock(2);
       
        }
        else{
            revClock(2);
        }
    }
    
    return outputString;
}

boolean[] customLfsr(int amount){
	
	//The Initial Fills
	boolean ini1[] = {true,true,true,true,false,false,false,true,true,false,true,true,false,false,false,false,true,true};
	boolean ini2[] = {true,false,true,false,false,true,true,false,true,true,false,false,true,false,true,false,false,true};
	boolean ini3[] = {true,false,true,false,true,false,true,true,false,true,true,false,false,false,true,false,false,true};
	boolean ini4[] = {true,true,true,false,false,true,false,true,true,false,true,true,false,false,false,false,true,true};
	
	//The Lfsr initializations
	
	lfsr lfsrc1 = new lfsr(18,ini1,1);
	lfsr lfsrc2 = new lfsr(18,ini2,1);
	lfsr lfsrc3 = new lfsr(18,ini3,1);
	lfsr Decider = new lfsr(18,ini1,7);
	
	//Needed Variables
	
	int i = 0;
	int index = 0;
    int outputCount = 0;
    boolean outputString[] = new boolean[amount];
    
    //Main Process of determining output
    
	while(i!=amount){
	if(Decider.lfsr1[0]==true){
		index = lfsrc1.lfsr1.length/2;
		outputString[outputCount] = minority(lfsrc1,lfsrc2,lfsrc3,lfsrc1.lfsr1.length-1)^ majority(lfsrc1,lfsrc2,lfsrc3,index);
		outputCount+=1;
		lfsrc1.clock(2,2,13);
		lfsrc2.clock(2,2,5);
		lfsrc3.clock(2,2,8);
		Decider.clock(2);
        i+=1;
	}
	else{
		index = lfsrc1.lfsr1.length/4;
		boolean x = minority(lfsrc1,lfsrc2,lfsrc3,lfsrc1.lfsr1.length-1)^ majority(lfsrc1,lfsrc2,lfsrc3,0);
		outputString[outputCount] = x^(minority(lfsrc1,lfsrc2,lfsrc3,index)^ majority(lfsrc1,lfsrc2,lfsrc3,lfsrc1.lfsr1.length-2));
		outputCount+=1;
		lfsrc1.clock(3);
		lfsrc2.clock(3,2,5);
		lfsrc3.clock(3);
		Decider.clock(3);
        i+=1;
	}
	}

	return outputString;
}

boolean majority(lfsr l1,lfsr l2,lfsr l3,int index){
	
	int ones = 0;
	int zeroes = 0;
	
	if(l1.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	
	if(l2.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	
	if(l3.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	
	if(ones>zeroes){
		return true;
	}
	else
		return false;
}
boolean minority(lfsr l1,lfsr l2,lfsr l3,int index){
	int ones = 0;
	int zeroes = 0;
	
	if(l1.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	if(l2.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	if(l3.lfsr1[index]== true)
		ones+=1;
	else
		zeroes+=1;
	if(ones>zeroes){
		return false;
	}
	else
		return true;
}

boolean areEqual(boolean one[],boolean two[]){
	boolean flag = true;;
	for(int i = 0;i<two.length;i++){
		if(one[i]!=two[i])
			flag = false;
	}
	return flag;
}

public static void main(String[] args) {
	
	final long startTime = System.currentTimeMillis();
	boolean ini[] = new boolean[18];
	FileInputStream fstream = null;
	try {
		fstream = new FileInputStream("prims.txt");
	} catch (FileNotFoundException e) {
		System.out.println("File not found.");
	}
	Scanner reader = new Scanner(fstream);
	
	while(reader.hasNext()){
		String tp = reader.nextLine();
		System.out.println(tp);
		char tp1[] = tp.toCharArray();
		lfsr lfsrP1 = new lfsr(18,ini,tp1);
		lfsrP1.problem1(18);
	}
	
	double answer = (double) tOnes/tWays;
	
	System.out.println("The answer is "+tOnes+"/"+tWays+" or "+answer+" in decimal form.");
	
	reader.close();
	
	final long endTime = System.currentTimeMillis();
	
	System.out.println("Total execution time: " + (endTime - startTime) );

	}
}


