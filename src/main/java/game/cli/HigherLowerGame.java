package game.cli;

import game.cards.Card;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class HigherLowerGame implements GameInterface {

    private final Random rand;

    public HigherLowerGame() {
        this.rand = new Random();
    }

    @Override
    public void startGame() {

        System.out.println("-----How To Play-----" +
                "\n1. A card is shown" +
                "\n2. Do you think the next random card will be a higher or lower value (Cards values are 1-15)" +
                "\n3. If you are right you play again, if wrong it's game over" +
                "\n4. Try get as many points as possible\n");

        Card currentCard = new Card(rand.nextInt(1, 15));
        Card tempCard;
        int points = 0;
        int pointIncrements = 10;

        boolean playAgain = true;
        while(playAgain) {

            while(true) {
                System.out.println("Current Points: " + points);
                System.out.printf("Your card is a %d of %s, higher or lower (h/l)?", currentCard.getValue(), currentCard.getSuit());

                String userInput = InputHandler.getUserInputAllowedCharacters(new HashSet<>(List.of("h", "l")));
                tempCard = getNextCard(currentCard);

                if(userInput.equalsIgnoreCase("h")) {
                    //New card must be higher than lower card
                    if(tempCard.getValue() > currentCard.getValue()) {
                        System.out.println("Correct!! It was a " + tempCard.getValue());
                        System.out.println("You gain " + pointIncrements + " points");
                    } else {
                        break;
                    }
                } else {
                    //New card must be lower than current card
                    if(tempCard.getValue() < currentCard.getValue()) {
                        System.out.println("Correct!! It was a " + tempCard.getValue());
                        System.out.println("You gain " + pointIncrements + " points");
                    } else {
                        break;
                    }
                }
                points += pointIncrements;
                currentCard = tempCard;

            }

            System.out.println("Wrong!! It was a " + tempCard.getValue());
            System.out.println("Game Over");
            System.out.println("Points: " + points);

            playAgain = getPlayAgain();

        }

    }

    private boolean getPlayAgain() {
        System.out.print("\nPlay Again? (y/n)");
        String playerAnswer = InputHandler.getUserInputAllowedCharacters(new HashSet<>(List.of("y","n")));
        return playerAnswer.equalsIgnoreCase("y");
    }




    private Card getNextCard(Card currentCard) {
        //Next card cannot equal current card
        int nextNumber = rand.nextInt(1, 15);
        while(nextNumber == currentCard.getValue()) {
            nextNumber = rand.nextInt(1, 15);
        }

        return new Card(nextNumber);
    }
}
