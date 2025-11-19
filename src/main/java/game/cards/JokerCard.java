package game.cards;

public class JokerCard implements AbstractCard {



    @Override
    public int getValue() {
        return 14;
    }

    @Override
    public boolean isJoker() {
        return true;
    }

    @Override
    public String getStringValue() {
        return "Jo";
    }
}
