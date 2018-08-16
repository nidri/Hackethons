/* Finds the router range. Hacker Earth problem - 
 * https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/practice-problems/algorithm/wifi-routers/
 */

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RouterRange
{
    double RouterRange;
    Coordinate Base;
    Router BaseRouter;
    Coordinate FarUser;
    Router FarRouter;
    Router ConnectedRouter;
    List<Router> RoutersList;
    List<Coordinate> UsersList;
    InputStreamReader IS;
	BufferedReader BR;
    
    RouterRange()
    {
        Base = new Coordinate();
        BaseRouter = new Router();
        RouterRange = 0;
        RoutersList = new ArrayList<Router>();
        UsersList = new ArrayList<Coordinate>();
        FarUser = Base;
        FarRouter = BaseRouter;
        ConnectedRouter = BaseRouter;
        IS = new InputStreamReader(System.in);
    	BR = new BufferedReader(IS);
    }
    
    void PrintRouterRange()
    {
        try 
        {
            //CaptureInput();
            System.out.println();
            FindClosestRouter();
            FindClosestUser();
            //FindFarthestUser();
            //FindRouterRange();
            CalRouterRange();
            DecimalFormat df = new DecimalFormat("#.000000");
            df.setRoundingMode(RoundingMode.HALF_EVEN);
            System.out.println(df.format(RouterRange));
        } 
        catch(Exception e) 
        {
            System.out.println("Exception in Print routine - " + e);
        } 
        finally 
        {
            //Do nothing for now.
            CleanUp();
        }
    }
    
    void CleanUp()
    {
    	RoutersList.clear();
    	UsersList.clear();
    	FarUser = Base;
    	RouterRange = 0;
    }
    
    void CaptureInput() throws IOException
    {
        //Capture users and routers.
        try 
        {
            int TC = Integer.parseInt(BR.readLine());
            String Splitter[] = new String[2];
            for(int i=1; i<= TC; i++)
            {
                String NextLine = BR.readLine().trim();
                if(NextLine.isEmpty())
                {
                	i--;
                	continue;
                }
                Splitter = NextLine.split("\\s+");
                int NoOfUsers = Integer.parseInt(Splitter[0]);
                int NoOfRouters = Integer.parseInt(Splitter[1]);
                
                for(int u=1; u<=NoOfUsers; u++) // Capture users
                {
                    NextLine = BR.readLine().trim();
                    if(NextLine.isEmpty())
	                {
	                	u--;
	                	continue;
	                }
                    Splitter = NextLine.split("\\s+");
                    Coordinate User = new Coordinate(Integer.parseInt(Splitter[0]), Integer.parseInt(Splitter[1]));
                    UsersList.add(User);
                }
                for(int r=1; r<=NoOfRouters; r++) // Capture Routers
                {
                    NextLine = BR.readLine().trim();
                    if(NextLine.isEmpty())
	                {
	                	r--;
	                	continue;
	                }
                    Splitter = NextLine.split("\\s+");
                    Router Router = new Router(Integer.parseInt(Splitter[0]), Integer.parseInt(Splitter[1]));
                    RoutersList.add(Router);
                }
                PrintRouterRange();
            }    
        } 
        catch(Exception e) 
        {
        } 
        finally 
        {
            BR.close();
            IS.close();
        }
        
    }
    
    
    void FindRouterRange()
    {
        for(Router R : RoutersList)
        {
            double DistFromFarUser = DistanceBetweenCoordinates(R, FarUser);
            if(RouterRange == 0 || RouterRange > DistFromFarUser)
            {
            	if(DistFromFarUser >= R.NearestRouterDistance)
            	{
            		RouterRange = DistFromFarUser;
                	ConnectedRouter = R;
            	}
                
            }
        }    
    }
    
    void CalRouterRange() {
    	// Max of NearestRouterDistance values in UsersList and RotuersList
    	for(Router R : RoutersList) { 
    		// Loop through routers and capture RouterRange value
    		if(R.NearestRouterDistance > RouterRange) {
    			// Get max of NearestRouterRange and RouterRange
    			RouterRange = R.NearestRouterDistance;
    		}
    	}
    	// Now loop through UsersList and get RouterRange
    	for(Coordinate U : UsersList) {
    		if(U.NearestRouterDistance > RouterRange) {
    			// Get max of NearestRouterRange and RouterRange
    			RouterRange = U.NearestRouterDistance;
    		}
    	}
    }
    
    void FindFarthestUser()
    {
        double FarUserDistance = 0;
        for(Coordinate C : UsersList)
        {
            double Dist = 0;
            Dist = C.DistanceFromBase;
            if(Dist > FarUserDistance)
            {
                FarUserDistance = Dist;
                FarUser = C;
            }
        }
    }
    
    void FindFarthestRouter()
    {
        double FarRouterDistance = 0;
        for(Router C : RoutersList)
        {
            double Dist = 0;
            Dist = C.DistanceFromBase;
            if(Dist > FarRouterDistance)
            {
                FarRouterDistance = Dist;
                FarRouter = C;
            }
        }
    }
    
    public double DistanceBetweenCoordinates(Coordinate A, Coordinate B)
    {
        double Dist = 0;
        Dist = Math.pow((A.XCoordinate - B.XCoordinate), 2) + Math.pow((A.YCoordinate - B.YCoordinate), 2);
        Dist = Math.sqrt(Dist);
        return Dist;
    }
    
    void FindClosestRouter()
    {
    	// For each of the router, find the distance from the nearest router.
    	for(Router R : RoutersList)
    	{
    		// Loop through all routers and get distance from its closest one.
			try 
			{
				for(Router IR : RoutersList) // Loop through all routers to find distance to each one. IR is Inner Router.
				{
					if(!R.equals(IR))
					{
						double Dist = DistanceBetweenCoordinates(R, IR);
						if(R.NearestRouterDistance == 0 || Dist < IR.NearestRouterDistance)
						{
							IR.NearestRouterDistance = Dist;
						}
					}
				}
			} 
			catch(Exception e) {
			} 
			finally {
			}
    	}
    }
    
    void FindClosestUser()
    {
    	// For each of the user, find the distance from the nearest router.
    	for(Router R : RoutersList)
    	{
    		// Loop through all routers and get distance from its closest one.
			try 
			{
				for(Coordinate IR : UsersList) // Loop through all users to find distance to each one. IR is User.
				{
					if(!R.equals(IR))
					{
						double Dist = DistanceBetweenCoordinates(R, IR);
						if(R.NearestRouterDistance == 0 || Dist < IR.NearestRouterDistance)
						{
							IR.NearestRouterDistance = Dist;
						}
					}
				}
			} 
			catch(Exception e) {
			} 
			finally {
			}
    	}
    }
    
    public class Coordinate
    {
        int XCoordinate;
        int YCoordinate;
        double DistanceFromBase = 0;
        double NearestRouterDistance = 0;
        Coordinate()
        {
            XCoordinate = 0;
            YCoordinate = 0;
        }
        Coordinate(int X, int Y)
        {
            this.XCoordinate = X;
            this.YCoordinate = Y;
            DistanceFromBase = DistanceBetweenCoordinates(this, Base);
        }
        
    }
    
    public class Router extends Coordinate
    {
    	Router()
    	{
    		super();
    	}
    	Router(int X, int Y)
    	{
    		super(X, Y);
    	}
    }
    
    public static void main(String ar[])
    {
        //System.out.println("Finding Routers range...");
        try 
        {
	        RouterRange R = new RouterRange();
	        R.CaptureInput();	
        } 
        catch(Exception e) 
        {
        } 
        finally 
        {
        }
        
    }
}