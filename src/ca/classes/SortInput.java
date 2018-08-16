package ca.classes;

import java.util.Arrays;


public class SortInput {
	String Unsorted = "";
	SortInput(String Input)
	{
		Unsorted = Input;
	}
	
	public String SortString(String Alpha)
	{
		// Sort the characters in alphabetical order.
        Alpha = Alpha.replaceAll("\\s","");
        //System.out.println(Alpha);
        char[] C = Alpha.toCharArray();
        Arrays.sort(C);
        Alpha = null;
        
        return new String(C);
	}
}
