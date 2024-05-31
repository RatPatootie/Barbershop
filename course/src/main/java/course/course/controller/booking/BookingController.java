package course.course.controller.booking;

import course.course.db.connection.AuthUser;
import course.course.db.connection.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static course.course.db.connection.Connect.connection;

public class BookingController {

    public AnchorPane downBox;
    public VBox sideBar;
    @FXML
    private Label label;
    @FXML
    private BorderPane labelBox;
    private Button activeButtonb;
    private Button activeButtons;
    private Button activeButtond;

    public void logout(ActionEvent event) throws IOException {
        AuthUser.user = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void openBookingEmp() throws IOException, SQLException {
        UserDAO userDAO = new UserDAO(connection);
        ResultSet answer;
        if (AuthUser.service != null) {
            answer = userDAO.findBarber(AuthUser.service);
        } else {
            answer = userDAO.findBarber();
        }
        VBox labelsContainer = new VBox(); // Контейнер для Label і ImageView
        while (answer.next()) {
            String firstName = answer.getString("first_name");
            int barberId = answer.getInt("id");
            Button button = new Button(firstName);
            //button.setStyle("-fx-font-size: 14px;");

            button.setOnAction(event -> {
                if (activeButtonb != null) {
                    activeButtonb.setStyle(""); // Повертаємо стиль попередньої активної кнопки
                }

                //System.out.println(barberId);
                AuthUser.barber = barberId;
                button.setStyle("-fx-background-color: #5555aa");
                if (activeButtonb == button) {
                    button.setStyle(""); // Повернення стилю за замовчуванням
                    AuthUser.barber = null; // Скидання значення ідентифікатора перукаря
                }
                activeButtonb = button;
                if (AuthUser.service != null && AuthUser.time != null && AuthUser.barber != null&& AuthUser.date != null) {
                    Button sidebutton = new Button("Продовжити бронювання");
                    sidebutton.setOnAction(event3 -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/booking/booking-user.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene ;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setScene(scene);
                        stage.show();
                    });
                    sideBar.getChildren().add(sidebutton);
                } else {
                    if (sideBar.getChildren().size() > 3) {
                        sideBar.getChildren().remove(sideBar.getChildren().size() - 1);
                    }
                }
            });

            if (AuthUser.barber != null && AuthUser.barber == barberId) {
                button.setStyle("-fx-background-color: #5555aa");
                activeButtonb = button;
            }

            labelsContainer.getChildren().add(button);
            labelsContainer.setSpacing(10.0);

        }
        labelBox.setCenter(labelsContainer);
    }


    public void openBookingService() throws IOException, SQLException {
        labelBox.setCenter(null);
        UserDAO userDAO = new UserDAO(connection);
        ResultSet answer;
        if (AuthUser.barber != null) {
            answer = userDAO.findService(AuthUser.barber);
        } else {
            answer = userDAO.findService();
        }
        VBox labelsContainer = new VBox();
        while (answer.next()) {
            String firstName = answer.getString("service_name");
            int serviceId = answer.getInt("id");
            Button button = new Button(firstName);
            //button.setStyle("-fx-font-size: 14px;");

            button.setOnAction(event -> {
                if (activeButtons != null) {
                    activeButtons.setStyle(""); // Повертаємо стиль попередньої активної кнопки
                }
                //System.out.println(serviceId);
                AuthUser.service = serviceId;
                button.setStyle("-fx-background-color: #5555aa");
                if (activeButtons == button) {
                    button.setStyle(""); // Повернення стилю за замовчуванням
                    AuthUser.service = null; // Скидання значення ідентифікатора перукаря
                }
                activeButtons = button;
                if (AuthUser.service != null && AuthUser.time != null && AuthUser.barber != null && AuthUser.date != null) {
                    Button sidebutton = new Button("Продовжити бронювання");
                    sidebutton.setOnAction(event3 -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/booking/booking-user.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene ;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setScene(scene);
                        stage.show();
                    });
                    sideBar.getChildren().add(sidebutton);
                } else {
                    if (sideBar.getChildren().size() > 3) {
                        sideBar.getChildren().remove(sideBar.getChildren().size() - 1);
                    }
                }
            });

            if (AuthUser.service != null && AuthUser.service == serviceId) {
                button.setStyle("-fx-background-color: #5555aa");
                activeButtons = button;
            }

            labelsContainer.getChildren().add(button);
            labelsContainer.setSpacing(10.0);

        }
        labelBox.setCenter(labelsContainer);
    }

    public void openBookingDate(ActionEvent event) throws IOException, SQLException {
        labelBox.setCenter(null);

        UserDAO userDAO = new UserDAO(connection);
        Label label = new Label("Виберіть дату:");
        DatePicker datePicker = new DatePicker();
        Button button = new Button("Знайти вільні місця");
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        hBox2.setPrefWidth(480);
        HBox labelContainer = new HBox();

        FlowPane flowPane = new FlowPane();
        hBox.getChildren().add(label);
        hBox.getChildren().add(datePicker);
        hBox.getChildren().add(button);

        button.setOnAction(event1 -> {
            labelContainer.getChildren().clear();
            flowPane.getChildren().clear();
            hBox2.getChildren().clear();
            flowPane.setAlignment(Pos.TOP_LEFT); // Встановлення початкового положення елементів вверхньому лівому куті
            flowPane.setHgap(10); // Горизонтальний відступ між елементами
            flowPane.setVgap(10);
            flowPane.getChildren().add(hBox);
            flowPane.getChildren().add(hBox2);

            LocalDate selectedDate = datePicker.getValue();
            AuthUser.date= String.valueOf(selectedDate);
            ResultSet used;
            ResultSet all;
            Set<Integer> usedZones = new HashSet<>();
            try {
                used = UserDAO.findUsedZones(String.valueOf(selectedDate));
                all = UserDAO.findZones();
                while (used.next()) {
                    Integer zoneId = used.getInt("zone_id");
                    usedZones.add(zoneId);
                }
                while (all.next()) {
                    if (!usedZones.contains(all.getInt("id"))) {
                        //System.out.println(all.getString("start_time"));
                        String startTime = all.getString("start_time");
                        String time = startTime.substring(0, startTime.length() - 3);
                        Button buttont = new Button(time);
                        buttont.setUserData(all.getInt("id"));
                        buttont.setOnAction(event2 -> {
                            if (activeButtond != null) {
                                activeButtond.setStyle(""); // Повертаємо стиль попередньої активної кнопки
                            }

                            AuthUser.time = buttont.getUserData().toString();
                            AuthUser.date= String.valueOf(selectedDate);
                            buttont.setStyle("-fx-background-color: #5555aa");
                            if (activeButtond == buttont) {
                                buttont.setStyle(""); // Повернення стилю за замовчуванням
                                AuthUser.time = null;
                                AuthUser.date=null;// Скидання значення ідентифікатора перукаря
                            }
                            activeButtond = buttont;
                            if (AuthUser.service != null && AuthUser.time != null && AuthUser.barber != null && AuthUser.date != null&&sideBar.getChildren().size() < 4) {
                                Button sidebutton = new Button("Продовжити бронювання");
                                sidebutton.setOnAction(event3 -> {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/course/booking/booking-user.fxml"));
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    Scene scene ;
                                    try {
                                        scene = new Scene(loader.load());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    stage.setScene(scene);
                                    stage.show();
                                });
                                sideBar.getChildren().add(sidebutton);
                            } else {
                                if (sideBar.getChildren().size() > 3) {
                                    sideBar.getChildren().remove(sideBar.getChildren().size() - 1);
                                }
                            }
                        });
                        flowPane.getChildren().add(buttont);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            labelBox.setCenter(flowPane);
        });
        hBox.setSpacing(10.0);
        labelBox.setCenter(hBox);


    }
}

