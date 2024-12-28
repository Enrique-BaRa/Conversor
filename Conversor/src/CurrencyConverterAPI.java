import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverterAPI {

    private JsonObject conversionRates;

    public boolean ConversionActual(String baseCurrency) {
        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/daf828b7145f4d1b64da1995/latest/" + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: Código de respuesta HTTP " + responseCode);
                return false;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            this.conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getConversionRate(String currency) {
        if (this.conversionRates != null && this.conversionRates.has(currency)) {
            return this.conversionRates.get(currency).getAsDouble();
        } else {
            return -1;
        }
    }
}
