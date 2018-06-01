import javax.net.ssl.HttpsURLConnection;
import java.net.URL;


/*
This class is used to find the response codes of https websites
 */

public class CheckHttpsStatus {
    public Integer getHTTPSStatus(String URL)
    {
        int result = 0;

        try{
            java.net.URL url = new URL(URL);
            HttpsURLConnection connection;

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            result = code;
        }
        catch (Exception e){
            result = 500;
        }
        return result;
    }
}
