package com.jdenner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Juliano
 */
public class Aplicacao extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/jdenner/view/Menu.fxml"))));
        stage.setTitle("Sistema");
        stage.setMaximized(true);
        stage.show();
    }

}
