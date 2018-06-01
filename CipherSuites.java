import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/*
This class returns an Array String of all the supported Cipher Suites of the Url
*/

public class CipherSuites {
    public String[] getCipherSuites(String url) throws IOException {

        String[] CS=null;

        try {


            url = url.replaceFirst("(http[s]?://www\\\\.|http[s]?://|www\\\\.)", "");
            if (url.charAt(url.length() - 1) == '/') {
                url = url.substring(0, url.length() - 1);
            }
            SSLSocket sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(url, 80);
            CS = sslSocket.getEnabledCipherSuites();
        }
        catch (Exception e){
            CS = null;
        }

        return CS;
    }

}
