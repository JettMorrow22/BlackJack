package textbasedblackjack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    
    private static Scanner scanner;
    private static Deck deck;
    private static Player player;
    private static Dealer dealer;

    private static HashMap<String, Integer> cardValues;


    public static void main(String[] args) {
        cardValues = createCardValuesMap();
        
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
            "~~~~~~~~~");
        System.out.println("\tWELCOME TO JETT MORROW'S BLACKJACK GAME");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
            "~~~~~~~~~");
        
        scanner = new Scanner(System.in);
        
        //Determine number of Decks, intialize decks, shuffle decks
        System.out.print("Enter the number of decks you wish to play with: ");
        int decks = scanner.nextInt();
        //validates number of decks input
        while ( decks <= 0) {
            System.out.println("Invalid Input. Try Again");
            System.out.print("Enter the number of decks you wish to play with: ");
            decks = scanner.nextInt();
        }
        deck = new Deck(decks);
        
        //Determine buy in and initialize player
        System.out.print("How much will you be buying in for: $");
        int startingBalance = scanner.nextInt();
        while ( startingBalance <= 0) {
            System.out.println("Please enter a positve number.");
            System.out.print("How much will you be buying in for: $");
            startingBalance = scanner.nextInt();
        }
        player = new Player(startingBalance);
        dealer = new Dealer();

        bettingCycle();
        
        
        System.out.println("THANK YOU FOR PLAYING");
        System.out.println("Player cashed out at: " + player.getBalance());
        scanner.close();
        System.exit(0);
    }
    
    /**
     * Cycle of player playing his hand
     * option to hit or stand
     * Keeps playing untill player does not have enought balance or player
     * doesnt wanna play
     */
    public static void bettingCycle() {
        boolean playAgain = true;
        
        while (playAgain && player.getBalance() > 0) {
            //create a player hand
            player.createHand();
            
            //get betting amount and update players balance
            System.out.print("How much would you like to bet: $");
            player.getHand(0).setBetAmount(scanner.nextInt());
            //check if they have this money
            while (player.getHand(0).getBetAmount() > player.getBalance() 
                    && player.getHand(0).getBetAmount() > 0) {
                System.out.println("Invalid Input. Try Again");
                System.out.print("How much would you like to bet: $");
                player.getHand(0).setBetAmount(scanner.nextInt());
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
                "~~~~~~~~~~~~~~~");
            System.out.println("Okay, No more bets!\n");
            
            
            
            //deal out to player and dealer
            player.addToHand(0, deck.deal());
            dealer.addToHand(deck.deal());
            player.addToHand(0, deck.deal());
            dealer.addToHand(deck.deal());
            
            //if player has BJ, pay em, clear hands, exit while loop
            if ( calculateHand(player.getHand(0).getHand()) == 21) {
                System.out.println("Player hit BLACKJACK");
                player.increaseBalance((3 * player.getHand(0).getBetAmount())/2);
                showFullTable();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~");
                player.getHand(0).getHand().clear();
                dealer.getHand().clear();
                continue;
            }
            
            
            //check if player has money to split or double
            if ( player.getBalance() >= 2 * player.getHand(0).getBetAmount() ) {
                //check if the hand can split
                
                //check if card value is the same 
                if ( cardValues.get(player.getHand(0).getCard(0).getValue())
                    == cardValues.get(player.getHand(0).getCard(1).getValue()) ){
                    wantToSplit(player.getHand(0));
                }
                else {
                    wantToDouble(player.getHand(0));
                }
            }
            else {
                hitOrStand(player.getHand(0));
            }
            
            String[] results = new String[2];
            //NOW IT IS THE DEALERS TURN
            //if all the players hands busted then skip and take money
            if (player.allHandsBusted()) {
                //decrease balance
                //show table
                //show that both hands busted
                for (int x = 0; x < player.getHands().size(); x++) {
                    player.decreaseBalance(player.getHand(x).getBetAmount());
                    results[x] = "Hand " + (x+1) + ": BUSTED";
                }
            }
            else {
                //all players did not bust so I must create dealers best hand
                while(calculateHand(dealer.getHand()) < 17) {
                    //give dealer new card
                    dealer.addToHand(deck.deal());
                    //method checks if busts, if so, checks for ace and
                    //cahnges to soft
                    didBust(dealer.getHand());
                }
                
                //NOW NEED TO COMPARE EACH PLAYER HAND WITH THE DEALER HAND
                for ( int x = 0; x < player.getHands().size(); x++) {       
                    
                    //if the hand busted they automatically lost
                    if (player.getHand(x).getDidBust()) {
                        player.decreaseBalance(player.getHand(x).getBetAmount());
                        results[x] = "Hand " + (x + 1) + ": PLAYER BUSTED";
                        continue;
                    }
                    
                    //if dealer busted they win since they didnt bust
                    if (calculateHand(dealer.getHand()) > 21) {
                        player.increaseBalance(player.getHand(x).getBetAmount());
                        results[x] = "Hand " + (x+1) + ": DEALER BUSTED, PLAYER WON";
                        continue;
                    }
                    
                    //neither hand busted so compare player with the dealer
                    if(calculateHand(player.getHand(x).getHand()) > 
                            calculateHand(dealer.getHand()) ) {
                        //player wins
                        player.increaseBalance(player.getHand(x).getBetAmount());
                        results[x] = "Hand " + (x+1) +": PLAYER WON";
                    }
                    else if(calculateHand(player.getHand(x).getHand()) < 
                            calculateHand(dealer.getHand())){
                        //dealer wins
                        player.decreaseBalance(player.getHand(x).getBetAmount());
                        results[x] = "Hand " + (x+1) + ": DEALER WON";
                    }
                    else {
                        results[x] = "PUSH";
                    }
                    
                }
            }
            //show the table and the results from the hands
            showFullTable();
            for (int x = 0; x < player.getHands().size(); x++) {
                System.out.println("" + results[x]);
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                "~~~~~~~~~~~~~~~~~~~");
            
            
            //Deciding if they want to play again
            if (player.getBalance() != 0) {
                //Do they wanna play again
                System.out.print("Current Balance: " + player.getBalance() + 
                    " - Play Again??? y or n:");
                String playAgainInput = scanner.next();
                
                //Validates input
                while (!playAgainInput.equals("y") && 
                        !playAgainInput.equals("n")) {
                    System.out.println("Invalid Input. Try Again");
                    System.out.print("Current Balance: " + player.getBalance() + 
                        " - Play Again??? y or n:");
                    playAgainInput = scanner.next();
                }
                //player is done playing
                if ( playAgainInput.equals("n")) {
                    //check if player wants to play
                    playAgain = false;
                }
            }
            else {
                System.out.println("You have officialy gone broke....");
                playAgain = false;
            }
            
            player.getHands().clear();
            dealer.getHand().clear();
        }
    }
    
    
    public static void wantToSplit(Hand hand) {
        
        //onlly show one of dealer cards
        System.out.println("Dealer Has: " + dealer.showOneCard());
        System.out.println("\nPlayer Has: " + showHand(player.getHand(0).getHand())
        + " - Total: " + calculateHand(player.getHand(0).getHand()));
        
        //ask hit or stand
        System.out.print("Would you like to SPLIT(sp), DOUBLE(d),"
            + " HIT(h) or STAND(s):");
        String option = scanner.next();
        
        //checks for valid input
        while (!option.equals("sp") && !option.equals("d") &&
            !option.equals("s") && !option.equals("h"))
        {
            System.out.println("Invalid Input. Try Again");
            System.out.print("Would you like to SPLIT(sp), DOUBLE(d)," + 
                " HIT(h) or STAND(s):");
            option = scanner.next();
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
            "~~~~~~~~~");
        
        switch(option) {
            case "sp":
                //create second hand, finish one hand at a time
                player.createHand();
                player.getHand(1).setBetAmount(player.getHand(0).getBetAmount());
                player.getHand(1).addToHand(hand.getHand().remove(1));
                
                player.getHand(1).addToHand(deck.deal());
                wantToDouble(player.getHand(1));
                
                player.getHand(0).addToHand(deck.deal());
                wantToDouble(player.getHand(0));
                //update bet
                return;
            case "d":
                wantToDouble(hand);
                return;
            case "h":
                hitOrStand(hand);
                return;
            case "s":
                return;
        }
    }
    
    
    /**
     * Method for the player's betting actions Hit or stand
     * recursively calls the function untill the player stands or busts
     * @param hand players hand
     */
    public static void hitOrStand(Hand hand) {
        //onlly show one of dealer cards
        System.out.println("Dealer Has: " + dealer.showOneCard());
        for (int x = 0; x < player.getHands().size(); x++) {
            System.out.println("\nPlayer Has: " + showHand(player.getHand(x).getHand())
                + " - Total: " + calculateHand(player.getHand(x).getHand()));
        }
        
        
        //ask hit or stand
        System.out.print("Would you like to HIT(h) or STAND(s):");
        String option = scanner.next();
        
        //checks for valid input
        while (option.equals("s") && option.equals("h"))
        {
            System.out.println("Invalid Input. Try Again");
            System.out.print("Would you like to HIT(h) or STAND(s):");
            option = scanner.next();
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
            "~~~~~~~~~");
        
        switch (option) {
            case "s": 
                return;
            case "h":
                hand.addToHand(deck.deal());
                if (didBust(hand.getHand())) {
                    hand.setDidBust(true);
                    return;
                }
                hitOrStand(hand);
                return;
            default: 
                System.out.println("Invalid Input. Try again");
                hitOrStand(hand);
                return;
        }
    }

    
    /**
     * function called for the players first decision of a hand
     * player only gets to decide to double on third card
     * still returns true if busted false if not
     * @param hand
     */
    public static void wantToDouble (Hand hand) {
      //onlly show one of dealer cards
        System.out.println("Dealer Has: " + dealer.showOneCard());
        for (int x = 0; x < player.getHands().size(); x++) {
            System.out.println("\nPlayer Has: " + showHand(player.getHand(x).getHand())
                + " - Total: " + calculateHand(player.getHand(x).getHand()));
        }
        
        //ask hit or stand or double
        System.out.print("Would you like to HIT(h), STAND(s), or DOUBLE(d):");
        String option = scanner.next();
        
        //checks for valid input
        while (!option.equals("s") && !option.equals("h") && !option.equals("d"))
        {
            System.out.println("Invalid Input. Try Again");
            System.out.print("Would you like to HIT(h), STAND(s), or DOUBLE(d):");
            option = scanner.next();
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+
            "~~~~~~~~~");
        
        switch (option) {
            case "s": 
                return;
            case "h":
                hand.addToHand(deck.deal());
                if (didBust(hand.getHand())) {
                    hand.setDidBust(true);
                    return;
                }
                hitOrStand(hand);
                return;
            case "d": 
                //check if they have money to double, give card
                //check if balance >= hand totals + cur hand
                
                if (player.getBalance() >= 
                        player.handBetTotals() + hand.getBetAmount() ) {
                    hand.setBetAmount(hand.getBetAmount() * 2);
                    hand.addToHand(deck.deal());
                    if ( didBust(hand.getHand())) {
                        hand.setDidBust(true);
                    }
                    return;
                }
                System.out.println("You do not have enough to double.");
                hitOrStand(hand);
                return;
                
            default: 
                System.out.println("Invalid Input. Try Again");
                hitOrStand(hand);
        }
    }
    
    
    
    /**
     * Function to show the full hand of both the dealer and player
     */
    public static void showFullTable() {
        System.out.println("Dealer Has: " + showHand(dealer.getHand()) 
            + " - Total: " + calculateHand(dealer.getHand()));
        
        //loop through all the player hands
        for (int x = 0; x < player.getHands().size(); x ++) {
            System.out.println("\nPlayer Has: " + showHand(player.getHand(x).getHand())
            + " - Total: " + calculateHand(player.getHand(x).getHand()));
        }
        
        System.out.println("Player Balance: " + player.getBalance());
    }
  
    /**
     * simple method to determine if the hand busted
     * added functionality of soft aces, if hand is busted, check if it has an
     * ace, if so change it to soft and return false
     * @param hand hand in questoin
     * @return true if busted false if not
     */
    public static Boolean didBust(ArrayList<Card> hand) {
        
        if (calculateHand(hand) > 21) {
            if (hasAce(hand)) {
                changeAceToSoft(hand);
                return calculateHand(hand) > 21;
            }
            return true;
        }
        return false;
    }
    
    
    /**
     * Method to display the cards in a hand
     * @param hand hand to be displayed
     * @return string displaying the hand
     */
    public static String showHand(ArrayList<Card> hand) {
        String curHand = "";
        for( int x = 0; x < hand.size() -1; x++) {
            curHand += hand.get(x).getValue() + ",";
        }
        curHand += hand.get(hand.size()-1).getValue();
        return curHand;
    }
    
    
    /**
     * calculates the total of the hand using cardValues hashMap
     * @param hand arraylist of hand
     * @return total of hand
     */
    public static int calculateHand(ArrayList<Card> hand) {
        int sum = 0; 
        for (int x = 0; x < hand.size(); x++) {
            sum += cardValues.get(hand.get(x).getValue());
        }
        return sum;
    }
    
    /**
     * Function to determine if a hand contains an ace
     * @param hand hand in question
     * @return true if ace, false if not
     */
    public static boolean hasAce(ArrayList<Card> hand) {
        for (int x = 0; x < hand.size(); x++) {
            if (hand.get(x).getValue().equals("A")) {
                return true;
            }
        }
        return false;
 
    }
    
    /**
     * Function to change ace in hand to soft ace == 11 -> 1
     * @param hand hand that has the ace
     */
    public static void changeAceToSoft(ArrayList<Card> hand) {
        //find the ACE and change to ace
        int num = 0;
       
        while (!hand.get(num).getValue().equals("A")) { 
            num++;
        }
        hand.get(num).setValue("a");
    }
    
    
    /**
     * creates hashMap of card values strings to ints
     * @return hashMap
     */
    public static HashMap<String, Integer> createCardValuesMap() {
        HashMap<String, Integer> temp = new HashMap<String, Integer>();
        for (int x = 2; x <= 9; x++) {
            temp.put("" + x, x);
        }
        temp.put("T", 10);
        temp.put("J", 10);
        temp.put("Q", 10);
        temp.put("K", 10);
        temp.put("A", 11);
        temp.put("a", 1);
        return temp;
    }
    
}
