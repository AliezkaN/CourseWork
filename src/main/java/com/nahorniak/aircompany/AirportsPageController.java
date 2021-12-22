package com.nahorniak.aircompany;
import com.nahorniak.aircompany.entities.Airport;
import com.nahorniak.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
/**
 * {@code PlanesPageController} class is responsible for application's planes page functionality.
 * There are methods to solve some problems:
 * <ul>
 *   <li>
 *       <p>{@link #initialize(URL, ResourceBundle)} - initializing <b>Airports</b> scene
 *   </li>
 *   <li>
 *       <p>{@link #getAirportsData()} - gets all <b>Airports</b> from database </p>
 *   </li>
 *   <li>
 *       <p>{@link #initData()} ()} - fills table with data in application </p>
 *   </li>
 *   <li>
 *       <p>{@link #addAirports()} - adds <b>Airport</b> to database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #editAirports()}  - edits data of <b>Airport</b> in database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #removeAirport()} - removes <b>Airport</b> from database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #getSelected(MouseEvent)} - when user clicks on row of application table text fields fill with data</p>
 *   </li>
 *   </ul>
 *
 *   <p>Other Controllers:</p>
 * <ul>
 * <li>
 *     {@link LoginController}
 * </li>
 * <li>
 *     {@link PlanesPageController}
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
public class AirportsPageController implements Initializable {

    /**
     * JavaFx table with <b>Airports</b> data
     */
    @FXML
    private TableView<Airport> table_airports;
    @FXML
    private TableColumn<Airport,String > col_city;
    @FXML
    private TableColumn<Airport, String> col_country;
    @FXML
    private TableColumn<Airport, Integer> col_id;

    /**
     * Text fields to input data about <b>Airports</b>
     */
    @FXML
    private TextField txt_city;
    @FXML
    private TextField txt_country;
    @FXML
    private TextField txt_id;

    /**
     * Labels to inform user with errors
     */
    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabel1;


    private DatabaseConnection connectNow = new DatabaseConnection();
    private Connection connectDB = connectNow.getConnection();
    private PreparedStatement preparedStatement = null;
    private ObservableList<Airport> listM;
    private int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }

    private void initData(){
        col_id.setCellValueFactory(new PropertyValueFactory<Airport,Integer>("id"));
        col_country.setCellValueFactory(new PropertyValueFactory<Airport,String>("country"));
        col_city.setCellValueFactory(new PropertyValueFactory<Airport,String>("city"));
        listM = getAirportsData();
        table_airports.setItems(listM);
    }

    public void editAirports(){
        String value = txt_id.getText();
        String value1 = txt_country.getText();
        String value2 = txt_city.getText();
        if(!value.isBlank() && !value1.isBlank() && !value2.isBlank() ){
            try {
                String sql = "update airports " +
                             "set id= '" +value + "'," +
                             "country= '" + value1 +"'," +
                             "city= '" + value2 + "'" +
                             "where id='"+value+"'";
                preparedStatement = connectDB.prepareStatement(sql);
                preparedStatement.execute();
                messageLabel.setText("Information updated");
                messageLabel1.setText("");
                initData();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            messageLabel1.setText("Please input data");
            messageLabel.setText("");
        }
    }

    public void removeAirport(){
        String value = txt_id.getText();
        if(!value.isBlank()){
            String selection = "select count(*) from airports where id = " + value;
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(selection);
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                            String sql = "delete from airports where id = " + value;
                            preparedStatement = connectDB.prepareStatement(sql);
                            preparedStatement.execute();
                            messageLabel.setText("Information removed");
                            messageLabel1.setText("");
                            initData();
                    } else {
                        messageLabel1.setText("Incorrect id");
                        messageLabel.setText("");
                    }
                }
            }catch (java.sql.SQLIntegrityConstraintViolationException e){
                messageLabel1.setText("can't remove active airport");
                messageLabel.setText("");
            } catch (Exception e){
                    e.printStackTrace();
                    e.getCause();
            }
        }
        else {
            messageLabel1.setText("Your id is blank");
            messageLabel.setText("");
        }

    }

    public void addAirports(){
        String sql = "insert into airports (country,city) values (?,?)";
        if(!txt_country.getText().isBlank() && !txt_city.getText().isBlank()){
            try {
                preparedStatement = connectDB.prepareStatement(sql);
                preparedStatement.setString(1,txt_country.getText());
                preparedStatement.setString(2,txt_city.getText());
                preparedStatement.execute();
                messageLabel.setText("Airport successfully added");
                messageLabel1.setText("");
                initData();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else {
            messageLabel1.setText("Your fields are blank");
            messageLabel.setText("");
        }
    }

    private ObservableList<Airport> getAirportsData(){

        ObservableList<Airport> list = FXCollections.observableArrayList();
        try {
            preparedStatement = connectDB.prepareStatement("select * from airports");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                list.add(new Airport(Integer.parseInt(rs.getString("id")),
                                    rs.getString("country"),
                                    rs.getString("city")));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return list;
    }

    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = table_airports.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        txt_id.setText(col_id.getCellData(index).toString());
        txt_country.setText(col_country.getCellData(index));
        txt_city.setText(col_city.getCellData(index));
    }
}
