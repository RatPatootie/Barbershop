
import course.course.Main;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest extends ApplicationTest {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        new Main().start(stage);
    }

    @Test
    public void testStart() {
        // Check if the primaryStage is showing
        assertTrue(stage.isShowing());

        // Check if the scene is set
        Scene scene = stage.getScene();
        assertNotNull(scene);

        // Check the title of the stage
        String title = stage.getTitle();
        assertNotNull(title);
        assertTrue(title.contains("Booking System"));
    }
}
