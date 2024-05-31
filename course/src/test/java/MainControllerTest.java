//package course.course.controller;

import course.course.controller.MainController;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerTest extends ApplicationTest {

    private MainController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new MainController();
        controller.initialize();
        stage.show();
    }

    @Test
    public void testUserLabelInitialization() {
        // Перевірка, чи правильно встановлено привітання для AuthUser.user
        assertEquals("Вітаю TestUser", controller.getUserLabel().getText());
    }
}
