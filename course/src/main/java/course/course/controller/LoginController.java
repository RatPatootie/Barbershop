package course.course.controller;


import course.course.Main;
import course.course.db.connection.AuthUser;
import course.course.db.connection.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static course.course.db.connection.Connect.connection;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static final Logger logger = LogManager.getLogger(Main.class);

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Логін або пароль не можуть бути порожніми.");
            return;
        }

        if (authenticate(username, password)) {
            logger.info("User {} logged in", username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/main.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

        } else {
            showAlert("Невірний логін або пароль.");
        }
    }

    private boolean authenticate(String username, String password) {

        try {
            UserDAO userDAO = new UserDAO(connection);
            ResultSet answer = userDAO.findUser(username);

            if (answer.next()) {
                AuthUser.user = answer.getString("login_name");
                return answer.getString("password").equals(password);
            } else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
