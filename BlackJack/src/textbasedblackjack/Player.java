package textbasedblackjack;

import java.util.ArrayList;

public class Player {

    
    //HOW TO IMPLEMENT SPLITTING
    //change the player hand to be an arrayList of arrayList<card>
    //soo they can have more than one hand
    //would have to change alot of shit wow alot of shit
    
    
    //------------------------------------------------------------
    private ArrayList<Hand> hands;
    private int balance;
    
    /**
     * Constructor for Player
     * @param num starting balance for the player
     */
    public Player( int num) {
        hands = new ArrayList<>();
        balance = num;
    }
    
    /**
     * Method to determine if all of the player's hands busted
     * @return true if all the hands busted
     */
    public boolean allHandsBusted() {
        boolean status = true;
        for (int x = 0; x < hands.size(); x++) {
            status = status && hands.get(x).getDidBust();
        }
        return status;
    }
    
    /**
     * function to calculate the total amount of bets per hand
     * @return the total amount of bets
     * 
     */
    public int handBetTotals() {
        int sum = 0;
        for ( int x = 0; x < hands.size(); x ++) {
            sum += hands.get(x).getBetAmount();
        }
        return sum;
    }
    
    /**
     * Adds hand to the player Hands
     */
    public void createHand() {
        hands.add(new Hand());
    }
    
    /**
     * Adds newly dealt card to the players hand
     * 
     * @param card card in players hand
     */
    public void addToHand(int hand,Card card) {
        hands.get(hand).addToHand(card);
    }
    
    /**
     * getter for hand in hands
     * @return return hand
     */
    public Hand getHand(int hand) {
        return hands.get(hand);
    }
    
    /**
     * basic getter for field hands    
     * @return
     */
    public ArrayList<Hand> getHands() {
        return hands;
    }

    /**
     * Simple getter for balance field
     * @return balance
     */
    public int getBalance() {
        return balance;
    }
    
    /**
     * Player won their bet
     * @param num new balance value
     */
    public void increaseBalance(int num) {
        balance += num;
    }
    
    /**
     * Player lost their bet
     * @param num new balance value
     */
    public void decreaseBalance(int num) {
        balance -= num;
    }
}
