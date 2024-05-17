package textbasedblackjack;
import java.util.ArrayList;


public class Dealer {

    
    private ArrayList<Card> hand;
    
    public Dealer() {
        hand = new ArrayList<Card>();
    }
    
    /**
     * Adds newly dealt card to the players hand
     * @param card card in players hand
     */
    public void addToHand(Card card) {
        hand.add(card);
    }
    
    public String showOneCard() {
        return hand.get(0).getValue();
    }
    
    /**
     * used to clear hand after every hand
     */
    public void clearHand() {
        hand.clear();
    }
    
    /**
     * Basic getter for field hand
     * @return return hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }
}
