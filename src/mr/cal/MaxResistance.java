/* Main class to calculate resistance with given inputs from a circuit */

package mr.cal;
import java.util.ArrayList;
import java.util.Scanner;
public class MaxResistance {
	String RPattern = "";
	String RValues  = "";
	CalRes CR = new CalRes();
	MaxResistance()
	{
		
	}
	MaxResistance(String Pat, String Val)
	{
		RPattern = Pat;
		RValues = Val;
	}
	public void PerformCal(ArrayList<String> AL)
	{
		int i = CR.CalRes(AL);
		System.out.println(i);
	}
	public void MapRes()
	{
		//Map resistance value to corresponding x in input string.
		
		ArrayList<String> AR = new ArrayList<String>();
		for(char c:RPattern.toCharArray())
		{
			AR.add(String.valueOf(c));
		}
		for(String S:RValues.split(" "))
		{
			if(AR.indexOf("X")!=(-1))
			AR.set(AR.indexOf("X"), S);
					
		}
		
		PerformCal(AR);
	}
	
	public static void main(String ar[])
	{
		Scanner Scan = new Scanner(System.in);
		//int t = Scan.nextInt();
		String Res = Scan.nextLine();
		String Val = Scan.nextLine();
		
		MaxResistance MR = new MaxResistance(Res, Val);
		//MR.PerformCal();
		MR.MapRes();
		Scan.close();
	}
}
