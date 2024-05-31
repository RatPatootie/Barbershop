package course.course.controller;

import course.course.Main;
import course.course.db.connection.AuthUser;
import course.course.db.connection.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static course.course.db.connection.Connect.connection;

public class CancelController {
    public FlowPane flowContainer;
    private static final Logger logger = LogManager.getLogger(Main.class);


    @FXML
    public void initialize()throws SQLException {
        VBox vbox = new VBox();
        UserDAO dao= new UserDAO(connection);
       ResultSet booking= dao.sellectBookingInfo(AuthUser.time,AuthUser.date);
       while(booking.next()){
           Label customerNameLabel = new Label("Імя користувача: " + booking.getString("customer_name"));
           Label customerPhoneLabel = new Label("Телефон користувача: " + booking.getString("customer_phone"));
           Label serviceLabel = new Label("Послуга: " + booking.getString("service"));
           Label employeeLabel = new Label("Фахівець: " + booking.getString("employee"));
           Label servicePriceLabel = new Label("Ціна: " + booking.getString("price"));
           Button deleteButton = new Button("видалити");
           deleteButton.setOnAction(event -> {
               try {

                   logger.info("User {} delete booking", AuthUser.user);
                   dao.deleteZones(AuthUser.time);
                   dao.deleteBooking(AuthUser.time,AuthUser.date);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
           });
       vbox.getChildren().addAll(customerNameLabel, customerPhoneLabel, serviceLabel, employeeLabel, servicePriceLabel, deleteButton);

       }
       flowContainer.getChildren().add(vbox);

    }
    public void logout(ActionEvent event) throws IOException {
        AuthUser.user=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/show-booking.fxml"));
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
}
