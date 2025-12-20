module com.example.fittracker { // Your module name might be slightly different
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    // --- ADD THESE LINES ---
    // This allows FXML to see your Main class
    opens com to javafx.fxml;

    // This allows FXML to see your Controllers (FIXES YOUR ERROR)
    opens com.controller to javafx.fxml;

    // Export your main package so the app can start
    exports com;
}