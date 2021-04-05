package net.thumbtack.school.database.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*",
        "com.jayway.jsonpath.*", "Lcom.jayway.jsonpath.*", "jdk.xml.*", "java.xml.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({CityService.class, JdbcUtils.class})
public class CityServiceWithMockTest {
    //тестовые данные
    private String[] cities = {"Paris", "London", "Kiev"};
    private InputStream stream1 = new ByteArrayInputStream(
            ("[{\"name\":\"France\",\"topLevelDomain\":[\".fr\"],\"alpha2Code\":\"FR\",\"alpha3Code\":\"FRA\"," +
                    "\"callingCodes\":[\"33\"],\"capital\":\"Paris\",\"altSpellings\":[\"FR\",\"French Republic\"," +
                    "\"République française\"],\"region\":\"Europe\",\"subregion\":\"Western Europe\"," +
                    "\"population\":66710000,\"latlng\":[46.0,2.0],\"demonym\":\"French\",\"area\":640679.0,\"gini\":32.7," +
                    "\"timezones\":[\"UTC-10:00\",\"UTC-09:30\",\"UTC-09:00\",\"UTC-08:00\",\"UTC-04:00\",\"UTC-03:00\"," +
                    "\"UTC+01:00\",\"UTC+03:00\",\"UTC+04:00\",\"UTC+05:00\",\"UTC+11:00\",\"UTC+12:00\"]," +
                    "\"borders\":[\"AND\",\"BEL\",\"DEU\",\"ITA\",\"LUX\",\"MCO\",\"ESP\",\"CHE\"],\"nativeName\":\"France\"," +
                    "\"numericCode\":\"250\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}]," +
                    "\"languages\":[{\"iso639_1\":\"fr\",\"iso639_2\":\"fra\",\"name\":\"French\",\"nativeName\":\"français\"}]," +
                    "\"translations\":{\"de\":\"Frankreich\",\"es\":\"Francia\",\"fr\":\"France\",\"ja\":\"フランス\"," +
                    "\"it\":\"Francia\",\"br\":\"França\",\"pt\":\"França\",\"nl\":\"Frankrijk\",\"hr\":\"Francuska\"," +
                    "\"fa\":\"فرانسه\"},\"flag\":\"https://restcountries.eu/data/fra.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\"," +
                    "\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"FRA\"}]")
                    .getBytes());
    private InputStream stream2 = new ByteArrayInputStream(
            ("[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"]," +
                    "\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\"," +
                    "\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\"," +
                    "\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0," +
                    "\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\"," +
                    "\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\"," +
                    "\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\"," +
                    "\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}]," +
                    "\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\"," +
                    "\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\"," +
                    "\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"}," +
                    "\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\"," +
                    "\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}]")
                    .getBytes());
    private InputStream stream3 = new ByteArrayInputStream(
            ("[{\"name\":\"Ukraine\",\"topLevelDomain\":[\".ua\"],\"alpha2Code\":\"UA\",\"alpha3Code\":\"UKR\"," +
                    "\"callingCodes\":[\"380\"],\"capital\":\"Kiev\",\"altSpellings\":[\"UA\",\"Ukrayina\"]," +
                    "\"region\":\"Europe\",\"subregion\":\"Eastern Europe\",\"population\":42692393,\"latlng\":[49.0,32.0]," +
                    "\"demonym\":\"Ukrainian\",\"area\":603700.0,\"gini\":26.4,\"timezones\":[\"UTC+02:00\"]," +
                    "\"borders\":[\"BLR\",\"HUN\",\"MDA\",\"POL\",\"ROU\",\"RUS\",\"SVK\"],\"nativeName\":\"Україна\"," +
                    "\"numericCode\":\"804\",\"currencies\":[{\"code\":\"UAH\",\"name\":\"Ukrainian hryvnia\"," +
                    "\"symbol\":\"₴\"}],\"languages\":[{\"iso639_1\":\"uk\",\"iso639_2\":\"ukr\",\"name\":\"Ukrainian\"," +
                    "\"nativeName\":\"Українська\"}],\"translations\":{\"de\":\"Ukraine\",\"es\":\"Ucrania\"," +
                    "\"fr\":\"Ukraine\",\"ja\":\"ウクライナ\",\"it\":\"Ucraina\",\"br\":\"Ucrânia\",\"pt\":\"Ucrânia\"," +
                    "\"nl\":\"Oekraïne\",\"hr\":\"Ukrajina\",\"fa\":\"وکراین\"},\"flag\":\"https://restcountries.eu/data/ukr.svg\"," +
                    "\"regionalBlocs\":[],\"cioc\":\"UKR\"}]")
                    .getBytes());

    private HttpURLConnection spyHttpURLConnection;
    private static final String URLENDPOINT = "http://restcountries.eu/rest/v2/capital/";

    private Connection spyConnection;
    private static final String USER = "test";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://localhost:3306/ttschool?useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Omsk";

    @Test
    public void test() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(2).thenReturn(1).thenReturn(3);
        when(resultSet.getString("name")).thenReturn(cities[0]).thenReturn(cities[1]).thenReturn(cities[2]);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(resultSet);

        Connection jdbcConnection = mock(Connection.class);
        when(jdbcConnection.prepareStatement(anyString())).thenReturn(statement);

        mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConnection()).thenReturn(jdbcConnection);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);


        HttpURLConnection http = mock(HttpURLConnection.class);
        when(http.getContent()).thenReturn(stream1).thenReturn(stream2).thenReturn(stream3);

        URL url = mock(URL.class);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);
        when(url.openConnection()).thenReturn(http);

        CityService cityService = new CityService();
        List<String> infoOnCites = cityService.tryToTestMe();

        Assert.assertEquals("Paris; France; Euro", infoOnCites.get(0));
    }
}