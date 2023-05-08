package pandemic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import pandemic.player.*;
import pandemic.jsonreader.*;
import pandemic.cards.*;
import pandemic.actions.*;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	private Game game;
    private ArrayList<Disease> diseases;
	
	@Before
	public void init() {
        Map map = new Map();
		try {
			map.setMapWithJSON("json/villes48.json");
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
        Disease d1 = new Disease("EBOLA", 0);
		Disease d2 = new Disease("SIDA", 1);
		Disease d3 = new Disease("CORONAVIRUS", 2);
		Disease d4 = new Disease("CHIKUNGUNYA", 3);
		ArrayList<Disease> diseases = new ArrayList<Disease>(Arrays.asList(d1, d2, d3, d4));
        this.diseases = diseases;
		ArrayList<Action> actions = new ArrayList<Action>(Arrays.asList(new Move(), new BuildResearchCenter(), new DiscoverCure(), new TreatDisease()));
		Player p1 = new Player("Globetrotter", Role.GLOBETROTTER);
		Player p2 = new Player("Expert", Role.EXPERT);
		Player p3 = new Player("Scientist", Role.SCIENTIST);
		Player p4 = new Player("Doctor", Role.DOCTOR);
        ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3, p4));
		this.game = new Game(map, players, diseases, actions);
	}

	@Test
	public void initTest() {
		assertEquals(20, this.game.getGlobalInfectionState());
        assertEquals(44, this.game.getPlayerCardsStack().stackSize());
	}

    @Test
    public void shortestPathTest() {
        Map map = this.game.getMap();
        Town town1 = map.getTownByName("ville-1");
        Town town2 = map.getTownByName("ville-2");
        Town town3 = map.getTownByName("ville-3");
        assertEquals(1, this.game.shortestPath(town1, town2).size());
        assertEquals(2, this.game.shortestPath(town1, town3).size());
    }

    @Test
    public void startInfectionPhaseTest() {
        this.game.startInfectionPhase(1);
        assertTrue(this.game.getGlobalInfectionState() == 21 || this.game.getGlobalInfectionState() == 20); // 20 if the city become a cluster, else 21
        this.game.startInfectionPhase(1000);
        assertTrue(this.game.lose());
    }

    @Test
    public void winTest() {
        assertFalse(this.game.win());
        for (Disease disease : this.diseases) {
            disease.cure();
            while(disease.getCube() != Disease.INITIAL_CUBES_NUMBER) {
                disease.removeCube();
            }
        }
        this.game.removeCuredDiseases();
        assertTrue(this.game.win());
    }

}
