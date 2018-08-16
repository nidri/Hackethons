// Class to perform logic to calculate resistance
package mr.cal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalRes {
ArrayList<Integer> ValInt; 
CalRes()
{
	ValInt = new ArrayList<Integer>();
}

//Calculate resistance

	
	public int CalRes(ArrayList<String> AR)
	{
		//Calculates max resistance and returns final integer
		int result = AR.size()==1? Integer.parseInt(AR.get(0)):0;
		//System.out.println("Input is - " + Input);
		//System.out.println(AR.get(0));
		while(AR.get(0).toString().equals("B") || AR.get(0).toString().equals("A")) // Loop through until last resistance is returned
		{
			int i = AR.size() - 1;
			System.out.println("E = " + AR.get(i));
			if(!AR.get(i).toString().equals("B") && !AR.get(i).toString().equals("A")) // If resistance value then add to array list.
			{
				//System.out.println("If");
				ValInt.add(Integer.parseInt(AR.get(i).toString()));
				AR.remove(i);
			}
			else // Else calculate resistance according to type.
			{
				//System.out.println("Else");
				result = ComputeRes(AR.get(i).toString());
				AR.set(i, Integer.toString(result));					
			}
		}
		return result;
	}
public int ComputeRes(String Type)
{
	int res = 0;
	if(Type.equals("A"))
	{
		//Sum of resistance values
		for(int i=0; i<ValInt.size(); i++)
		{
			res += ValInt.get(i); 
		}
	}
	else
	{
		//Max of resistance values
		res = Collections.max(ValInt);
	}
	ValInt.clear();
	return res;
}
}
