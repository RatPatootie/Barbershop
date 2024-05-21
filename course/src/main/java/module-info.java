module course.course {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens course.course to javafx.fxml;
    exports course.course;
    exports course.course.controller;
    opens course.course.controller to javafx.fxml;
}