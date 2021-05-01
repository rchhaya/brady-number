import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    
    private String name, team;
    private int year;

    //Stores the team key to the list of all years that a player played for that team
    private HashMap<String, ArrayList<Integer>> playerMap;
    
    public Player(String name) {
        this.name = name;
        this.playerMap = new HashMap<String, ArrayList<Integer>>();
    }
    
    //Constructor for each player
    public Player(String name, String team, int year) {
        this.name = name;
        this.team = team;
        this.year = year;
        setTeamAndYear(team,year);
    }
    
    //Add the year to the respective team if not already in the HashMap
    public void setTeamAndYear(String team, int year) { 
        if (playerMap.get(team) == null) {
            playerMap.put(team, new ArrayList<Integer>());
            playerMap.get(team).add(year);
        } else {
            if (!playerMap.get(team).contains(year)) {
                playerMap.get(team).add(year);
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public HashMap<String,ArrayList<Integer>> getPlayerMap() {
        return playerMap;
    }
    
}
