package game.gui.helper;

import game.cards.AbstractCard;
import game.cards.Card;
import game.cards.Suit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class CardPicker {

    private CardPicker() {

    }

    private static Image pickCardFromImage(Card card) {


        int suitNum;
        switch (card.getSuit()) {
            case SPADE:
                suitNum = 0;
                break;
            case HEART:
                suitNum = 1;
                break;
            case DIAMOND:
                suitNum = 2;
                break;
            default:
                suitNum = 3;
        }


        Image deckImage = new Image("file:src/main/resources/images/deck.png");
        PixelReader reader = deckImage.getPixelReader();
        WritableImage newImage = new WritableImage(reader, 4 + (card.getValue() - 1) * 92, 7 + suitNum * 134, 88, 127);

        return newImage;
    }

    public static Image getJoker() {
        Image jokerImage = new Image("file:src/main/resources/images/joker.png");
        return jokerImage;
    }

    public static Image getCardBack() {
        Image cardBackImage = new Image("file:src/main/resources/images/cardBack.png");
        return cardBackImage;
    }

    public static Image getCardImage(AbstractCard card) {

        if(card.isJoker() || card.getValue() == 14) {
            return CardPicker.getJoker();
        }

        return pickCardFromImage((Card) card);
    }


}
