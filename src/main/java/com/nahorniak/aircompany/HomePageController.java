package com.nahorniak.aircompany;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * {@code HomePageController} class is responsible for application's home page functionality.
 * There are methods to choose mode to work with:
 * <ul>
 *   <li>
 *       <p>{@link #flightsButtonOnAction(ActionEvent)} - to work with <b>Flights</b></p>
 *   </li>
 *   <li>
 *       <p>{@link #airportButtonOnAction(ActionEvent)} - to work with <b>Airports</b></p>
 *   </li>
 *   <li>
 *       <p>{@link #planesButtonOnAction(ActionEvent)} - to work with <b>Planes</b></p>
 *   </li>
 *   <li>
 *       <p>{@link #exitButtonOnAction(ActionEvent)} - to exit the program</p>
 *   </li>
 *   <li>
 *       <p>{@link #setNode(Node)} - to set a scene properly to work without creating a new window</p>
 *   </li>
 *   </ul>
 *
 *   <p>Other Controllers:</p>
 * <ul>
 * <li>
 *     {@link LoginController}
 * </li>
 * <li>
 *     {@link AirportsPageController}
 * </li>
 * <li>
 *     {@link PlanesPageController}
 * </li>
 * <li>
 *     {@link FlightsPageController}
 * </li>
 * </ul>
 * @author     {@code Oleh Nahorniak}
 * @version    {@code 1.0}
 */
public class HomePageController {

    /**
     * JavaFX items
     */
    @FXML
    private AnchorPane holderPane;
    @FXML
    private AnchorPane childPane;
    @FXML
    private Button exitButton;

    /**
     * {@code setNode} method is responsible for switching home's node with a new one
     * @param node
     */
    private void setNode(Node node){
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    /**
     * {@code planesButtonOnAction} method switches holder pane with child pane which includes menu to work with <b>Planes</b>
     * @param event when user clicks on <b>Planes</b> button
     */
    public  void planesButtonOnAction(ActionEvent event){
        try {
            childPane = FXMLLoader.load(getClass().getResource("planes.fxml"));
            setNode(childPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code flightsButtonOnAction} method switches holder pane with child pane which includes menu to work with <b>Flights</b>
     * @param event when user clicks on <b>Flights</b> button
     */
    public  void flightsButtonOnAction(ActionEvent event){
        try {
            childPane = FXMLLoader.load(getClass().getResource("flights.fxml"));
            setNode(childPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code airportButtonOnAction} method switches holder pane with child pane which includes menu to work with <b>Airports</b>
     * @param event when user clicks on <b>Airports</b> button
     */
    public void airportButtonOnAction(ActionEvent event){
        try {
            childPane = FXMLLoader.load(getClass().getResource("airports.fxml"));
            setNode(childPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code exitButtonOnAction} method destroys window and ends application >
     * @param event when user clicks on <b>Exit</b> button
     */
    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
