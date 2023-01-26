module com.company.Chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.company.Chat.Client to javafx.fxml;
    exports com.company.Chat.Client;
}