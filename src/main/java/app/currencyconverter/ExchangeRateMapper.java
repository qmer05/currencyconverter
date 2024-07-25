package app.currencyconverter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public class ExchangeRateMapper {

    private final String url;
    private final String currencyNames;
    private final String exchangeRates;

    public ExchangeRateMapper(String url, String currencyNames, String exchangeRates){
        this.url = url;
        this.currencyNames = currencyNames;
        this.exchangeRates = exchangeRates;
    }

    public LinkedHashMap<String, Double> extractExchangeRate() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String html = response.body().string();
                Document document = Jsoup.parse(html);

                // Select the elements containing the currencies and exchange rates
                Elements currencyName = document.select(currencyNames);
                Elements exchangeRate = document.select(exchangeRates);

                // Ensure both Elements lists are of the same size
                if (currencyName.size() == exchangeRate.size()) {
                    LinkedHashMap<String, Double> exchangeRates = new LinkedHashMap<>();
                    exchangeRates.put("Danske kroner", 100.00);
                    for (int i = 0; i < currencyName.size(); i++) {
                        String currency = currencyName.get(i).text();
                        String rate = exchangeRate.get(i).text();

                        String modifiedRate = rate.replace(",", ".");
                        double modifiedRateResult = Double.parseDouble(modifiedRate);

                        exchangeRates.put(currency, modifiedRateResult);
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
