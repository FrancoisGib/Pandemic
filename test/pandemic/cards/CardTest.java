package pandemic.cards;

import pandemic.cards.Card;
import pandemic.Disease;
import pandemic.Town;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CardTest {
    private Card card1;
    private Card card2;
    private Disease disease;
    private Town town;

    @Before
    public void init(){
        this.town = new Town("Toronto",0);
        this.disease = new Disease("COVID-19",3);
        this.card1 = new Card(town, disease);
<<<<<<< HEAD
        this.card2 = new Card();
=======
        this.card2 = new Card(null, null);
>>>>>>> François
    }

    @Test
    public void initCardTest() {
        assertEquals("Toronto", this.card1.getTownName());
        assertEquals(this.disease, this.card1.getDisease());
        assertEquals("COVID-19", this.card1.getDiseaseName());
    }

    @Test
    public void epidemicCardTest(){
        assertEquals(null, this.card2.getTown());
        assertEquals(null, this.card2.getDisease());
    }
}
