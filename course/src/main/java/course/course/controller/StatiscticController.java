package course.course.controller;

import course.course.db.connection.AuthUser;
import course.course.db.connection.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static course.course.db.connection.Connect.connection;

public class StatiscticController {

    public BarChart barChart;
    @FXML
    public void initialize() throws SQLException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Кількість бронювань");
        UserDAO dao = new UserDAO(connection);
        ResultSet data= dao.sellectBooking();
        while(data.next()){
            series.getData().add(new XYChart.Data<>(data.getString("date"), data.getInt("enum")));
        }
        // Додавання даних до серії
        //series.getData().add(new XYChart.Data<>("Категорія 1", 23));
        //series.getData().add(new XYChart.Data<>("Категорія 2", 14));
        //series.getData().add(new XYChart.Data<>("Категорія 3", 15));
        //series.getData().add(new XYChart.Data<>("Категорія 4", 24));
        //series.getData().add(new XYChart.Data<>("Категорія 5", 34));

        // Додавання серії до BarChart
        barChart.getData().add(series);

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
}
