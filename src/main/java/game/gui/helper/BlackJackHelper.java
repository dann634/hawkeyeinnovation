package game.gui.helper;

import game.cards.*;

import java.util.List;

public class BlackJackHelper {

    private BlackJackHelper() {}

    public static int getHandWinSum(List<AbstractCard> hand) {
        int sum = 0;
        //Add all non-ace cards
        for(AbstractCard card : hand) {
            if(card.getValue() != 1) {
                if(card.getValue() > 10) {
                    sum += 10;
                } else {
                    sum += card.getValue();
                }
            }
        }

        //Get all combinations of Ace Cards
        for(AbstractCard card : hand) {
            if(card.getValue() == 1) {
                if(sum + 11 > 21) {
                    sum += 1;
                } else {
                    sum += 11;
                }
            }
        }
        return sum;
    }

    public static Deck generateDeck(){
        Deck deck = new Deck();
        for (int i = 1; i < 14; i++) {
            for(Suit suit : List.of(Suit.SPADE, Suit.CLUB, Suit.DIAMOND, Suit.HEART)) {
                deck.addCard(new Card(i, suit));
            }
        }
        deck.addCard(new JokerCard());
        deck.addCard(new JokerCard());
        deck.shuffleDeck();
        return deck;
    }

}
