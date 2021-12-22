package com.nahorniak.aircompany;

import com.nahorniak.aircompany.entities.Plane;
import com.nahorniak.database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
 *       <p>{@link #initialize(URL, ResourceBundle)} - initializing planes scene
 *   </li>
 *   <li>
 *       <p>{@link #getPlanesData()} - gets all <b>Planes</b> from database </p>
 *   </li>
 *   <li>
 *       <p>{@link #initData()} ()} - fills table with data in application </p>
 *   </li>
 *   <li>
 *       <p>{@link #addPlanes()} - adds <b>Plane</b> to database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #editPlanes()}  - edits data of <b>Plane</b> in database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #removePlane()} - removes <b>Plane</b> from database and application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #getSelected(MouseEvent)} - when user clicks on row of application table text fields fill with data</p>
 *   </li>
 *   <li>
 *       <p>{@link #getValues()} - gets all values from text fields</p>
 *   </li>
 *   <li>
 *       <p>{@link #filterPlanes(ActionEvent)} - filter <b>Planes</b> and show data in application table</p>
 *   </li>
 *   <li>
 *       <p>{@link #showPlanes(ActionEvent)} - show all data about <b>Planes</b></p>
 *   </li>
 *   <li>
 *       <p>{@link #totalSeatsOnAction()} - shows counted total seats</p>
 *   </li>
 *   <li>
 *       <p>{@link #getTotalLoadCapacity()} - shows counted total load capacity</p>
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
 *     {@link HomePageController}
 * </li>
 * <li>
 *     {@link FlightsPageController}
 * </li>
 * </ul>
 * @author     {@code Oleh Nahorniak}
 * @version    {@code 1.0}
 */
public class PlanesPageController implements Initializable {
    /**
     * JavaFX table to fill with <b>Planes</b> data
     */
    @FXML
    private TableColumn<Plane, Integer> col_crew_capacity;
    @FXML
    private TableColumn<Plane, Double> col_fuel_capacity;
    @FXML
    private TableColumn<Plane, Double> col_fuel_usage;
    @FXML
    private TableColumn<Plane, Integer> col_id;
    @FXML
    private TableColumn<Plane, Double> col_load_capacity;
    @FXML
    private TableColumn<Plane, Double> col_max_range;
    @FXML
    private TableColumn<Plane, String> col_name;
    @FXML
    private TableColumn<Plane, Integer> col_total_seats;
    @FXML
    private TableColumn<Plane, String> col_type;
    @FXML
    private TableView<Plane> planes_table;

    /**
     * Text fields to input data
     */
    @FXML
    private TextField txt_crewCapacity;
    @FXML
    private TextField txt_fuelCapacity;
    @FXML
    private TextField txt_fuelUsage;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_loadCapacity;
    @FXML
    private TextField txt_maxRange;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_totalSeats;
    @FXML
    private TextField txt_type;
    @FXML
    private TextField boundEnd;
    @FXML
    private TextField boundStart;

    /**
     * Labels to show error messages and  total values
     */
    @FXML
    private Label messageLabel;
    @FXML
    private Label messageLabel1;
    @FXML
    private Label messageLabel2;
    @FXML
    private Label messageLabel3;
    @FXML
    private Label messageLabel4;
    @FXML
    private Label tlcLabel;
    @FXML
    private Label tsLabel;


    private final DatabaseConnection connectNow = new DatabaseConnection();
    private final Connection connectDB = connectNow.getConnection();
    private PreparedStatement preparedStatement = null;
    private ObservableList<Plane> listM;
    private String choice = "";
    private String value ="",value1="",value2="",value3="",
            value4="", value5="",value6="",value7="";
    private int index = -1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    initData();
    getTotalSeats();
    getTotalLoadCapacity();
    }


    public void addPlanes(){
        String sql = "insert into planes (name,type,fuel_capacity,fuel_usage,total_seats," +
                                         "max_range,load_capacity,crew_capacity) values (?,?,?,?,?,?,?,?)";
        getValues();
        if(!value.isBlank() && !value1.isBlank()&&
                !value2.isBlank() && !value3.isBlank()&&
                !value4.isBlank() && !value5.isBlank()&&
                !value6.isBlank() && !value7.isBlank()){
            try {
                preparedStatement = connectDB.prepareStatement(sql);
                preparedStatement.setString(1,value);
                preparedStatement.setString(2,value1);
                preparedStatement.setString(3,value2);
                preparedStatement.setString(4,value3);
                preparedStatement.setString(5,value4);
                preparedStatement.setString(6,value5);
                preparedStatement.setString(7,value6);
                preparedStatement.setString(8,value7);
                preparedStatement.execute();
                messageLabel.setText("Plane successfully added");
                messageLabel1.setText("");
                messageLabel4.setText("");
                initData();
                getTotalSeats();
                getTotalLoadCapacity();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
        else {
            messageLabel1.setText("Your fields are blank");
            messageLabel.setText("");
            messageLabel4.setText("");
        }
    }

    private void getValues(){
         value = txt_name.getText();
         value1 = txt_type.getText();
         value2 = txt_fuelCapacity.getText();
         value3 = txt_fuelUsage.getText();
         value4 = txt_totalSeats.getText();
         value5 = txt_maxRange.getText();
         value6 = txt_loadCapacity.getText();
         value7 = txt_crewCapacity.getText();
    }

    public void editPlanes(){
        String value8 = txt_id.getText();
        getValues();
        if(!value.isBlank() && !value1.isBlank()&&
                !value2.isBlank() && !value3.isBlank()&&
                !value4.isBlank() && !value5.isBlank()&&
                !value6.isBlank() && !value7.isBlank() && !value8.isBlank()){
            try {
                String sql = "update planes " +
                        "set id= '" +value8 + "'," +
                        "name= '" + value +"'," +
                        "type = '" + value1 + "'," +
                        "fuel_capacity = '" + value2 + "'," +
                        "fuel_usage = '" + value3 + "'," +
                        "total_seats = '" + value4 + "'," +
                        "max_range = '" + value5 + "'," +
                        "load_capacity = '" + value6 + "'," +
                        "crew_capacity = '" + value7 + "'" +
                        "where id='"+value8+"'";
                preparedStatement = connectDB.prepareStatement(sql);
                preparedStatement.execute();
                messageLabel.setText("Information updated");
                messageLabel1.setText("");
                messageLabel4.setText("");
                initData();
                getTotalSeats();
                getTotalLoadCapacity();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }else{
            messageLabel1.setText("Please input data");
            messageLabel.setText("");
            messageLabel4.setText("");
        }
    }

    public void removePlane() {
        String value = txt_id.getText();
        if (!value.isBlank()) {
            String selection = "select count(*) from planes where id = " + value;
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(selection);
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                            String sql = "delete from planes where id = " + value;
                            preparedStatement = connectDB.prepareStatement(sql);
                            preparedStatement.execute();
                            messageLabel.setText("Information removed");
                            messageLabel4.setText("");
                            messageLabel1.setText("");
                            initData();
                            getTotalLoadCapacity();
                            getTotalSeats();
                    } else {
                        messageLabel1.setText("Incorrect id");
                        messageLabel.setText("");
                        messageLabel4.setText("");
                    }
                }
            } catch (SQLIntegrityConstraintViolationException e){
                messageLabel1.setText("Can't remove");
                messageLabel4.setText("active plane");
                messageLabel.setText("");
            }catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }else {
            messageLabel1.setText("Please input data");
            messageLabel.setText("");
            messageLabel4.setText("");
        }
    }


    @FXML
    void filterPlanes(ActionEvent event) {
        String startBound = boundStart.getText();
        String endBound = boundEnd.getText();
        try{
            if(!startBound.isBlank() && !endBound.isBlank()
                    && Integer.parseInt(startBound) >= 0 && Integer.parseInt(endBound) >0
                    && Integer.parseInt(endBound) > Integer.parseInt(startBound) && !choice.isBlank()) {
                listM = getFilteredData();
                planes_table.setItems(listM);
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery("select sum(total_seats),sum(load_capacity) from planes where "
                        + choice + " between "+Integer.parseInt(boundStart.getText()) + " and " + Integer.parseInt(boundEnd.getText()));
                while (queryResult.next())
                {
                    tsLabel.setText(Integer.toString(queryResult.getInt(1)));
                    tlcLabel.setText(Double.toString(queryResult.getDouble(2)));
                }
                messageLabel3.setText("");
                messageLabel1.setText("");
                messageLabel2.setText("Information filtered");
                messageLabel.setText("");
                messageLabel4.setText("");
            }else {
                messageLabel3.setText("Incorrect input");
                messageLabel1.setText("");
                messageLabel2.setText("");
                messageLabel.setText("");
                messageLabel4.setText("");
            }
        }catch (Exception e){
        }
    }

    @FXML
    void showPlanes(ActionEvent event) {
    initData();
    getTotalSeats();
    getTotalLoadCapacity();
        messageLabel3.setText("");
        messageLabel1.setText("");
        messageLabel2.setText("");
        messageLabel.setText("");
        messageLabel4.setText("");
    }

    private ObservableList<Plane> getPlanesData(){

        javafx.collections.ObservableList<Plane> list = FXCollections.observableArrayList();
        try {
            preparedStatement = connectDB.prepareStatement("select * from planes");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                list.add(new Plane(Integer.parseInt(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("type"),
                        Double.parseDouble(rs.getString("fuel_capacity")),
                        Double.parseDouble(rs.getString("fuel_usage")),
                        Integer.parseInt(rs.getString("total_seats")),
                        Double.parseDouble(rs.getString("max_range")),
                        Double.parseDouble(rs.getString("load_capacity")),
                        Integer.parseInt(rs.getString("crew_capacity"))
                        ));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return list;
    }

    private ObservableList<Plane> getFilteredData(){
        ObservableList<Plane> list = FXCollections.observableArrayList();
        try {
            preparedStatement = connectDB.prepareStatement("select * from planes where "+ choice +
                    " between "+Integer.parseInt(boundStart.getText()) + " and " + Integer.parseInt(boundEnd.getText()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                list.add(new Plane(Integer.parseInt(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("type"),
                        Double.parseDouble(rs.getString("fuel_capacity")),
                        Double.parseDouble(rs.getString("fuel_usage")),
                        Integer.parseInt(rs.getString("total_seats")),
                        Double.parseDouble(rs.getString("max_range")),
                        Double.parseDouble(rs.getString("load_capacity")),
                        Integer.parseInt(rs.getString("crew_capacity"))
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return list;
    }

    private void initData(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_fuel_capacity.setCellValueFactory(new PropertyValueFactory<>("fuelCapacity"));
        col_fuel_usage.setCellValueFactory(new PropertyValueFactory<>("fuelUsage"));
        col_total_seats.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        col_max_range.setCellValueFactory(new PropertyValueFactory<>("maxRange"));
        col_load_capacity.setCellValueFactory(new PropertyValueFactory<>("loadCapacity"));
        col_crew_capacity.setCellValueFactory(new PropertyValueFactory<>("crewCapacity"));

        listM = getPlanesData();
        planes_table.setItems(listM);
    }

    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = planes_table.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index));
        txt_type.setText(col_type.getCellData(index));
        txt_fuelCapacity.setText(col_fuel_capacity.getCellData(index).toString());
        txt_fuelUsage.setText(col_fuel_usage.getCellData(index).toString());
        txt_totalSeats.setText(col_total_seats.getCellData(index).toString());
        txt_maxRange.setText(col_max_range.getCellData(index).toString());
        txt_loadCapacity.setText(col_load_capacity.getCellData(index).toString());
        txt_crewCapacity.setText(col_crew_capacity.getCellData(index).toString());
    }

    private void getTotalSeats(){
        String selection = "select sum(total_seats) from planes";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(selection);
            while (queryResult.next())
            {
                Integer totalSeats = queryResult.getInt(1);
                tsLabel.setText(totalSeats.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    private void getTotalLoadCapacity(){
        String selection = "select sum(load_capacity) from planes";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(selection);
            while (queryResult.next())
            {
                Double totalLoadCapacity = queryResult.getDouble(1);
                tlcLabel.setText(totalLoadCapacity.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void fuelCapacityOnAction(){
        choice = "fuel_capacity";
    }
    public void fuelUsageOnAction(){
        choice = "fuel_usage";
    }
    public void totalSeatsOnAction(){
        choice = "total_seats";
    }
    public void maxRangeOnAction(){
        choice = "max_range";
    }
    public void loadCapacityOnAction(){
        choice = "load_capacity";
    }
    public void crewCapacityOnAction(){
        choice = "crew_capacity";
    }
}
