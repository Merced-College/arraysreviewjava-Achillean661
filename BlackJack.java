/* 
* Ryan Lee
* 1/22/2026
* CPSC-39-12704
* ArrayAndCards Review
*/


import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    // Arrays for suits, ranks, and current deck.
    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static int currentCardIndex = 0;

    // Run game.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeDeck();
        shuffleDeck();

        int playerTotal = dealInitialPlayerCards();
        int dealerTotal = dealInitialDealerCards();
        boolean dealerAce = false;

        playerTotal = playerTurn(scanner, playerTotal);
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

    // Handing out dealer starting cards.
    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }

    // Simulates player turn.
    private static int playerTurn(Scanner scanner, int playerTotal) {
        
        // Continously works until player stands.
        while (true) {
            // if statement if dealer's card is ace. Offer insurance. 
            // if (dealerAce == true) 
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
