import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/*
This class looks for all the certificate chains and returns the earliest expiry date
*/

public class ExpiryDate {

    public String returnExpiryDate(URL url, HttpsURLConnection connection) throws SSLPeerUnverifiedException

    {
        String result = "";
        try {
            Certificate[] certs = connection.getServerCertificates();
            ArrayList<Date> dates = new ArrayList<Date>();
            for (Certificate cert : certs) {
                if (cert instanceof X509Certificate) {
                    X509Certificate sslCommon = (X509Certificate) cert;
                    dates.add(sslCommon.getNotAfter());
                }
            }
            Collections.sort(dates);
            result = dates.get(0).toString();
        }
        catch (Exception e){
            result = "null";
        }
        return result;
    }
}
