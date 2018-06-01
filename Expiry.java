import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

/*
This class looks for all the certificate chains and calculates 'how far' is the earliest expiry date.
*/

public class Expiry {

    public Long returnExpiry(URL url, HttpsURLConnection connection) throws SSLPeerUnverifiedException
    {
        Certificate[] certs = connection.getServerCertificates();
        ArrayList<Long> differences = new ArrayList();
        for(Certificate cert: certs)
        {
            if(cert instanceof X509Certificate)
            {
                X509Certificate sslCommon = (X509Certificate) cert;
                //System.out.println("Expires: " + sslCommon.getNotAfter());
                differences.add((sslCommon.getNotAfter().getTime() - System.currentTimeMillis())/(1000*24*60*60));
            }
        }
        Collections.sort(differences);

        return differences.get(0);
    }
}
