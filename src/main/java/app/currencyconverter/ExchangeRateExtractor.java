package app.currencyconverter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public class ExchangeRateExtractor {

    public static LinkedHashMap<String, Double> extractExchangeRate() {
        String url = "https://www.valutakurser.dk";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String html = response.body().string();
                Document document = Jsoup.parse(html);

                // Select the elements containing the currency pairs and exchange rates
                Elements currencyPairs = document.select("div.currencyItem_currencyNameContainer__19YHn");
                Elements rateElements = document.select("div.currencyItem_actualValueContainer__2xLkB");

                // Ensure both Elements lists are of the same size
                if (currencyPairs.size() == rateElements.size()) {
                    LinkedHashMap<String, Double> exchangeRates = new LinkedHashMap<>();
                    for (int i = 0; i < currencyPairs.size(); i++) {
                        String currencyPair = currencyPairs.get(i).text();
                        String rate = rateElements.get(i).text();

                        String modifiedRate = rate.replace(",", ".");
                        double modifiedRateResult = Double.parseDouble(modifiedRate);

                        exchangeRates.put(currencyPair, modifiedRateResult);
                    }
                    return exchangeRates;
                } else {
                    System.err.println("Mismatch between currency pairs and exchange rates.");
                }
            } else {
                System.err.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
