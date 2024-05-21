package course.course.controller;


import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainController {
    @FXML
    private ListView<String> bookingListView;

    @FXML
    public void initialize() {
        // ініціалізація компонентів
    }
}

// Аналогічно для інших контролерів (BookingController, ClientController, ScheduleController)
