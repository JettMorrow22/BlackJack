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
    
    public Card getCard(int num) {
        return hand.get(num);
    }
    
    public int getBetAmount() {
        return betAmount;
    }

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
    

    public boolean getDidBust() {
        return didBust;
    }

    public void setDidBust(boolean didBust) {
        this.didBust = didBust;
    }
       
}
