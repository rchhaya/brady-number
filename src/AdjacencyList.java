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
        for (Player p : li) {
            if (!adjList.containsKey(p)) {
                adjList.put(p, new ArrayList<Player>());
            }  
        }
    }
    
    public void construct() {
        int cnt = 0;
        for (Map.Entry<Player, ArrayList<Player>> entry : adjList.entrySet()) {
            if (cnt == 0) {
                entry.getKey().setTeamAndYear("1", 2000);
                entry.getKey().setTeamAndYear("2", 2001);
            }
            if (cnt == 1) {
                entry.getKey().setTeamAndYear("2", 2001);
                entry.getKey().setTeamAndYear("3", 2002);
            }
            cnt++;
        }
        
        for (Map.Entry<Player, ArrayList<Player>> entry : adjList.entrySet()) {
            Player curr = entry.getKey();
            
            System.out.println("curr player: " + curr.getName());
            HashMap<String,ArrayList<Integer>> currPlayerMap = curr.getPlayerMap();
            System.out.println("currplayermap: " + currPlayerMap);
            for (Map.Entry<Player, ArrayList<Player>> f : adjList.entrySet()) {
                if (!f.getKey().equals(curr)) {
                    Player neighbor = f.getKey();
                    System.out.println("neighbor player: " + neighbor.getName());
                    HashMap<String,ArrayList<Integer>> neighborPlayerMap = neighbor.getPlayerMap();
                    
                    for (Map.Entry<String, ArrayList<Integer>> e : currPlayerMap.entrySet()) {
                        String team = e.getKey();
                        System.out.println("team: " + team);
                        ArrayList<Integer> years = e.getValue();
                        for (int year : years) {
                            
                            System.out.println("current year: " + year);
                            if (neighborPlayerMap.get(team) != null) {
                                System.out.println("it exists");
                                if (neighborPlayerMap.get(team).contains(year)) {
                                    if (!adjList.get(curr).contains(neighbor)) {
                                        System.out.println("entered first");
                                        adjList.get(curr).add(neighbor);
                                    }
                                    if (!adjList.get(neighbor).contains(curr)) {
                                        System.out.println("entered second");
                                        adjList.get(neighbor).add(curr);
                                    }
                                }
                            }
                        }
                        System.out.println("-------------");
                    }
                }
            }
            
           
            
            
        }
    }
    
 
    public HashMap<Player,ArrayList<Player>> getAdjList() {
        return adjList;
    }
}
