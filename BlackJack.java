/* 
* Ryan Lee & Michael Coker
* 1/22/2026
* CPSC-39-12704
* ArrayAndCards Review
*/


import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    /* Ryan
    Suits and Ranks are determined by using the remainder of 4 (How many suits there are) and 13 (how many ranks there are).
    That remainder determines what suit/rank the card will be. 
    For example, in SUITS:
    Hearts = index 0
    Diamonds = index 1
    Clubs = index 2
    Spades = index 3
    */

    // Arrays for suits, ranks, and current deck.
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    /* Ryan
    Redid dealer card data storage to account for Ace check. Added dealerData to store array from dealInitialDealerCards and modified dealerTotal
    to take in 0 index of dealerData array as total. Boolean dealerAce added to check if index 1 of dealerData (face card rank) is true/false; passed into
    playerTurn to check for Ace and offer insurance.
    */
    // Run game.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeDeck();
        shuffleDeck();

        int playerTotal = dealInitialPlayerCards();
        int[] dealerData = dealInitialDealerCards();
        int dealerTotal = dealerData[0];
        boolean dealerAce = (dealerData[1] == 12);

        playerTotal = playerTurn(scanner, playerTotal, dealerAce);
        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            return;
        }

        dealerTotal = dealerTurn(dealerTotal);
        determineWinner(playerTotal, dealerTotal);

        scanner.close();
    }
    
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }
    
    // Shuffles the deck.
    private static void shuffleDeck() {
        
        // Uses random to shuffle the whole 52 card deck.
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }

        // Unnecessary because it clutters the GUI.
        /*
        System.out.println("printed deck");
        for (int i = 0; i < DECK.length; i++) {
            System.out.println(DECK[i] + " ");
        }
        */

    }

    // Handing out player starting cards.
    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and "
                + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }

    
    /* Ryan
    Completely redid the entire method. Added hiddenCard and faceCard to hold values for both hidden and face cards for dealer. Refer to the
    top to see how card values are chosen. Added totalScore to independently calculate and return total. Method now returns array with total 
    and face card value to check for Ace. System.out.print now outputs only the face card.
    */

    // Handing out dealer starting cards.
    private static int[] dealInitialDealerCards() {
        int hiddenCard = dealCard();
        int faceCard = dealCard();
        int totalScore = cardValue(hiddenCard) + cardValue(faceCard);
        System.out.println("Dealer's card: " + RANKS[faceCard] + " of " + SUITS[faceCard % 4]);
        return new int[] {totalScore, faceCard};
    }

    // Simulates player turn.
    private static int playerTurn(Scanner scanner, int playerTotal, boolean dealerAce) {
        
        /* Ryan
        Added a boolean value to pass into playerTurn for Ace check. If statement below checks if true.
        If true then system will ask if player wants insurance; accepts only yes/no inputs. No betting
        system implemented yet, assume system automatically allocates bet when insurance is accepted.
        */

        // Insurance Check and Offering
        if (dealerAce) { // Runs if dealerAce is true.

            // Asks player if they want insurance and stores input into insurance Option.
            System.out.print("Dealer has an Ace. Do you want insurance? (Yes/No): ");
            String insuranceOption = scanner.next().toLowerCase();
            
            // Checks if player said yes or no.
            if (insuranceOption.equals("yes")) {
                System.out.println("Insurance accepted.");
            } else {
                System.out.println("Insurance declined.");
            }
        }


        // Continously works until player stands.
        while (true) {
            // Player actions
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            
            // Simulating actions.
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                System.out.println("new card index is " + newCard);
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
                if (playerTotal > 21) {
                    break;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }

        return playerTotal;
    }

    // Displays dealer hand.
    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

    // Compares dealer and player hands to determine winner.
    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
        }
    }

    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    private static int cardValue(int card) {
        return card < 9 ? card + 2 : 10;
    }

    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
}
