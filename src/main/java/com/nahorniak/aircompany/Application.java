package com.nahorniak.aircompany;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class starts our javaFX application using methods:
 * <p>{@link  com.nahorniak.aircompany.Application#start}</p>
 * <p>{@link  com.nahorniak.aircompany.Application#launch(String...)}</p>
 *
 * @author  {@code Oleh Nahorniak}
 * @version {@code 1.0}
 */
public class Application extends javafx.application.Application {

    /**
     * Method {@code start} creates and sets new scene with different params and styles
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 575);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("AirCompany");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method launch application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}