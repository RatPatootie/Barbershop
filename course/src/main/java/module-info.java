module course.course {
    requires org.apache.logging.log4j;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    exports course.course.db.connection;
    opens course.course.db.connection to org.mockito;

    opens course.course to javafx.fxml;
    exports course.course;
    exports course.course.controller;
    opens course.course.controller to javafx.fxml;
    exports course.course.controller.booking;
    opens course.course.controller.booking to javafx.fxml;
}