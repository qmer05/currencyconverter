package app.currencyconverter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Main extends Application {

    Stage window;
    Scene scene;
    Button convertButton;
    Button exitButton;
    TextField amountToConvert;
    TextField convertedAmount;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        window = primaryStage;
        window.setTitle("Currency Converter");

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Label labelFromCurrency = new Label("From currency:");
        Label labelToCurrency = new Label("To currency:");

        ChoiceBox<String> convertFrom = new ChoiceBox<>();
        convertFrom.getItems().addAll("USD", "EUR", "GBP");

        ChoiceBox<String> convertTo = new ChoiceBox<>();
        convertTo.getItems().addAll("USD", "EUR", "GBP");

        Map<String, Function<Double, Double>> conversionMap = new HashMap<>();
        conversionMap.put("USD_EUR", CurrencyConverter::USDtoEUR);
        conversionMap.put("EUR_USD", CurrencyConverter::EURtoUSD);
        conversionMap.put("EUR_GBP", CurrencyConverter::EURtoGBP);
        conversionMap.put("GBP_EUR", CurrencyConverter::GBPtoEUR);
        conversionMap.put("GBP_USD", CurrencyConverter::GBPtoUSD);
        conversionMap.put("USD_GBP", CurrencyConverter::USDtoGBP);

        convertButton = new Button();
        convertButton.setText("Convert");
        convertButton.setOnAction(e -> {

            String selectedFromCurrency = convertFrom.getSelectionModel().getSelectedItem();
            String selectedToCurrency = convertTo.getSelectionModel().getSelectedItem();
            try {
                double amount = Double.parseDouble(amountToConvert.getText());

                String key = selectedFromCurrency + "_" + selectedToCurrency;

                if (conversionMap.containsKey(key)) {
                    double converted = conversionMap.get(key).apply(amount);
                    convertedAmount.setText(String.valueOf(converted));
                } else {
                    convertedAmount.setText(String.valueOf(amount));
                }
            } catch (NumberFormatException ex) {
                convertedAmount.setText("Conversion not supported");
            }
        });

        exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(e -> closeProgram());

        amountToConvert = new TextField();
        convertedAmount = new TextField();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelFromCurrency, convertFrom, amountToConvert, labelToCurrency, convertTo, convertedAmount, convertButton, exitButton);

        scene = new Scene(layout, 450, 350);
        window.setScene(scene);
        window.show();
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Title", "Are you sure you want to exit?");
        if (answer) {
            window.close();
        }
    }
}