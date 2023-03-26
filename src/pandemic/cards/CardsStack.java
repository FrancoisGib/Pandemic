package pandemic.cards;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;

/* the class that defines a card stack in the game */
public class CardsStack {
    /* the total stack of cards in game */
    private Stack<Card> stack;
    private Stack<Card> discardStack;
    private boolean discard;

    public CardsStack(ArrayList<Card> cards, boolean discard) {
        this.stack = new Stack<Card>();
        this.initStack(cards);
        this.discardStack = new Stack<Card>();
        this.discard = discard;
    }

    /* get the current stack of Card */
    public Stack<Card> getCardStack() {
        return this.stack;
    }

    public void initStack(ArrayList<Card> cards) {
        Random random = new Random();
        while (!cards.isEmpty()) {
            int i = random.nextInt(cards.size());
            Card card = cards.remove(i);
            this.stack.push(card);
        }
    }

    public boolean hasCardsLeft() {
        return this.stack.isEmpty();
    }

    /* pick the top card of stack */
    public Card pickCard() {
        if (!this.stack.empty()) {
            return this.stack.pop();
        } else {
            if (this.discard && !this.discardStack.isEmpty()) {
                this.resetStack();
                return this.pickCard();
            }
        }
        return null;
    }

    /* if the stack is empty, reset it with mixed cards from the discard */
    public void resetStack() {
        while (!this.stack.isEmpty()) {
            Card c = this.stack.pop();
            this.discardStack.push(c);
        }
        ArrayList<Card> cards = new ArrayList<Card>();
        while (!this.discardStack.empty()) {
            Card card = this.discardStack.pop();
            cards.add(card);
        }
        this.initStack(cards);
    }

    public void discardCard(Card card) {
        this.discardStack.push(card);
    }

    public void mergeStacks(ArrayList<Stack<Card>> mergedStacks) {
        for (Stack<Card> cardStack : mergedStacks) {
            while (!cardStack.isEmpty()) {
                Card card = cardStack.pop();
                this.discardStack.push(card);
            }
        }
        System.out.println(this.discardStack.size());
        this.resetStack(); // shuffle the new stack
    }

    public ArrayList<Stack<Card>> splitCards(int n) {
        this.resetStack(); // to have all the cards in the stack
        ArrayList<Stack<Card>> cardStack = new ArrayList<Stack<Card>>();
        for (int i = 0; i < n; i++) {
            cardStack.add(new Stack<Card>());
        }
        int j = 0;
        while (!this.stack.isEmpty()) {
            Card card = this.stack.pop();
            cardStack.get(j % (n - 1)).add(card);
            j++;
        }
        return cardStack;
    }

    public int stackSize() {
        return this.stack.size();
    }

    public int discardSize() {
        return this.discardStack.size();
    }
}
