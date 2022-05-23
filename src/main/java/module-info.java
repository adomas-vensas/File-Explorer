module com.explorer.supercommander {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;
    requires java.desktop;

    opens com.explorer.supercommander to javafx.fxml;
    exports com.explorer.supercommander;
}