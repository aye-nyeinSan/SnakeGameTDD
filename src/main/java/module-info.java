module com.chapter5.snakegametdd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.chapter5.snakegametdd to javafx.fxml;
    exports com.chapter5.snakegametdd;
}