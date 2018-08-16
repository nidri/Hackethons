/*
 * HackerEarth problem - https://www.hackerearth.com/problem/algorithm/irctc/
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

public class RailwayRoutes {
	List<String[]> Routes;
	Map<String, List<String[]>> SID; // Station Info
	Map<String, Vertex> Vertices; // List of vertices
	String SourceSID; // Source station
	String DestSID; // Destination station
	String PassSID; // Passing station
	InputStreamReader IS;
	BufferedReader BR;
	PriorityQueue<Vertex> PQ;
	Comparator<Vertex> Comp;
	CurVertex CV;
	String NoTrain = "No Train Found.";
	String Path;
	long Distance;
	static Logger oLog;
    RailwayRoutes()
    {
    	Routes = new ArrayList<String[]>();
    	IS = new InputStreamReader(System.in);
    	BR = new BufferedReader(IS);
    	//SID  = new HashMap<String, List<String[]>>();
    	Vertices = new HashMap<String, Vertex>();
    	Comp = new QComp();
    	SourceSID = "";
    	DestSID = "";
    	PassSID = "";
    	PQ = new PriorityQueue<Vertex>(1, Comp);
    	CV = new CurVertex();
    	Path = "";
    	Distance = 0;
    	oLog = Logger.getLogger(this.getClass().getName());
    }
    
    public void ComputePath()
    {
    	SP(PassSID);
    	if(PathExists())
    	{
    		//Build path and distance
    		Distance = Vertices.get(SourceSID).Dist + Vertices.get(DestSID).Dist;
    		Path = GetPath(SourceSID, PassSID, false);
    		Path = Path + " " + PassSID + " " + (GetPath(DestSID, PassSID, true));
    		Path = Distance + "\n" + Path;
    	}
    	else
    	{
    		Path = NoTrain;
    	}
    	
    	System.out.println(Path);
    	CleanUp();
    }
    
    public String GetPath(String From, String To, boolean Reverse)
    {
    	String Path = From;
    	Vertex V = new Vertex();
    	String NextStation = From;
    	
    	while(!To.equals(V.PId))
    	{
    		V = Vertices.get(NextStation);
    		
    		if(!To.equals(V.PId))
    		{
    			NextStation = V.PId;
    			if(Reverse)
    			{
    				Path = V.PId + " " + Path;
    			}
    			else
    			{
    				Path = Path + " " + V.PId;
    			}
    		}
    	}
    	return Path.trim();
    }
    
    public boolean PathExists()
    {
    	return (Vertices.containsKey(SourceSID) && Vertices.containsKey(DestSID));
    }
    
    public void SP(String Station)
    {
    	
    	try {
    		PQ.offer(new Vertex(Station, "", (long) 0)); // Add Station to queue
			Vertices.put(Station, new Vertex(Station, "", (long) 0));
			SPA();
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public void SPA()
    {
    	while(!PQ.isEmpty())
    	{
    		/*if(CV.Counter <= 0)
    		{
    			break;
    		}*/
    		Vertex V = (Vertex)PQ.poll();
    		CV.Dist = V.Dist; // Maintaining CV distance to add as cumulative 
    		if(SID.get(V.Id) != null)
			{
				AddToVertices(V.Id); // Compute distance to each node and store in Vertices.
				SID.remove(V.Id); // Remove from SID once visited for improving performance.
			}
    	}
    }
    
    public void AddToVertices(String Station) 
    {
    	//Routes = SID.get(Station);
    	Iterator<String[]> It = SID.get(Station).iterator();
    	while(It.hasNext()) // Each route
    	{
    		String[] Edge = (String[])It.next();
    		String Dest = Edge[0];
    		long Dist = Long.parseLong(Edge[1]);
    		Dist = Dist + CV.Dist;
    		if(Dest.equals(SourceSID) || Dest.equals(DestSID))
    		{
    			CV.Counter--;
    		}
    		Vertex V = (Vertex)Vertices.get(Dest);
    		if(V != null)
    		{
    			if(V.Dist > Dist)
    			{
    				V.Dist = Dist;
    				V.PId = Station;
    			}
    			else
    			{
    				continue;
    			}
    			Dist = V.Dist;
    		} 
    		else
    		{
    			Vertices.put(Dest, new Vertex(Dest, Station, Dist));
    		}
    		PQ.offer(new Vertex(Dest, Station, Dist));
    	}
    }
    
    public void PrintVertices()
    {
    	for(Map.Entry<String, Vertex> entry : Vertices.entrySet())
    	{
    		Vertex V = (Vertex)entry.getValue();
    		System.out.println("Vertex - " + V.Id + " Parent - " + V.PId + " Distance - " + V.Dist);
    	}
    }
    
    public void CleanUp()
    {
    	//Cleanup
    	SID.clear();
    	Path = "";
    	SourceSID = "";
    	PassSID = "";
    	DestSID = "";
    	Routes.clear(); // Clear Routes
    	PQ.clear(); // Empty queue
    	Vertices.clear(); // Empty vertices
    }
    
    
    public class CurVertex
    {
    	String Id;
    	long Dist;
    	int Counter;
    	CurVertex()
    	{
    		Id = "";
    		Dist = 0;
    		Counter = 0;
    	}
    }

    public class Vertex {
    	String Id;
    	String PId;
    	long Dist;
    	Vertex()
    	{
    		
    	}
    	Vertex(String Id, String PId, Long Dist)
    	{
    		this.Id = Id;
    		this.PId = PId;
    		this.Dist = Dist;
    	}
    }
    public class QComp implements Comparator<Vertex> 
    {
		@Override
		public int compare(Vertex V1, Vertex V2) {
			return (V1.Dist > V2.Dist) ? 1 : -1 ;
		}
    }
    
    public void IncrementCounter(String Source, String Dest)
    {
    	if(Source.equals(SourceSID) || Dest.equals(SourceSID))
    	{
    		CV.Counter++;
    	}
    	if(Source.equals(DestSID) || Dest.equals(DestSID))
    	{
    		CV.Counter++;
    	}
    }
    
    
    public void CaptureInput() throws IOException 
    {
    	int TC = Integer.parseInt(BR.readLine());
    	
    	int NoOfRoutes = 0;
    	int NoOfStations = 0;
    	//String Route = "";
    	String[] RouteSplit = new String[3];
    	long TimeTaken = 0;
    	long BeforeTime = 0;
    	long AfterTime = 0;
    	try {
    		for(int t=1; t<=TC; t++) //Capture stations and routes for all test cases
        	{
        		String StationsAndRoutes = BR.readLine();
        		String StationRoute[] = StationsAndRoutes.split("\\s+");
        		NoOfStations = Integer.parseInt(StationRoute[0]);
        		NoOfRoutes = Integer.parseInt(StationRoute[1]);
        		SID  = new HashMap<String, List<String[]>>(NoOfStations*75/100 + 1);
        		/*for(int i=1; i<=NoOfStations; i++) //Create entries in SID
        		{
        			SID.put(Integer.toString(i), new ArrayList<String[]>());
        		}*/
        		for(int r=1; r<=NoOfRoutes; r++) // Capture routes for stations
        		{
        			//Route = Scan.nextLine();
        			RouteSplit = BR.readLine().split("\\s+");
        			//Routes.add(RouteSplit);   
        			AddToSID(RouteSplit[0], RouteSplit[1], RouteSplit[2]);
        		}
        		String[] SIDSplit = BR.readLine().split("\\s+");
        		SourceSID = SIDSplit[0];
        		PassSID = SIDSplit[1];
        		DestSID = SIDSplit[2];
        		//AddSID();
        		BeforeTime = System.currentTimeMillis();
        		ComputePath();
        		AfterTime = System.currentTimeMillis();
        		TimeTaken = TimeTaken + (AfterTime - BeforeTime);
        	}
    		oLog.info("Total Time taken - " + TimeTaken);
    	}
    	catch(Exception e)
        {
            System.out.println("Error in reading line - " + e);
        }
    	finally
    	{
    		BR.close();
    		IS.close();
    	}
    }
    
    public void AddToSID(String Source, String Dest, String Dist)
    {
    	try 
    	{
    		/*SID.get(Source).add(new String[] {Dest, Dist});
    		SID.get(Dest).add(new String[] {Source, Dist});*/
    		if(SID.get(Source)!=null)
    		{
    			SID.get(Source).add(new String[] {Dest, Dist});
    		}
    		else
    		{
    			List<String[]> NewAdd = new ArrayList<String[]>();
    			NewAdd.add(0, new String[] {Dest, Dist});
    			SID.put(Source, NewAdd);
    		}
    		if(SID.get(Dest)!=null)
    		{
    			SID.get(Dest).add(new String[] {Source, Dist});
    		}
    		else
    		{
    			List<String[]> NewAdd = new ArrayList<String[]>();
    			NewAdd.add(0, new String[] {Source, Dist});
    			SID.put(Dest, NewAdd);
    		}
		}
		catch (Exception e)
		{
			System.out.println("Error in adding station - " + e);
			return;
		}
    }
    
    public void AddSID() {
    	//Add SID and respective Route destination with distance to Map.
    	String Source = "";
    	String Dest = "";
    	String Dist = "";
    	
    	for(String[] CurArray : Routes)
    	{
    		try {
    			Source = CurArray[0];
        		Dest = CurArray[1];
        		Dist = CurArray[2];
        		IncrementCounter(Source, Dest);
        		if(SID.get(Source)!=null)
        		{
        			SID.get(Source).add(new String[] {Dest, Dist});
        		}
        		else
        		{
        			List<String[]> NewAdd = new ArrayList<String[]>();
        			NewAdd.add(0, new String[] {Dest, Dist});
        			SID.put(Source, NewAdd);
        		}
        		if(SID.get(Dest)!=null)
        		{
        			SID.get(Dest).add(new String[] {Source, Dist});
        		}
        		else
        		{
        			List<String[]> NewAdd = new ArrayList<String[]>();
        			NewAdd.add(0, new String[] {Source, Dist});
        			SID.put(Dest, NewAdd);
        		}
    		}
    		catch (Exception e)
    		{
    			System.out.println("Error in adding station - " + e);
    			return;
    		}
    	}
    	Routes.clear();
    }
		
	public static void main(String ar[]) throws IOException
	{
		RailwayRoutes RR = new RailwayRoutes();
		RR.CaptureInput();
		//RR.PrintVertices();
	}
}
