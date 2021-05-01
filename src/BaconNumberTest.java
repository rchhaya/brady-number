import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class BaconNumberTest {
    
    @Test
    public void test() {
        ArrayList<Player> li = new ArrayList<Player>();
        Player p1 = new Player("A");
        p1.setTeamAndYear("1", 2000);
        p1.setTeamAndYear("2", 2001);
        System.out.println(p1.getPlayerMap());
        Player p2 = new Player("B");
        p1.setTeamAndYear("2", 2001);
        p1.setTeamAndYear("3", 2002);
        System.out.println(p1.getPlayerMap());
        li.add(p1);
        li.add(p2);
        AdjacencyList a = new AdjacencyList(li);
        System.out.println(a.getAdjList());
    }
}
