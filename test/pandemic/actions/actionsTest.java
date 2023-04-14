package pandemic.actions;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import pandemic.Disease;
import pandemic.Map;
import pandemic.Town;
import pandemic.cards.Card;
import pandemic.player.Player;
import pandemic.player.Role;

public class actionsTest {
    private Action move;
    private Action buildResearchCenter;
    private Action discoverCure;
    private Action treatDisease;

    @Before
    public void init() {
		this.move = new Move();
		this.buildResearchCenter = new BuildResearchCenter();
		this.discoverCure = new DiscoverCure();
		this.treatDisease = new TreatDisease();
	}

    @Test
    public void moveTest() {
        Player p1 = new Player("p1", Role.DOCTOR);
        Town t1 = new Town("t1", 0);
        p1.setTown(t1);
        Town t2 = new Town("t2", 0);
        this.move.run(p1, t2);
        assertSame(p1.getTown(), t2);
    }

    @Test
    public void buildResearchCenterTest() {
        Player p1 = new Player("p1", Role.EXPERT);
        Town t1 = new Town("t1", 0);
        p1.setTown(t1);
        this.buildResearchCenter.run(p1, t1);
        assertTrue(t1.hasResearchCenter());

        Town t2 = new Town("t2", 0);
        Card card = new Card(t2, new Disease("", 0));
        Player p2 = new Player("p2", Role.GLOBETROTTER);
        p2.setTown(t2);
        p2.addCard(card);
        this.buildResearchCenter.run(p2, null);
        assertTrue(t2.hasResearchCenter());
        assertSame(p2.getCardsByTown(t2).size(), 0);
    }

    @Test
    public void discoverCureTest() {
        Player p1 = new Player("p1", Role.SCIENTIST);
        Town t1 = new Town("t1", 0);
        p1.setTown(t1);
        Disease d1 = new Disease("Ebola", 0);
        for (int i = 0; i < 5; i++) {
            p1.addCard(new Card(t1, d1));
        }
        this.discoverCure.run(p1, d1);
        assertTrue(d1.isCured());
        assertSame(p1.getCards().size(), 1);

        Player p2 = new Player("p2", Role.GLOBETROTTER);
        Town t2 = new Town("t2", 0);
        Disease d2 = new Disease("Sida", 0);
        p2.setTown(t2);
        for (int i = 0; i < 5; i++) {
            p2.addCard(new Card(t2, d2));
        }
        this.discoverCure.run(p2, d2);
        assertTrue(d2.isCured());
        assertSame(p2.getCards().size(), 0);
    }

    @Test
    public void treatDiseaseTest() {
        Disease d = new Disease("Ebola", 0);
        Town town = new Town("town", 0);
        town.setInfectionState(d, 3);
        Player p1 = new Player("player1", Role.GLOBETROTTER);
        Player p2 = new Player("player2", Role.DOCTOR);
        p1.setTown(town);
        p2.setTown(town);
        this.treatDisease.run(p1, d);
        assertSame(town.getInfectionState(d), 2);
        this.treatDisease.run(p2, d);
        assertSame(town.getInfectionState(d), 0);
    }
}
