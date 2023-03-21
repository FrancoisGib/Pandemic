package pandemic.cards;

import java.util.*;

import pandemic.Disease;
import pandemic.Town;

/* the class that defines a card stack in the game */
public class CardsStack {
    /* the total stack of cards in game */
    private Stack<Card> stack;
    private Stack<Card> discardStack;

    public CardsStack(ArrayList<Card> cards) {
        this.stack = new Stack<Card>();
        this.initStack(cards);
        this.discardStack = new Stack<Card>();
    }
    /*get the current stack of Card */
    public Stack<Card> getCardStack(){
        return this.stack;
    }

    public void initStack(ArrayList<Card> cards) {
        Random random = new Random();
        while (!cards.isEmpty()) {
            int i = random.nextInt(cards.size());
            Card card = cards.get(i);
            cards.remove(i);
            this.stack.push(card);
        } 
    }

    public boolean hasCardsLeft() {
        return this.stack.isEmpty();
    }

    /* pick the top card of stack */
    public Card pickCard(){
        return this.stack.pop();
    }

    public void discardCard(Card card) {
        this.discardStack.add(card);
    }

    /* if the stack is empty, reset it with mixed cards from the discard*/
    public void resetStack(){
        if (this.stack.empty()) {
            ArrayList<Card> cards = new ArrayList<Card>();
            while(!this.discardStack.empty()){
                cards.add(this.discardStack.pop());
            }
            this.initStack(cards);
        }
    }

    public static void main(String args []){
        Town town = new Town("New York", 1);
        Town town2 = new Town("Chicago", 2);
        Town town3 = new Town("Lille", 59);
        Disease d = new Disease("Ebola");
        Disease d2 = new Disease("Sida");
        Disease d3 = new Disease("Peste Noire");
        Card c3 = new Card(town3, d3);
        Card c2 = new Card(town2, d2);
        Card c1 = new Card(town,d);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        CardsStack c = new CardsStack(cards);
        Card c4 = c.pickCard();
        Card c5 = c.pickCard();
        Card c6 = c.pickCard();
        c.discardCard(c4);
        c.discardCard(c5);
        c.discardCard(c6);
        c.resetStack();
        while(!c.stack.empty()){
            Card card = c.stack.pop();
            System.out.println(card.getTownName()+ " " + card.getDisease().getName());
        }       
    }


}
