package com.nahorniak.aircompany;

import com.nahorniak.database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * {@code LoginController} class is responsible for Authorization, admin logins to start working with air company .
 * There are methods to solve some problems:
 * <ul>
 *   <li>
 *       <p>{@link #loginButtonOnAction(ActionEvent)} - when user clicks on login button  - {@code validateLogin} method will be called </p>
 *   </li>
 *   <li>
 *       <p>{@link #canselButtonOnAction(ActionEvent)} - when user clicks on cansel button application ends</p>
 *   </li>
 *   <li>
 *       <p>{@link #validateLogin()} - inputted data compares to database data</p>
 *   </li>
 *   <li>
 *       <p>{@link #createHomePage()}  - destroy login page and creates a new one - home page</p>
 *   </li>
 *   </ul>
 *   <p>Other Controllers:</p>
 * <ul>
 * <li>
 *     {@link PlanesPageController}
 * </li>
 * <li>
 *     {@link AirportsPageController}
 * </li>
 * <li>
 *     {@link HomePageController}
 * </li>
 * <li>
 *     {@link FlightsPageController}
 * </li>
 * </ul>
 * @author     {@code Oleh Nahorniak}
 * @version    {@code 1.0}
 */

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private Button canselButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loginMessageLabel;

    public void loginButtonOnAction(ActionEvent event){

        if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            validateLogin();
        }else {
            loginMessageLabel.setText("Please enter Username and Password");
        }
    }

    public void canselButtonOnAction(ActionEvent event){
        Stage stage = (Stage) canselButton.getScene().getWindow();
        stage.close();
    }

    private void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT  count(1) from user_account where userName = '" + usernameTextField.getText() +
                "' and password = '" + passwordTextField.getText() +"'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if(queryResult.getInt(1) == 1 ){
                  createHomePage();
                }else{
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createHomePage(){
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home.fxml"));
            Stage homeStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 1070, 575);
            homeStage.initStyle(StageStyle.UNDECORATED);
            homeStage.setTitle("AirCompany");
            homeStage.setScene(scene);
            homeStage.show();
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
}
