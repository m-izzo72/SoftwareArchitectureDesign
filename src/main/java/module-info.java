module org.softwarearchitecturedesigngroup10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    //requires eu.hansolo.tilesfx;

    opens org.softwarearchitecturedesigngroup10 to javafx.fxml;
    exports org.softwarearchitecturedesigngroup10;
    exports org.softwarearchitecturedesigngroup10.controller;
    opens org.softwarearchitecturedesigngroup10.controller to javafx.fxml;
}