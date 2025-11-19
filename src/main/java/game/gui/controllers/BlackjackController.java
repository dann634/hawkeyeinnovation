package game.gui.controllers;

import game.cards.AbstractCard;
import game.cards.Deck;
import game.gui.Main;
import game.gui.helper.BlackJackHelper;
import game.gui.helper.CardPicker;
import game.gui.scenes.GameOverScene;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BlackjackController extends Scene {

    private final Deck deck;
    private final List<AbstractCard> playerCards;
    private final List<AbstractCard> dealerCards;
    private final CardRow dealerCardRow;
    private final CardRow playerCardRow;
    private final Pane animationLayer;
    private SimpleBooleanProperty isPlayerPlaying;


    public BlackjackController() {
        super(new StackPane());
        StackPane stackRoot = (StackPane) getRoot();

        VBox layoutRoot = new VBox();
        Pane animationLayer = new Pane();         // ← floating cards go HERE
        animationLayer.setPickOnBounds(false);
        animationLayer.setMouseTransparent(true);

        Main.applyWindowSize(stackRoot);

        stackRoot.getChildren().addAll(layoutRoot, animationLayer);

        this.animationLayer = animationLayer;

        this.isPlayerPlaying = new SimpleBooleanProperty(true);


        getStylesheets().add("file:src/main/resources/stylesheets/blackjack.css");

        this.playerCards = new ArrayList<>();
        this.dealerCards = new ArrayList<>();

        this.deck = BlackJackHelper.generateDeck();

        playerCards.add(deck.removeCard());
        playerCards.add(deck.removeCard());

        dealerCards.add(deck.removeCard());

        this.dealerCardRow = new CardRow(dealerCards);
        this.dealerCardRow.addBackCard();

        this.playerCardRow = new CardRow(playerCards);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        HBox buttonRow = new HBox(20);
        buttonRow.setPadding(new Insets(0, 20, 0, 0));
        buttonRow.setAlignment(Pos.CENTER);

        var hitButton = new Button("Hit");
        hitButton.disableProperty().bind(this.isPlayerPlaying.not());
        hitButton.opacityProperty().bind(this.isPlayerPlaying.map(action -> action ? 1 : 0.7));

        var standButton = new Button("Stand");
        standButton.disableProperty().bind(this.isPlayerPlaying.not());
        standButton.opacityProperty().bind(this.isPlayerPlaying.map(action -> action ? 1 : 0.7));

        ImageView backCard = new ImageView(CardPicker.getCardBack());
        backCard.setFitHeight(125);
        backCard.setPreserveRatio(true);

        var spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        var spacer3 = new Pane();
        spacer3.setMinWidth(120);
        HBox.setHgrow(spacer3, Priority.ALWAYS);

        buttonRow.getChildren().addAll(spacer3, hitButton, standButton, spacer2, backCard);

        var spacer1 = new Region();
        VBox.setVgrow(spacer1, Priority.ALWAYS);

        layoutRoot.getChildren().addAll(dealerCardRow, spacer1, buttonRow, spacer, playerCardRow);


        hitButton.setOnAction(e -> {
            AbstractCard nextCard = deck.removeCard();
            playerCards.add(nextCard);

            int playerTotal = BlackJackHelper.getHandWinSum(playerCards);
            if(playerTotal > 21) {
                this.isPlayerPlaying.set(false);
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                pause.play();
                GameOverScene gameOverScene = new GameOverScene(new BlackjackController());
                gameOverScene.addReason("You went bust...");
                Main.setScene(gameOverScene);
            }
            if(playerTotal == 21) {
                this.isPlayerPlaying.set(false);
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                pause.play();
                dealerCardRow.removeCard();
                dealerPlay(backCard);
            }

            animateCardDeal(backCard, playerCardRow, nextCard);
        });

        standButton.setOnAction(e -> {
            this.isPlayerPlaying.set(false);

            //Dealer start playing
            dealerCardRow.removeCard();
            dealerPlay(backCard);
        });
    }

    private void dealerPlay(ImageView deckImage) {
        int dealerTotal = BlackJackHelper.getHandWinSum(dealerCards);

        // Dealer stops at 17 or more
        if (dealerTotal >= 17) {
            checkWinner();
            return;
        }

        // Dealer draws a card
        AbstractCard next = deck.removeCard();
        dealerCards.add(next);

        animateCardDeal(deckImage, dealerCardRow, next);

        // Wait for the animation to finish before continuing
        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(ev -> dealerPlay(deckImage)); // recursive continuation
        pause.play();
    }

    private void checkWinner() {
        int dealerTotal = BlackJackHelper.getHandWinSum(dealerCards);
        int playerTotal = BlackJackHelper.getHandWinSum(playerCards);
        GameOverScene gameOverScene = new GameOverScene(new BlackjackController());
        System.out.println("Dealer: " + dealerTotal);
        System.out.println("Player: " + playerTotal);

        if (dealerTotal > 21) {
            // Dealer bust → player wins
            gameOverScene.addReason("Dealer busts... You Win!!");
            Main.setScene(gameOverScene);
            return;
        }

        // Otherwise normal comparison
        if (dealerTotal > playerTotal) {
            // Dealer wins
            gameOverScene.addReason("You Lose...");
            Main.setScene(gameOverScene);
        } else {
            // Player wins
            gameOverScene.addReason("You Win!!");
            Main.setScene(gameOverScene);
        }
    }



    private void animateCardDeal(ImageView deckImage, CardRow targetRow, AbstractCard card) {

        Image finalFace = CardPicker.getCardImage(card);

        ImageView floating = new ImageView(finalFace);   // FACE UP
        floating.setFitHeight(125);
        floating.setPreserveRatio(true);

        animationLayer.getChildren().add(floating);

        // ABSOLUTE coordinate conversion
        var deckBounds = deckImage.localToScene(deckImage.getBoundsInLocal());
        var layerBounds = animationLayer.localToScene(animationLayer.getBoundsInLocal());

        double startX = deckBounds.getMinX() - layerBounds.getMinX();
        double startY = deckBounds.getMinY() - layerBounds.getMinY();

        floating.setLayoutX(startX);
        floating.setLayoutY(startY);

        // Target coordinates
        var targetBounds = targetRow.localToScene(targetRow.getBoundsInLocal());
        double endX = targetBounds.getMinX() - layerBounds.getMinX() + targetRow.getWidth() / 2;
        double endY = targetBounds.getMinY() - layerBounds.getMinY();

        double dx = endX - startX;
        double dy = endY - startY;

        TranslateTransition move = new TranslateTransition(Duration.millis(500), floating);
        move.setByX(dx);
        move.setByY(dy);

        move.setOnFinished(e -> {
            animationLayer.getChildren().remove(floating);
            targetRow.addCard(card);
        });

        move.play();
    }






    private static class CardRow extends HBox {

        private final int CARD_HEIGHT = 125;

        public CardRow(List<AbstractCard> cards) {

            setAlignment(Pos.CENTER);

            for(AbstractCard card : cards) {
                Image image = CardPicker.getCardImage(card);
                ImageView cardView = new ImageView(image);
                cardView.setFitHeight(CARD_HEIGHT);
                cardView.setPreserveRatio(true);

                getChildren().add(cardView);
            }

        }

        public void addBackCard() {
            ImageView backImageView = new ImageView(CardPicker.getCardBack());
            backImageView.setFitHeight(CARD_HEIGHT);
            backImageView.setPreserveRatio(true);
            getChildren().add(backImageView);
        }

        public void removeCard() {
            getChildren().remove(getChildren().size() - 1);
        }

        public void addCard(AbstractCard card) {
            Image image = CardPicker.getCardImage(card);
            ImageView cardView = new ImageView(image);
            cardView.setFitHeight(CARD_HEIGHT);
            cardView.setPreserveRatio(true);
            getChildren().add(cardView);
        }


    }
}
