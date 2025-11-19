package game.cli;

import game.cards.*;
import game.gui.helper.BlackJackHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlackJackGame implements GameInterface{
    @Override
    public void startGame() {

        System.out.println("-----How to Play-----");



        boolean playAgain = true;

        while(playAgain) {

            Deck deck = BlackJackHelper.generateDeck();

            List<AbstractCard> dealerCards = new ArrayList<>();
            List<AbstractCard> playersCards = new ArrayList<>();

            dealerCards.add(deck.removeCard());
            playersCards.add(deck.removeCard());
            playersCards.add(deck.removeCard());

            System.out.println("Player cards: " + getHandOutput(playersCards));
            System.out.println("Dealer cards: " + getHandOutput(dealerCards)); //Dealer only shows one card

            boolean isPlayerBust = false;
            boolean isDealerBust = false;


            //Player Actions
            System.out.println("Hit or stand? (h/s)");
            String userAnswer = InputHandler.getUserInputAllowedCharacters(new HashSet<>(List.of("h","s")));
            while(!userAnswer.equalsIgnoreCase("s")) {
                AbstractCard newCard = deck.removeCard();
                playersCards.add(newCard);
                System.out.println("You were dealt a " + newCard.getStringValue());

                if(BlackJackHelper.getHandWinSum(playersCards) > 21) {
                    System.out.println("Bust! You lose");
                    isPlayerBust = true;
                    break;
                }

                System.out.println("Current hand: " + getHandOutput(playersCards));

                System.out.println("Hit or stand? (h/s)");
                userAnswer = InputHandler.getUserInputAllowedCharacters(new HashSet<>(List.of("h","s")));
            }

            if(isPlayerBust) {
                playAgain = getPlayAgain();
                continue;
            }

            //Dealer actions
            while(BlackJackHelper.getHandWinSum(dealerCards) < 17) { //Dealer always stands on 17
                AbstractCard newCard = deck.removeCard();
                dealerCards.add(newCard);
                System.out.println("Dealer draws a " + newCard.getStringValue() + ", current hand is " + getHandOutput(dealerCards));

                if(BlackJackHelper.getHandWinSum(dealerCards) > 21) {
                    System.out.println("Dealer busts!! You Win");
                    isDealerBust = true;
                    break;
                }

            }

            if(isDealerBust) {
                playAgain = getPlayAgain();
                continue;
            }

            int playerTotal = BlackJackHelper.getHandWinSum(playersCards);
            int dealerTotal = BlackJackHelper.getHandWinSum(dealerCards);

            System.out.println("Player holds a " + playerTotal + " and the dealer holds a " + dealerTotal);

            if(playerTotal >= dealerTotal) {
                System.out.println("You win!!");
            } else {
                System.out.println("You lose...");
            }

            playAgain = getPlayAgain();


        }

    }

    private boolean getPlayAgain() {
        System.out.print("\nPlay Again? (y/n)");
        String playerAnswer = InputHandler.getUserInputAllowedCharacters(new HashSet<>(List.of("y","n")));
        return playerAnswer.equalsIgnoreCase("y");
    }




    private String getHandOutput(List<AbstractCard> hand) {
        StringBuilder output = new StringBuilder();
        for(AbstractCard card : hand) {
            output.append(card.getStringValue()).append(" ");
        }
        return output.toString();
    }


}
