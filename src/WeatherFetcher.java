package WeatherApp.src;

import java.io.BufferedReader;
//import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// import java.util.Map;
// import java.util.function.Consumer;

import org.json.JSONObject;

public class WeatherFetcher {

    @SuppressWarnings("deprecation")
    static WeatherRecord getWeather(WeatherRecord info) {
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + info.latitude + "&longitude="
                + info.longitude
                + "&current=temperature_2m,relative_humidity_2m,precipitation,rain,is_day,apparent_temperature&timezone=auto&forecast_days=1";

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            if (con.getResponseCode() != 200) {
                System.out.println("Error occuered while calling the API");
                return null;
            }

            InputStream is = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer responseBuffer = new StringBuffer("");
            String line;
            while ((line = br.readLine()) != null) {
                responseBuffer.append(line);
            }
            String jsonString = responseBuffer.toString();
            // System.out.println(temp);
            JSONObject obj = new JSONObject(jsonString);

            WeatherRecord newInfo = new WeatherRecord();

            JSONObject newobj = obj.getJSONObject("current");
            newInfo.temperature = newobj.getFloat("temperature_2m");
            newInfo.relative_humidity = newobj.getFloat("relative_humidity_2m");
            newInfo.precipitation = newobj.getFloat("precipitation");
            newInfo.rain = newobj.getFloat("rain");
            newInfo.isDay = newobj.getFloat("is_day");
            newInfo.apparentTemperature = newobj.getFloat("apparent_temperature");

            // Using map to reduce code
            // Map<String, Consumer<Float>> field = Map.of(
            // "temperature_2m", v -> newInfo.temperature = v,
            // "relative_humidity_2m", v -> newInfo.relative_humidity = v,
            // "precipitation", v -> newInfo.precipitation = v,
            // "rain", v -> newInfo.rain = v,
            // "is_day", v -> newInfo.isDay = v,
            // "apparent_temperature", v -> newInfo.apparentTemperature = v);

            // field.forEach((key, setter) -> {
            // if (newobj.has(key))
            // setter.accept(newobj.getFloat(key));
            // });
            // Map<String, String> fieldLabels = Map.of(
            // "temperature_2m", "Temperature ( *C )",
            // "relative_humidity_2m", "Relative Humidity ( % )",
            // "precipitation", "Precipitation ( mm )",
            // "rain", "Rain ( mm )",
            // "is_day", "Is Day",
            // "apparent_temperature", "Apparent Temperature ( *C )");

            // fieldLabels.forEach((jsonKey, label) -> {
            // if (newobj.has(jsonKey)) {
            // System.out.println(label + " : " + newobj.getFloat(jsonKey));
            // }
            // });

            return newInfo;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            System.out.println("Program ended");
        }
    }
}
