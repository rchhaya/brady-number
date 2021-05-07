import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjacencyList  {
 
    private ArrayList<Player> li = new ArrayList<Player>();
    private HashMap<Player,ArrayList<Player>> adjList = new HashMap<Player,ArrayList<Player>>();
    
    public AdjacencyList(ArrayList<Player> li) {
        this.li = li;
        initialize();
        construct();
    }
    
    public void initialize() {
    	//Initializes the keys
        for (Player p : li) {
            if (!adjList.containsKey(p)) {
                adjList.put(p, new ArrayList<Player>());
            }  
        }
    }
    
    public void construct() {
        
        for (Map.Entry<Player, ArrayList<Player>> entry : adjList.entrySet()) {
            
            //Current player
            Player curr = entry.getKey();
            
            //Access the current player's player map (team key, list of years value)
            HashMap<String,ArrayList<Integer>> currPlayerMap = curr.getPlayerMap();
            
            //Iterate through each of the players that is not the current player 
            for (Map.Entry<Player, ArrayList<Player>> f : adjList.entrySet()) {
                
                if (!f.getKey().equals(curr)) {
                    
                    //Neighbor player
                    Player neighbor = f.getKey();
                    
                    //Access the neighbor player map
                    HashMap<String,ArrayList<Integer>> neighborPlayerMap = neighbor.getPlayerMap();
                    
                    //Iterate through the entries of the current player map
                    for (Map.Entry<String, ArrayList<Integer>> e : currPlayerMap.entrySet()) {
                        
                        //Current team
                        String team = e.getKey();
                        
                        //List of years for the current team
                        ArrayList<Integer> years = e.getValue();
                        
                        //Iterate through the years of the current team
                        for (int year : years) {
                            
                            //Check if the neighbor player played for the same team
                            if (neighborPlayerMap.get(team) != null) {
                                
                                //If the neighbor played on the current team, check if 
                                //played the current year
                                if (neighborPlayerMap.get(team).contains(year)) {
                                    
                                    //Add the neighbor the current 
                                    if (!adjList.get(curr).contains(neighbor)) {
                                        adjList.get(curr).add(neighbor);
                                    }
                                    
                                    //Add the current to the neighbor
                                    if (!adjList.get(neighbor).contains(curr)) {
                                        adjList.get(neighbor).add(curr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
           
            
            
        }
    }
    
 
    public HashMap<Player,ArrayList<Player>> getAdjList() {
        return adjList;
    }
}