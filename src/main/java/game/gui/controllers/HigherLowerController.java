package game.gui.controllers;

import game.Game;
import game.cards.AbstractCard;
import game.cards.Card;
import game.gui.Main;
import game.gui.helper.CardPicker;
import game.gui.scenes.GameOverScene;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.Random;

public class HigherLowerController extends Scene {

    private SimpleIntegerProperty points;
    private ImageView currentImage;
    private ImageView nextImage;
    private final Random rand;
    private AbstractCard currentCard;
    private AbstractCard nextCard;
    private boolean isHideUI = true;
    private SimpleBooleanProperty actionInProgress;

    public HigherLowerController() {
        super(new HBox());
        HBox root = (HBox) getRoot();

        Main.applyWindowSize(root);
        root.setId("root");

        this.rand = new Random();
        this.points = new SimpleIntegerProperty(0);
        actionInProgress = new SimpleBooleanProperty(false);

        var spacer = new Region();
        var spacer1 = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        root.getChildren().add(getCurrentUI());
        root.getChildren().add(spacer);
        root.getChildren().add(getMiddleUI());
        root.getChildren().add(spacer1);
        root.getChildren().add(getRightUI());

        //Select first card
        this.currentCard = getNextCard();
        this.currentImage.setImage(CardPicker.getCardImage(this.currentCard));

        //Set next card
        this.nextImage.setImage(CardPicker.getCardBack());

        getStylesheets().add("file:src/main/resources/stylesheets/higherlower.css");

    }

    private VBox getCurrentUI() {
        VBox vBox = new VBox(10);
        vBox.setVisible(isHideUI);
        vBox.setAlignment(Pos.CENTER);

        var heading = new Label("Current");
        heading.setId("heading");

        this.currentImage = new ImageView();
        currentImage.setPreserveRatio(true);
        currentImage.setFitHeight(250);

        vBox.getChildren().add(heading);
        vBox.getChildren().add(this.currentImage);

        return vBox;
    }

    private VBox getMiddleUI() {
        VBox vBox = new VBox();
        vBox.setId("middleVbox");

        var higherButton = new Button("Higher");
        higherButton.disableProperty().bind(actionInProgress);
        higherButton.opacityProperty().bind(actionInProgress.map(active -> active ? 0.7 : 1));
        higherButton.setOnAction(e -> action("h"));

        var lowerButton = new Button("Lower");
        lowerButton.disableProperty().bind(actionInProgress);
        lowerButton.opacityProperty().bind(actionInProgress.map(active -> active ? 0.7 : 1));
        lowerButton.setOnAction(e -> action("l"));

        var pointsLabel = new Label("Points: 0");
        pointsLabel.textProperty().bind(this.points.asString("Points: %d"));

        var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        var paddingRegion = new Pane();
        paddingRegion.setMinHeight(50);

        vBox.getChildren().add(paddingRegion);
        vBox.getChildren().add(higherButton);
        vBox.getChildren().add(lowerButton);
        vBox.getChildren().add(spacer);
        vBox.getChildren().add(pointsLabel);

        return vBox;
    }

    private VBox getRightUI() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        var heading = new Label("Next");
        heading.setId("heading");

        this.nextImage = new ImageView();
        nextImage.setPreserveRatio(true);
        nextImage.setFitHeight(250);

        vBox.getChildren().add(heading);
        vBox.getChildren().add(this.nextImage);

        return vBox;

    }

    //Game Logic
    private Card getNextCard(AbstractCard currentCard) {
        //Next card cannot equal current card
        int nextNumber = rand.nextInt(1, 15);
        while(nextNumber == currentCard.getValue()) {
            nextNumber = rand.nextInt(1, 15);
        }

        return new Card(nextNumber);
    }

    private Card getNextCard() {
        return new Card(rand.nextInt(1, 15));
    }



    private void flipCard(ImageView cardView, Image newImage) {
        ScaleTransition shrink = new ScaleTransition(Duration.millis(150), cardView);
        shrink.setFromX(1);
        shrink.setToX(0);

        ScaleTransition expand = new ScaleTransition(Duration.millis(150), cardView);
        expand.setFromX(0);
        expand.setToX(1);

        shrink.setOnFinished(event -> cardView.setImage(newImage));
        shrink.play();
        shrink.setOnFinished(e -> {
            cardView.setImage(newImage);
            expand.play();
        });
    }

    private void action(String choice) {

        // Keep a reference to the old current card
        AbstractCard oldCurrent = this.currentCard;

        // Generate the next card based on current
        AbstractCard nextCard = getNextCard(oldCurrent);
        Image nextCardImage = CardPicker.getCardImage(nextCard);

        // Flip the next card
        flipCard(this.nextImage, nextCardImage);



        // Determine if the player's guess is correct
        boolean isNextHigher = nextCard.getValue() > oldCurrent.getValue();
        boolean correctGuess = (choice.equals("h") && isNextHigher) || (choice.equals("l") && !isNextHigher);

        this.actionInProgress.set(true);


        // Delay so user sees the flip
        PauseTransition pause = new PauseTransition(Duration.millis(1500));
        pause.setOnFinished(e -> {
            if (correctGuess) {
                this.points.set(this.points.get() + 10);

                // Swap images
                this.currentImage.setImage(this.nextImage.getImage());
                this.nextImage.setImage(CardPicker.getCardBack());

                // Update current card
                this.currentCard = nextCard;
                this.actionInProgress.set(false);

            } else {
                Main.setScene(new GameOverScene(this.points.get(), new HigherLowerController()));
            }
        });
        pause.play();
    }



}
