package textbasedblackjack;

import java.util.ArrayList;

public class Hand {
    
    private ArrayList<Card> hand;
    private boolean didBust;
    private int betAmount;
    
    
    public Hand() {
        hand = new ArrayList<Card>();
        didBust = false;
        betAmount = 0;
    }
    
    /**
     * Method to display the cards in a hand
     * @param hand hand to be displayed
     * @return string displaying the hand
     */
    public String showHand() {
        String curHand = "";
        for( int x = 0; x < hand.size() - 1; x++) {
            curHand += hand.get(x).getValue() + ",";
        }
        curHand += hand.get(hand.size()-1).getValue();
        return curHand;
    }
    
    /**
     * Returns the specific card of the hand
     * @param num the card to be selected
     * @return the num-th card of the hand
     */
    public Card getCard(int num) {
        return hand.get(num);
    }
    
    /**
     * Basic getter for betAmount field
     * @return the value of betAmount
     */
    public int getBetAmount() {
        return betAmount;
    }

    /**
     * Bassic setter for betAmount field
     * @param betAmount new value for betAmount
     */
    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    /**
     * adds to hand
     * @param card card to add
     */
    public void addToHand(Card card) {
        hand.add(card);
    }
    
    /**
     * Determines if the hand is perfect pair
     * @return true if pairs
     */
    public boolean canSplit() {
        return hand.size() == 2 && 
            hand.get(0).equals(hand.get(1));
    }

    /**
     * Basic getter for hand
     * @return hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    /**
     * Basic getter for didBust field
     * @return didBust field
     */
    public boolean getDidBust() {
        return didBust;
    }

    /**
     * Basic setter for didBust Field
     * @param didBust new value for field
     */
    public void setDidBust(boolean didBust) {
        this.didBust = didBust;
    }
       
}
