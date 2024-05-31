package course.course.controller;


import course.course.db.connection.AuthUser;
import course.course.db.connection.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static course.course.db.connection.Connect.connection;

public class MainController {
    public FlowPane flowContainer;
    public BarChart barChart;
    public DatePicker datePicker;
    @FXML
    private Label UserLabel;
    private Button activeButtond;
    @FXML
    public void initialize() {
       if(UserLabel !=null) UserLabel.setText("Вітаю "+ AuthUser.user);

    }
    public Label getUserLabel() {
        return UserLabel;
    }
    public void logout(ActionEvent event) throws IOException {
        AuthUser.user=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void openAddReservation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/booking/booking.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void openViewReservations(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/show-booking.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void openStatistics(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/statistic.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }


    public void showbutton(ActionEvent event) throws SQLException {
        flowContainer.getChildren().clear();
        UserDAO userDAO = new UserDAO(connection);
        LocalDate selectedDate = datePicker.getValue();
        ResultSet used;
        ResultSet all;
        Set<Integer> usedZones = new HashSet<>();
        used = UserDAO.findUsedZones(String.valueOf(selectedDate));
        all = UserDAO.findZones();
        while (used.next()) {
            Integer zoneId = used.getInt("zone_id");
            usedZones.add(zoneId);
        }
        while (all.next()) {
            if (!usedZones.contains(all.getInt("id"))) {
                String startTime = all.getString("start_time");
                String time = startTime.substring(0, startTime.length() - 3);
                Button buttont = new Button(time);
                //System.out.println(buttont+"free");
                buttont.setUserData(all.getInt("id"));
                buttont.setStyle("");
                flowContainer.getChildren().add(buttont);

            }
            else{
                String startTime = all.getString("start_time");
                String time = startTime.substring(0, startTime.length() - 3);
                Button buttont = new Button(time);
                //System.out.println(buttont+"booked");
                buttont.setUserData(all.getInt("id"));
                buttont.setStyle("-fx-background-color: #5555aa");
                buttont.setOnAction(event1 -> {
                    AuthUser.time= buttont.getUserData().toString();
                    AuthUser.date=String.valueOf(selectedDate);

                    System.out.println(AuthUser.time);
                    System.out.println(AuthUser.date);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/booking-cancel.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = null;
                    try {
                        scene = new Scene(loader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.show();

                });
                flowContainer.getChildren().add(buttont);
            }
        }
    }


}

