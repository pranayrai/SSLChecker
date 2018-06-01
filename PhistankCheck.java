import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
/*
This class checks for trust worthiness using the Phistank online API

Find the xml format from 'https://checkurl.phishtank.com/checkurl/index.php?url=www.sitename.com' .
 */

public class PhistankCheck {

    public String PhisCheck(String url) throws IOException {
        String result = "";
        URL phistankUrl = new URL("https://checkurl.phishtank.com/checkurl/index.php?url=".concat(url));
        URLConnection connection = phistankUrl.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        String output = "";
        while ((line = br.readLine()) != null) {
            if(line.contains("<in_database>")) {
                output=line.substring((line.indexOf("e")+2), (line.indexOf("/")-1));
            }
        }
        if(output.equals("false"))
            result = "YES";
        else if(output.equals("true"))
            result="NO";
        else
            result="NA";

        return result;
    }
}
