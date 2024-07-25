module app.currencyconverter {
    requires javafx.controls;
    requires javafx.graphics;
    requires okhttp3;
    requires org.jsoup;

    opens app.currencyconverter to javafx.fxml;
    exports app.currencyconverter;
}