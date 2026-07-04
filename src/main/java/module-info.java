module com.example.librarymanagementsystemgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarymanagementsystemgui to javafx.fxml;
    exports com.example.librarymanagementsystemgui;
}