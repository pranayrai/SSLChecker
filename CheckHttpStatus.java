import java.net.HttpURLConnection;
import java.net.URL;

/*
This class is used to find the response codes of http websites
 */


public class CheckHttpStatus {
    public Integer getHTTPStatus(String URL)
    {
        int result = 0;

        try{
            URL url = new URL(URL);
            HttpURLConnection connection;

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            result = code;
            //connection.disconnect();
        }
        catch (Exception e){
            result = 500;
        }
        return result;
    }
}
