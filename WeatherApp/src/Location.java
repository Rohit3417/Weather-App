package WeatherApp.src;

public class Location {
    float latitude, longitude;

    float temperature, apparentTemperature, isDay, rain, precipitation, relative_humidity;

    public String toString() {
        return " Temperature: " + temperature + "°C\n" +
                " Feels Like: " + apparentTemperature + "°C\n" +
                " Is Day: " + isDay + "\n" +
                " Rain: " + rain + " mm\n" +
                " Precipitation: " + precipitation + " mm\n" +
                " Humidity: " + relative_humidity + "%";
    }

    public int hashCode() {
        String strtemperature = String.valueOf(temperature);
        String strapptemp = String.valueOf(apparentTemperature);
        String strisDay = String.valueOf(isDay);
        String strrain = String.valueOf(rain);
        String strprecip = String.valueOf(precipitation);
        String strrel = String.valueOf(relative_humidity);

        int hashtemp = strtemperature.hashCode();
        int hashappTemp = strapptemp.hashCode();
        int hashisDay = strisDay.hashCode();
        int hashrain = strrain.hashCode();
        int hashprecip = strprecip.hashCode();
        int hashrel = strrel.hashCode();

        return hashappTemp + hashisDay + hashprecip + hashrain + hashrel + hashtemp;

    }

    // @Override
    // public String toString() {
    // // return String.format("| %8s | %5s |", latitude, longitude);
    // return String.valueOf(latitude) + " " + String.valueOf(longitude);
    // }

    // public int hashCode() {
    // String strlatitude = String.valueOf(latitude);
    // String strlongitude = String.valueOf(longitude);

    // int hashLatitude = strlatitude.hashCode();
    // int hashLongitude = strlongitude.hashCode();

    // return hashLatitude + hashLongitude;
    // }
}
