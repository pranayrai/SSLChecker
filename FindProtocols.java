import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/*
This class returns all the supported Security Protocols of the url.
It creates a connection with each version, and then queries for the protocol used.
If the response it NONE, that means it doesn't support that version.

 */

public class FindProtocols {

    public ArrayList getSupportedProtocols(URL url, HttpsURLConnection connection) throws IOException {

        ArrayList protocols = new ArrayList();

        try {

            String urlToCheck =url.toString();
            if(url.toString().substring(url.toString().length()-1).equals("/"))
            {
                urlToCheck = url.toString().substring(0,url.toString().length()-2);
            }


            SSLSocket ss = (SSLSocket) SSLSocketFactory.getDefault().createSocket(urlToCheck.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""), 443);
            ss.setEnabledProtocols(new String[]{"TLSv1.2"});

            if ((ss.getSession().getProtocol().equals("TLSv1.2"))) {
                protocols.add("TLSv1.2");
            }

            SSLSocket ss1 = (SSLSocket) SSLSocketFactory.getDefault().createSocket(urlToCheck.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""), 443);


            ss1.setEnabledProtocols(new String[]{"TLSv1.1"});
            if ((ss1.getSession().getProtocol().equals("TLSv1.1"))) {
                protocols.add("TLSv1.1");
            }

            SSLSocket ss2 = (SSLSocket) SSLSocketFactory.getDefault().createSocket(urlToCheck.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""), 443);
            ss2.setEnabledProtocols(new String[]{"TLSv1"});
            if ((ss2.getSession().getProtocol().equals("TLSv1"))) {
                protocols.add("TLSv1");
            }
            SSLSocket ss3 = (SSLSocket) SSLSocketFactory.getDefault().createSocket(urlToCheck.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""), 443);
            ss3.setEnabledProtocols(new String[]{"SSLv3"});
            if ((ss3.getSession().getProtocol().equals("SSLv3"))) {
                protocols.add("SSLv3");
            }

        }
        catch (Exception e)
        {
            protocols = null;
        }

        return protocols;
    }
}
