package game.gui;

import game.gui.controllers.MainMenuController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double HEIGHT = 544;
    private static final double WIDTH = 1024;
    private static Stage stage;


    public static void applyWindowSize(Parent root) {
        root.setStyle("-fx-min-height: " + HEIGHT + ";" +
                "-fx-min-width: " + WIDTH + ";");

    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    @Override
    public void start(Stage pstage) throws Exception {
        stage = pstage;
        stage.setTitle("Hawk-Eye Innovations Project");
        setScene(new MainMenuController());
        stage.setResizable(false);
        stage.show();
    }
}
