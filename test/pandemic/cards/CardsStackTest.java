package pandemic.cards;
import pandemic.*;
import pandemic.cards.Card;

import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

<<<<<<< HEAD
import org.junit.Before;
import org.junit.Test;
public class CardsStackTest {
    private CardsStack cardStack1;
    private CardsStack cardStack2;
    private CardsStack cardStack3;
    private ArrayList<Card> cards;
    private Card c1;
    private Card c2;
=======
import javax.security.sasl.SaslException;

import org.junit.Before;
import org.junit.Test;
public class CardsStackTest {
    private CardsStack cardsStack1;
    private CardsStack cardsStack2;
    private CardsStack cardsStack3;
    private ArrayList<Card> cards;
    private Card c1;
    private Card c2;
    private Card c3;
    private Card c4;
    private Card c5;
>>>>>>> 75db201ae81e83e39501a56f79263f7599c7909e

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
<<<<<<< HEAD
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
=======
        this.c1 = new Card(t1,d1);
        this.c2 = new Card(t2,d2);
        this.c3 = new Card(t3,d3);
        this.c4 = new Card(t4,d4);
        this.c5 = new Card(t5,d5);
        this.cards = new ArrayList<Card>(Arrays.asList(this.c1, this.c2, this.c3, this.c4, this.c5));
        this.cardsStack1 = new CardsStack(cards,true);
        this.cardsStack2 = new CardsStack(cards,true);
        this.cardsStack3 = new CardsStack(cards,true);
    }

    @Test
    public void initStackTest(){
        Stack<Card> stack = this.cardsStack1.getCardStack();
        for (int i = 0; i < this.cards.size(); i++) {
            assertTrue(stack.search(this.cards.get(i))!= -1);   
        }
    }

    @Test
    public void discardCardTest(){
        assertEquals(0, this.cardsStack3.discardSize());
        this.cardsStack3.discardCard(this.c2);
        assertEquals(1,this.cardsStack3.discardSize());
    }

    @Test
    public void pickCardTest() {
        assertSame(5, this.cardsStack1.stackSize());
        Card card = this.cardsStack1.pickCard();
        assertSame(4, this.cardsStack1.stackSize());

        CardsStack emptyCardsStack = new CardsStack(new ArrayList<Card>(), true);
        assertSame(null, emptyCardsStack.pickCard());

        Card pickedCard = this.cardsStack2.pickCard(); // test when there's no cards remaining in the stack, so the stack is refilled with the discard stack
        this.cardsStack2.discardCard(pickedCard);

        while (this.cardsStack2.stackSize() != 0) {
            Card c = this.cardsStack2.pickCard();
            this.cardsStack2.discardCard(c);
        }
        this.cardsStack2.resetStack();
        
        assertTrue(this.cardsStack2.getCardStack().search(pickedCard) != -1);
    }

    @Test
    public void resetStackTest() {
        this.cardsStack2.resetStack();
        Stack<Card> stackCards = this.cardsStack2.getCardStack();
        for (Card card : this.cards) {
            assertTrue(stackCards.search(card) != -1);
        }
    }

    @Test
    public void mergeStacksTest() {
        Stack<Card> stack1 = new Stack<Card>();
        stack1.push(c1);
        Stack<Card> stack2 = new Stack<Card>();
        stack2.push(c2);
        Stack<Card> stack3 = new Stack<Card>();
        stack3.push(c3);
        Stack<Card> stack4 = new Stack<Card>();
        stack4.push(c4);
        Stack<Card> stack5 = new Stack<Card>();
        stack5.push(c5);
        ArrayList<Stack<Card>> stackOfStack = new ArrayList<Stack<Card>>(Arrays.asList(stack1, stack2, stack3, stack4, stack5));
        CardsStack newCardsStack = new CardsStack(new ArrayList<Card>(), true);
        newCardsStack.mergeStacks(stackOfStack);
        Stack<Card> stackOfNewCardsStack = newCardsStack.getCardStack();
        for (Stack<Card> stackOfCards : stackOfStack) {
            while (!stackOfCards.isEmpty()) {
                Card card = stackOfCards.pop();
                assertTrue(stackOfNewCardsStack.search(card) != -1);
            }
        }
    }
>>>>>>> 75db201ae81e83e39501a56f79263f7599c7909e
}
