package pandemic.cards;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;

/* The class that defines a stack of card in the game */
public class CardsStack {

    /* The stack of cards */
    private Stack<Card> stack;

    /* The stack of discarded cards if the stack is discarded */
    private Stack<Card> discardStack;

    /* The boolean to tell if a stack is discarded or not */
    private boolean discard;

    /**
     * Builds a CardsStack for the game
     * 
     * @param cards The stack's cards
     * @param discard The boolean telling if a stack is discarded or not
     */
    public CardsStack(ArrayList<Card> cards, boolean discard) {
        this.stack = new Stack<Card>();
        this.initStack(cards);
        this.discardStack = new Stack<Card>();
        this.discard = discard;
    }

    /**
     * Get the stack of cards
     * 
     * @return The stack of cards
     */
    public Stack<Card> getCardStack() {
        return this.stack;
    }

    /**
     * Initialize the stack by randomly adding all the cards of an ArrayList into the stack
     * 
     * @param cards The cards to fullfill the stack with
     */
    public void initStack(ArrayList<Card> cards) {
        Random random = new Random();
        while (!cards.isEmpty()) {
            int i = random.nextInt(cards.size());
            Card card = cards.remove(i);
            this.stack.push(card);
        }
    }

    /**
     * Tell if the stack is empty or not
     * 
     * @return True if the stack is empty, else false
     */
    public boolean hasCardsLeft() {
        return this.stack.isEmpty();
    }

    /**
     * Pick the card on the top of the stack, if the stack is empty, it is fullfilled with the discarded stack
     * 
     * @return The popped card, null if the discarded stack and the stack are empty
     */
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

    /**
     * Resets the stack of cards by discarding all cards from the stack and init a "new" stack with the remaining cards
     */
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

    /**
     * Put a card into the discard stack
     * 
     * @param card The card to add into the discard stack
     */
    public void discardCard(Card card) {
        this.discardStack.push(card);
    }

    /**
     * Merge different stacks into one
     * 
     * @param mergedStacks The ArrayList of stacks to merge
     */
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

    /**
     * Splits the stack into n stacks
     * 
     * @param n The number you want to split the stack in
     * @return The ArrayList of stacks
     */
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

    /**
     * Get the stack size
     * 
     * @return The stack size
     */
    public int stackSize() {
        return this.stack.size();
    }

    /**
     * Get the discard stack size
     * 
     * @return The discard stack size
     */
    public int discardSize() {
        return this.discardStack.size();
    }

    public Stack<Card> getDiscardStack() {
        return this.discardStack;
    }
}
