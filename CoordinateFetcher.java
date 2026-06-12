package WeatherApp.src;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoordinateFetcher {
    @SuppressWarnings("deprecation")
    static WeatherRecord getLocation(String city) {
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city
                + "&count=1&language=en&format=json";

        WeatherRecord info = new WeatherRecord();

        try {

            URL url = new URL(urlString); // Java just creates an url object from the given string
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); // Just prepares the protocols and is
                                                                              // ready to send request / call the api
            con.setRequestMethod("GET"); // To fetch data
                                         // Parameter goes into the url fo e.g: link + ?city = surat
                                         // In links the spaces are just replaced by '+'
            if (con.getResponseCode() != 200) {
                System.out.println("Error");
                return null;
            }
            // InputStream receives the data in the form of stream of bytes
            // InputStreamReader can be used to convert the bytes to characters
            // But instead we use BufferedReader which efficient and uses buffer
            // Server → (bytes) → InputStream → InputStreamReader → BufferedReader → Your
            // Code
            // this is when data is not coming in the form of json
            // When json obejct is sent as data we need to parse using Gson or jackson

            InputStream is = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer responseBuffer = new StringBuffer("");

            String line;
            while ((line = br.readLine()) != null) {
                responseBuffer.append(line);
            }
            // System.out.println(temp);
            // As json format is array type of result thus we need JSONarray
            String jsonString = responseBuffer.toString();
            JSONObject obj = new JSONObject(jsonString);
            JSONArray arr = obj.getJSONArray("results");
            // Loop through each result
            for (int i = 0; i < arr.length(); i++) {
                JSONObject specific = arr.getJSONObject(i);

                info.latitude = specific.getFloat("latitude");
                info.longitude = specific.getFloat("longitude");

            }
            // System.out.println("Latitude : " + info.latitude + " Longitude : " +
            // info.longitude);
            return info;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
