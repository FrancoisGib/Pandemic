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
        if (this.stack.isEmpty()) {
            this.resetStack();
        }
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
}
