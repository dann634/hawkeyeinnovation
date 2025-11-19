package game.gui.scenes;

import game.gui.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InfoPage extends Scene {


    public InfoPage(String contentStr, Scene nextScene) {

        super(new VBox());
        VBox root = (VBox) getRoot();

        root.setStyle("" +
                "-fx-alignment: center;" +
                "-fx-spacing: 25;" +
                "-fx-min-width: 1024;" +
                "-fx-min-height: 544;" +
                "-fx-padding: 100");


        var playLabel = new Label("How to Play");
        playLabel.setStyle("" +
                "-fx-font-size: 24;" +
                "-fx-font-weight: bold");

        var content = new Label(contentStr);
        content.setStyle("" +
                "-fx-font-size: 18;");

        var exitButton = new Button("Close");
        exitButton.setStyle("" +
                "-fx-min-width: 200;" +
                "-fx-min-height: 50;" +
                "-fx-font-size: 18");
        exitButton.setOnAction(e -> Main.setScene(nextScene));

        var spacer = new Region();
        var spacer1 = new Region();

        VBox.setVgrow(spacer, Priority.ALWAYS);
        VBox.setVgrow(spacer1, Priority.ALWAYS);


        root.getChildren().add(playLabel);
        root.getChildren().add(spacer);
        root.getChildren().add(content);
        root.getChildren().add(spacer1);
        root.getChildren().add(exitButton);

    }
}
