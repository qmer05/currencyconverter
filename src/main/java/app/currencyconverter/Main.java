package app.currencyconverter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends Application {

    Stage window;
    Scene scene;
    Button convertButton;
    Button exitButton;
    TextField amountToConvert;
    TextField convertedAmount;
    String selectedFromCurrency;
    String selectedToCurrency;

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

        Label labelFromCurrency = new Label("From currency: ");
        Label labelToCurrency = new Label("To currency: ");
        Label labelAmount = new Label("Amount: ");

        ExchangeRateExtractor exchangeRateExtractor = new ExchangeRateExtractor("https://www.valutakurser.dk", "div.currencyItem_currencyNameContainer__19YHn", "div.currencyItem_actualValueContainer__2xLkB");
        LinkedHashMap<String, Double> exchangeRates = exchangeRateExtractor.extractExchangeRate();

        ImageView flagImageViewFrom = new ImageView();
        flagImageViewFrom.setFitHeight(32);
        flagImageViewFrom.setFitWidth(32);

        ImageView flagImageViewTo = new ImageView();
        flagImageViewTo.setFitHeight(32);
        flagImageViewTo.setFitWidth(32);

        ChoiceBox<String> convertFrom = new ChoiceBox<>();
        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
            String key = entry.getKey();
            convertFrom.getItems().add(key);
        }

        convertFrom.setOnAction(e -> {
            String selectedFromCurrency = convertFrom.getSelectionModel().getSelectedItem();
            if (selectedFromCurrency != null) {
                try {
                    if (selectedFromCurrency.contains("*")) {
                        selectedFromCurrency = selectedFromCurrency.replaceAll("\\*", "").trim();
                    }
                    String imagePath = "/images/" + selectedFromCurrency + ".png";
                    Image flagImage = new Image(getClass().getResourceAsStream(imagePath));
                    flagImageViewFrom.setImage(flagImage);
                } catch (Exception ex) {
                    flagImageViewFrom.setImage(null);
                }
            }
        });

        ChoiceBox<String> convertTo = new ChoiceBox<>();
        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
            String key = entry.getKey();
            convertTo.getItems().add(key);
        }

        convertTo.setOnAction(e -> {
            String selectedToCurrency = convertTo.getSelectionModel().getSelectedItem();
            if (selectedToCurrency != null) {
                try {
                    String imagePath = "/images/" + selectedToCurrency + ".png";
                    Image flagImage = new Image(getClass().getResourceAsStream(imagePath));
                    flagImageViewTo.setImage(flagImage);
                } catch (Exception ex) {
                    flagImageViewTo.setImage(null);
                }
            }
        });

        convertButton = new Button();
        convertButton.setText("Convert");
        convertButton.setOnAction(e -> {
            selectedFromCurrency = convertFrom.getSelectionModel().getSelectedItem();
            selectedToCurrency = convertTo.getSelectionModel().getSelectedItem();

            try {
                double amount = Double.parseDouble(amountToConvert.getText());
                double dkkToFromCurrency = exchangeRates.get(selectedFromCurrency);
                double dkkToToCurrency = exchangeRates.get(selectedToCurrency);
                double converted = (double) Math.round((amount * (dkkToFromCurrency / dkkToToCurrency) * 100)) / 100;
                convertedAmount.setText(String.valueOf(converted));
            } catch (NumberFormatException ex) {
                convertedAmount.setText("Conversion not supported");
            }
        });

        exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(e -> closeProgram());

        amountToConvert = new TextField();
        convertedAmount = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(labelFromCurrency, 0, 0);
        gridPane.add(convertFrom, 1, 0);
        gridPane.add(flagImageViewFrom, 2, 0);
        gridPane.add(labelToCurrency, 0, 1);
        gridPane.add(convertTo, 1, 1);
        gridPane.add(flagImageViewTo, 2, 1);
        gridPane.add(labelAmount, 0, 2);
        gridPane.add(amountToConvert, 1, 2);
        gridPane.add(convertedAmount, 1, 3);
        gridPane.add(convertButton, 1, 4);
        gridPane.add(exitButton, 1, 5);

        scene = new Scene(gridPane, 350, 250);
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