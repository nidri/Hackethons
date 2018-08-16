package ca.classes;

public class CheckAlpha {

	String Sorted = "";
	String UnSorted = "";	
	CheckAlpha(String Input)
	{
		SortInput SI = new SortInput(Input);
		Sorted = SI.SortString(Input);
		UnSorted = Input;
	}
	
	public String RemDups()
	{
		char old[] = Sorted.toCharArray();
		String result = "";
		for(char c:old)
		{
			//System.out.println(String.valueOf(c));
			if(!result.contains(String.valueOf(c)))
			{
				result += "" + String.valueOf(c);
			}
		}
		return result;
	}
	
	public boolean CompareAlpha()
	{
		boolean b = true;
		char Alpha[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		for(char c:Alpha)
		{
			if(!UnSorted.contains(String.valueOf(c)))
			{
				b = false;
				break;
			}
		}
		return b;
	}
	
	public static void main(String ar[])
	{
		CheckAlpha CA = new CheckAlpha("abcdefghijklmnopqrstuvwxyz");
		//System.out.println(CA.RemDups());
		System.out.println(CA.CompareAlpha());
	}
}
