import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BFS {
    
    private HashMap<Player,ArrayList<Player>> graph = new HashMap<Player,ArrayList<Player>>();
    private Player src, tgt;
    private HashMap<Player,Player> parent;

    public BFS(HashMap<Player,ArrayList<Player>> graph, Player src, Player tgt) {
        this.graph = graph;
        this.src = src;
        this.tgt = tgt;
        parent = new HashMap<Player,Player>();
    }
    
    public ArrayList<Player> bfsTraversal() {
        
        ArrayList<Player> path = new ArrayList<Player>();
        
        //Initialize the player HashMap for each player 
        for (Map.Entry<Player, ArrayList<Player>> entry : graph.entrySet()) {
            parent.put(entry.getKey(), null);
        }
        
        ArrayList<Player> discovered = new ArrayList<Player>();
        ArrayList<Player> queue = new ArrayList<Player>();
        ArrayList<Player> layer = new ArrayList<Player>();
        ArrayList<Player> nextLayer = new ArrayList<Player>();
        
        //Account for the src/first player
        queue.add(src);
        layer.add(src);
        discovered.add(src);
            
        while (!queue.isEmpty()) {
            
            //For each layer 
            for (int i = 0; i < layer.size(); i++) {
                
                Player curr = queue.remove(0);
                
                for (Player p : graph.get(curr)) {
                    if (!discovered.contains(p)) {
                        discovered.add(p);
                        queue.add(p);
                        parent.put(p,curr);
                        nextLayer.add(p);
                    }
                }
            }
            
            layer = nextLayer;
            nextLayer = new ArrayList<Player>();
            
        }
        
        //Create the path by traversing the parents from tgt to src Player
        
        Player curr = tgt;
        path.add(0,curr);
        while (curr != src) {
            curr = parent.get(curr);
            if (curr != src && curr == null) {
                return new ArrayList<Player>();
            }
            path.add(0,curr);
        }
        
        return path;
        
    }
    
}
