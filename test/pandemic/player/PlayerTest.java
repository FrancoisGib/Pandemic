package pandemic.player;

import pandemic.Town;
import pandemic.Disease;
import pandemic.cards.Card;
import pandemic.cards.CardsStack;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;
    private Town town;
    private Disease disease;
    private Card card;
    private CardsStack cardsStack;

    @Before
	public void init() {
		this.player = new Player("Player", Role.DOCTOR);
        this.town = new Town("Town", 0);
        this.disease = new Disease("Disease", 0);
        this.card = new Card(this.town, this.disease);
        this.cardsStack = new CardsStack(new ArrayList<Card>(Arrays.asList(this.card)));
	}

    public void movableTownsTest() {
        ArrayList<Town> townNeighbors = this.town.getNeighbors();
        HashSet<Town> movableTowns = new HashSet<Town>();
        this.player.movableTowns(this.town, movableTowns);
        for (Town t : townNeighbors) {
            assertTrue(townNeighbors.contains(t));
        }
    }

    public void getTownCardsNumberTest() {
        assertSame(0, this.player.getCardsByTown(this.town).size());
        this.player.addCard(this.card);
        assertSame(1, this.player.getCardsByTown(this.town).size());
    }
}
