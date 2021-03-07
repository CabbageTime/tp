package seedu.hdbuy.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import seedu.hdbuy.data.Unit;
import seedu.hdbuy.data.exception.EmptyResponseException;
import seedu.hdbuy.data.exception.GatewayException;

public class GetRequest {

    private static final String URL =
        "https://data.gov.sg/api/action/datastore_search" +
        "?resource_id=42ff9cfe-abe5-4b54-beda-c88f9bb438ee&q=";
    private static final String REQUEST_PROPERTY =
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko)" +
        " Chrome/23.0.1271.95 Safari/537.11";
    private static final String REQUEST_PROPERTY_HEADER = "User-Agent";
    private static final String REQUEST_METHOD = "GET";

    /**
     * Connects to database and sends a GET request to get units matching query.
     * @param query Combination of user's filter criteria.
     * @return Units decoded from response body.
     * @throws EmptyResponseException When there are no units matching user's filter criteria.
     */
    public static HashMap<Integer, Unit> getResponse(String query) throws EmptyResponseException {
        try {
            URL url = new URL(URL + query);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty(REQUEST_PROPERTY_HEADER, REQUEST_PROPERTY);
            httpURLConnection.setRequestMethod(REQUEST_METHOD);
            httpURLConnection.connect();
            return ResponseDecoder.decodeResponse(httpURLConnection.getInputStream());
        } catch (GatewayException | IOException exception) {
            System.out.println(exception.getMessage());
            return new HashMap<Integer, Unit>();
        }
    }
}
