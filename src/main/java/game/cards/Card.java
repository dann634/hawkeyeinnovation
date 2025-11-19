package game.cards;

import java.util.Random;

public class Card implements AbstractCard {

    private final int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Card(int value) {
        this.value = value;

        //Assign Random Suit
        Random rand = new Random();
        int randomNumber = rand.nextInt(0, 4);
        switch (randomNumber) {
            case 0:
                this.suit = Suit.CLUB;
                break;
            case 1:
                this.suit = Suit.DIAMOND;
                break;
            case 2:
                this.suit = Suit.SPADE;
                break;
            default:
                this.suit = Suit.HEART;
        }
    }

    @Override
    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean isJoker() {
        return false;
    }

    @Override
    public String getStringValue() {
        switch (this.value) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(this.value);
        }
    }

    @Override
    public String toString() {
        return String.format("Card[%d]", this.value);
    }
}
