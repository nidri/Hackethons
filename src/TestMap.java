import java.util.*;
import java.util.Map;
import java.util.HashMap;
public class TestMap {
	String SID;
	Map<String, String> Routes = new HashMap();
	
	public void DisplayRoutes()
	{
		System.out.println("Available routes from " + SID + "\n");
		for(String key: Routes.keySet())
		{
			System.out.println(key +" : "+ Routes.get(key));
			System.out.println();
		}
	}
}
