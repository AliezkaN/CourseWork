package com.nahorniak.aircompany;
import com.nahorniak.aircompany.entities.Flight;
import com.nahorniak.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * {@code PlanesPageController} class is responsible for application's planes page functionality.
 * There are methods to solve some problems:
 * <ul>
 *   <li>
 *       <p>{@link #initialize(URL, ResourceBundle)} - initializing <b>Flights</b> scene
 *   </li>
 *   <li>
 *       <p>{@link #getFlightsData()} - gets all <b>Flights</b> from database </p>
 *   </li>
 *   <li>
 *       <p>{@link #initData()}  - fills table with data in application </p>
 *   </li>
 *   <li>
 *       <p>{@link #addFlight(ActionEvent)} - adds <b>Flight</b> to database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #editFlight(ActionEvent)}  - edits data of <b>Flight</b> in database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #removeFlight(ActionEvent)} - removes <b>Flight</b> from database and application table</p>
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
 *     {@link AirportsPageController}
 * </li>
 * </ul>
 * @author     {@code Oleh Nahorniak}
 * @version    {@code 1.0}
 */

public class FlightsPageController implements Initializable {

    @FXML
    private TableColumn<Flight, Integer> col_arrival_airport_id;

    @FXML
    private TableColumn<Flight, String> col_arrival_time;

    @FXML
    private TableColumn<Flight, Integer> col_dep_airport_id;

    @FXML
    private TableColumn<Flight, String> col_departure_time;
    @FXML
    private TableColumn<Flight, Integer> col_id;
    @FXML
    private TableColumn<Flight, Integer> col_planeId;
    @FXML
    private TableColumn<Flight, Double> col_range;
    @FXML
    private TableView<Flight> flightsTable;
    @FXML
    private TextField txt_arrId;
    @FXML
    private TextField txt_arrivalTime;
    @FXML
    private TextField txt_depId;
    @FXML
    private TextField txt_depTime;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_planeId;
    @FXML
    private TextField txt_range;

    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabel1;


    private DatabaseConnection connectNow = new DatabaseConnection();
    private Connection connectDB = connectNow.getConnection();
    private ObservableList<Flight> listM;
    private int index = -1;
    private PreparedStatement preparedStatement = null;
    private String value,value1,value2,value3,value4,value5;

    @FXML
    void addFlight(ActionEvent event) {
        String sql = "insert into flights (dep_airport_id,arrival_airport_id,departure_time,arrival_time,plane_id," +
                     "`range`) values (?,?,?,?,?,?)";
        getValues();
        if(!value.isBlank() && !value1.isBlank()&&
           !value2.isBlank() && !value3.isBlank()&&
           !value4.isBlank() && !value5.isBlank()){
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                format.parse(value2);
                format.parse(value3);
                if(value2.compareTo(value3) <0){
                    try {
                        preparedStatement = connectDB.prepareStatement(sql);
                        preparedStatement.setString(1,value);
                        preparedStatement.setString(2,value1);
                        preparedStatement.setString(3,value2);
                        preparedStatement.setString(4,value3);
                        preparedStatement.setString(5,value4);
                        preparedStatement.setString(6,value5);
                        preparedStatement.execute();
                        messageLabel.setText("Flight successfully added");
                        messageLabel1.setText("");
                        initData();
                    }catch (SQLIntegrityConstraintViolationException e){
                        messageLabel1.setText("You input incorrect airport/plane id");
                        messageLabel.setText("");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        e.getCause();
                    }
                }else{
                    messageLabel1.setText("arrival date equals/less than departure");
                    messageLabel.setText("");
                }
            }catch(ParseException e){
                messageLabel1.setText("Incorrect date format");
                messageLabel.setText("");
            }
        }
        else {
            messageLabel1.setText("Your fields are blank");
            messageLabel.setText("");
        }
    }

    private void getValues(){
        value = txt_depId.getText();
        value1 = txt_arrId.getText();
        value2 = txt_depTime.getText();
        value3 = txt_arrivalTime.getText();
        value4 = txt_planeId.getText();
        value5 = txt_range.getText();
    }

    @FXML
    void editFlight(ActionEvent event) {
        String value6 = txt_id.getText();
        getValues();
        if(!value.isBlank() && !value1.isBlank()&&
                !value2.isBlank() && !value3.isBlank()&&
                !value4.isBlank() && !value5.isBlank()){
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                format.parse(value2);
                format.parse(value3);
                if(value2.compareTo(value3) <0){
                    try {
                        String sql = "update flights " +
                                     "set id= '" +value6 + "'," +
                                     "dep_airport_id= '" + value +"'," +
                                     "arrival_airport_id = '" + value1 + "'," +
                                     "departure_time = '" + value2 + "'," +
                                     "arrival_time = '" + value3 + "'," +
                                     "plane_id = '" + value4 + "'," +
                                     "`range` = '" + value5 + "'" +
                                     "where id='"+value6+"'";
                        preparedStatement = connectDB.prepareStatement(sql);
                        preparedStatement.execute();
                        messageLabel.setText("Information updated");
                        messageLabel1.setText("");
                        initData();
                    }catch (SQLIntegrityConstraintViolationException e){
                        messageLabel1.setText("You input incorrect airport/plane id");
                        messageLabel.setText("");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        e.getCause();
                    }
                }else{
                    messageLabel1.setText("arrival date equals/less than departure");
                    messageLabel.setText("");
                }
            }catch(ParseException e){
                messageLabel1.setText("Incorrect date format");
                messageLabel.setText("");
            }
        }
        else {
            messageLabel1.setText("Your fields are blank");
            messageLabel.setText("");
        }
    }

    @FXML
    void removeFlight(ActionEvent event) {
        String value = txt_id.getText();
        if (!value.isBlank()) {
            String selection = "select count(*) from flights where id = " + value;
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(selection);
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        String sql = "delete from flights where id = " + value;
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
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }else {
            messageLabel1.setText("Please input data");
            messageLabel.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    initData();
    }

    private void initData(){
        col_id.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("id"));
        col_dep_airport_id.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("depAirportId"));
        col_arrival_airport_id.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("arrAirportId"));
        col_departure_time.setCellValueFactory(new PropertyValueFactory<Flight,String>("depTime"));
        col_arrival_time.setCellValueFactory(new PropertyValueFactory<Flight,String>("arrTime"));
        col_planeId.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("planeId"));
        col_range.setCellValueFactory(new PropertyValueFactory<Flight,Double>("range"));

        listM = getFlightsData();
        flightsTable.setItems(listM);
    }

    private ObservableList<Flight> getFlightsData(){

        ObservableList<Flight> list = FXCollections.observableArrayList();
        try {
            preparedStatement = connectDB.prepareStatement("select * from flights");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                list.add(new Flight(Integer.parseInt(rs.getString("id")),
                                    Integer.parseInt(rs.getString("dep_airport_id")),
                                    Integer.parseInt(rs.getString("arrival_airport_id")),
                                    rs.getString("departure_time"),
                                    rs.getString("arrival_time"),
                                    Integer.parseInt(rs.getString("plane_id")),
                                    Double.parseDouble(rs.getString("range"))));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return list;
    }

    @FXML
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = flightsTable.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        txt_id.setText(col_id.getCellData(index).toString());
        txt_depId.setText(col_dep_airport_id.getCellData(index).toString());
        txt_arrId.setText(col_arrival_airport_id.getCellData(index).toString());
        txt_depTime.setText(col_departure_time.getCellData(index));
        txt_arrivalTime.setText(col_arrival_time.getCellData(index));
        txt_planeId.setText(col_planeId.getCellData(index).toString());
        txt_range.setText(col_range.getCellData(index).toString());
    }
}
