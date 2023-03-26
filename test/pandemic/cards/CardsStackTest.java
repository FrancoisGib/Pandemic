package pandemic.cards;
import pandemic.*;
import pandemic.cards.Card;

import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
public class CardsStackTest {
    private CardsStack cardStack1;
    private CardsStack cardStack2;
    private CardsStack cardStack3;
    private ArrayList<Card> cards;
    private Card c1;
    private Card c2;

    @Before
    public void init(){
        Town t1 = new Town("Lille",1);
        Town t2 = new Town("Chicago",2);
        Town t3 = new Town("Lagos",2);
        Town t4 = new Town("Montreuil",3);
        Town t5 = new Town("Lyon",4);
        Disease d1 = new Disease("EBOLA",1);
        Disease d2 = new Disease("COVID-19",2);
        Disease d3 = new Disease("SIDA",3);
        Disease d4 = new Disease("CHIKUNGUNYA",4);
        Disease d5 = new Disease("GRIPPE",4);
        Card c1 = new Card(t1,d1);
        Card c2 = new Card(t2,d2);
        Card c3 = new Card(t3,d3);
        Card c4 = new Card(t4,d4);
        Card c5 = new Card(t5,d5);
        this.cards = new ArrayList<Card>(Arrays.asList(c1,c2,c3,c4,c5));
        this.cardStack1 = new CardsStack(cards,true);
        this.cardStack2 = new CardsStack(cards,true);
        this.cardStack3 = new CardsStack(cards,true);
    }

    @Test
   public void TestinitStack(){
        for (int i = 0; i < this.cards.size(); i++) {
            assertTrue(this.cardStack1.getCardStack().search(this.cards.get(i))!= -1);   
        }
   }

   @Test
   public void TestDiscardSize(){
    assertEquals(0, this.cardStack3.discardSize());
    this.cardStack3.discardCard(this.c2);
    assertEquals(1,this.cardStack3.discardSize());
   }

   @Test
   public void TestStackSize(){
    assertEquals(0,this.cardStack3.stackSize());
   }
}
