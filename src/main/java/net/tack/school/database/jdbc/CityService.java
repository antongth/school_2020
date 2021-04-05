package net.tack.school.database.jdbc;

import com.jayway.jsonpath.JsonPath;
import net.tack.school.database.model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityService {
    private Connection connection;
    private URL url;
    private HttpURLConnection httpURLConnection;

    private List<City> citiesFromDB = new ArrayList<>();
    private String jsonRespons;
    private List<String> infoOnCities = new ArrayList<>();
    private static final String BASEURL = "http://restcountries.eu/";
    private static final String ENDPOINT = "rest/v2/capital/";

    public List<String> tryToTestMe() throws SQLException, IOException {
        connection = JdbcUtils.getConnection();
        String query = "SELECT * FROM cites ORDER BY name DESC";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("name");
                citiesFromDB.add(new City(id, firstName));
            }
        }
        for (City city: citiesFromDB) {
            String cityName = city.getName().toLowerCase();
            url = new URL( BASEURL + ENDPOINT + cityName);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            try(InputStreamReader in = new InputStreamReader((InputStream) httpURLConnection.getContent());
                BufferedReader buff = new BufferedReader(in)) {
                jsonRespons = buff.readLine();
            }
            String currencies = JsonPath.read(jsonRespons, "$[0].currencies[0].name");
            String country = JsonPath.read(jsonRespons, "$[0]['name']");
            city.setCountry(country);
            city.setCurrencies(currencies);
            infoOnCities.add(city.getName() + "; " +city.getCountry() + "; " +city.getCurrencies());
        }
        return infoOnCities;
    }
}
