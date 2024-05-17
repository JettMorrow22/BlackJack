package textbasedblackjack;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    private ArrayList<Card> deck;
    private int numOfDecks;
    
    /**
     * Constructor for Deck, determines the num of Decks
     * and creates the deck ArrayList
     * @param num the number of decks to be made
     */
    public Deck(int num) {
        numOfDecks = num;
        deck = new ArrayList<Card>();
        initalizeDeck();
        shuffleDeck();
    }
    
    public void initalizeDeck() {
        //each deck has 4 of each number 2-A
        //13 total numbers * 4 so 52 cards * numOfDecks
        String[] classicDeck = {"2", "2", "2", "2", "3", "3", "3", "3", 
            "4", "4", "4", "4", "5", "5", "5", "5", "6", "6", "6", "6",
            "7", "7", "7", "7", "8", "8", "8", "8", "9", "9", "9", "9", 
            "T", "T", "T", "T", "J", "J", "J", "J", "Q", "Q", "Q", "Q", 
            "K", "K", "K", "K", "A", "A", "A", "A"};
        //loops through all the decks we gonna use and creates card with value
        //adds the card to the current working deck
        for (int decks = 0; decks < numOfDecks; decks++ ) {
            for (int x = 0; x < classicDeck.length; x++ ) {
                deck.add(new Card(classicDeck[x]));
            }
        }
    }
    
    /**
     * shuffles the deck using collections method
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
    
    /**
     * removes and returns the top card to deal 
     * @return the top card of the deck
     */
    public Card deal() {
        if ( deck.size() < ((numOfDecks * 52)/4) ) {
            deck.clear();
            initalizeDeck();
            shuffleDeck();
        }
        return deck.remove(0);
    }
    
    /**
     * Basic getter for field deck
     * @return deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }
    
    /**
     * Simple display method to see the deck that is being worked on
     */
    public void printDeck() {
        System.out.print("Deck = {" + deck.get(0).getValue() + ", ");
        for (int x = 1; x < deck.size() - 1; x++) {
            System.out.print(deck.get(x).getValue() + ", ");
        }
        System.out.println(deck.get(deck.size() - 1).getValue() + "}");
    }

}
