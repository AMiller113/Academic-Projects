package edu.qc.seclass;

public class MyCustomString implements MyCustomStringInterface {

	private String my_string;
	  private static final String[] num_names = {"zero"," one"," two"," three"," four"," five"," six"," seven", " eight"," nine"};
	
	public String getString() {
		if(my_string == null)
			return null;
		else
			return my_string;
	}

	public void setString(String string) {
		my_string = string;
	}

	public int countNumbers() throws NullPointerException {
		
		if(my_string == null)
			throw new NullPointerException();
		
		int length_of_number = 0;
		int amount_of_numbers = 0;
	
		for(Character current_char :my_string.toCharArray()){
			if(current_char.isDigit(current_char)){
				length_of_number++;
				
				if(length_of_number == 1)
					amount_of_numbers++;
			}
			else
				length_of_number = 0;	
		}
		
		if(amount_of_numbers == 0)
			return 0;
		else 
			return amount_of_numbers;
	}

	public String getEveryNthCharacterFromBeginningOrEnd(int n,boolean startFromEnd) throws NullPointerException,IllegalArgumentException {
		
		if(n <= 0)
			throw new IllegalArgumentException();
		else if(my_string == null && n >0)
			throw new NullPointerException();
		else if(my_string.length() < n)
			return "";
		else{
			
			String new_string = "";
			char[] arr_my_string = my_string.toCharArray();
			
			if(!(startFromEnd)){
				while(n < arr_my_string.length){
					new_string+= Character.toString(arr_my_string[n-1]);
					n += n;
					}
				}
			else{
				while(n < my_string.length()){
					new_string += Character.toString(arr_my_string[(arr_my_string.length-n)-1]);
					n += n;
				}
			}
			
			return new_string;
		}
	}

	public void convertDigitsToNamesInSubstring(int startPosition,int endPosition)
	throws IllegalArgumentException,MyIndexOutOfBoundsException,NullPointerException {
		
		if(endPosition < startPosition)
			throw new IllegalArgumentException();
		
		else if(endPosition > my_string.length()||endPosition < 1)
			throw new MyIndexOutOfBoundsException();
	
		else if(startPosition > my_string.length()||startPosition < 1)
			throw new MyIndexOutOfBoundsException();
		
		else if(my_string == null)
			throw new NullPointerException();
		
		String num_string = my_string.substring(startPosition, endPosition);
		String num_word_string = "";
		char[] arr_num_string = num_string.toCharArray();
		
		for(char current_char:arr_num_string){
			if(Character.isDigit(current_char))
				num_word_string += num_names[Character.getNumericValue(current_char)];
			else
				num_word_string += Character.toString(current_char);
		}
		
		my_string = my_string.substring(0,startPosition) + num_word_string + my_string.substring(endPosition,my_string.length());
		
		

}
	}
