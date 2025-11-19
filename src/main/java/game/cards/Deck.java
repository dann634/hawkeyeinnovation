package game.cards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private ArrayDeque<AbstractCard> deck;

    public Deck() {
        this.deck = new ArrayDeque<>();
    }

    public void addCard(AbstractCard card) {
        this.deck.push(card);
    }

    public AbstractCard removeCard() {
        return this.deck.pop();
    }

    public int size() {
        return this.deck.size();
    }

    public void shuffleDeck() {
        List<AbstractCard> tempList = new ArrayList<>();
        while(!this.deck.isEmpty()) {
            tempList.add(this.removeCard());
        }

        Collections.shuffle(tempList);

        for(AbstractCard card : tempList) {
            this.addCard(card);
        }
    }
}
