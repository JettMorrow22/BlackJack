package textbasedblackjack;

public class Dealer {

    private Hand hand;
    
    public Dealer() {
        hand = new Hand();
    }
    
    /**
     * Adds newly dealt card to the players hand
     * @param card card in players hand
     */
    public void addToHand(Card card) {
        hand.addToHand(card);
    }
    
    /**
     * Special function that only shows the dealers first card when it is the
     * players turn
     * @return the dealers first card value
     */
    public String showOneCard() {
        return hand.getHand().get(0).getValue();
    }
    
    /**
     * Basic getter for field hand
     * @return return hand
     */
    public Hand getHand() {
        return hand;
    }
}
