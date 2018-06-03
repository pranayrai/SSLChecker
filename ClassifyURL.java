import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ClassifyURL {


    public void ClassifyURLs(ArrayList<String> httpUrls,ArrayList<String> httpsUrls, ArrayList<String> invalidUrls, ArrayList<String> websites) throws IOException {
        CheckHttpStatus http = new CheckHttpStatus();
        CheckHttpsStatus https = new CheckHttpsStatus();

        for(int i=0; i<websites.size(); i++)                            // Add urls to specific lists
        {
            System.out.println(websites.get(i));
            String checker = websites.get(i);

            if(checker.startsWith("http"))
            {
                if(checker.substring(0, checker.indexOf(":")).equals("https"))
                {
                    if(https.getHTTPSStatus(checker)!=500)
                    {
                        if(https.getHTTPSStatus(checker)==200)
                        {
                            httpsUrls.add(checker);
                            continue;
                        }

                        else if(https.getHTTPSStatus(checker) >= 300 && https.getHTTPSStatus(checker) <= 399)
                        {
                            httpsUrls.add(checker);
                            URL url = new URL(checker);
                            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                            con.connect();
                            if(con.getHeaderField("Location").startsWith("https"))
                            {
                                httpsUrls.add(con.getHeaderField("Location"));
                            }
                            else if(con.getHeaderField("Location").startsWith("http"))
                            {
                                httpUrls.add(con.getHeaderField("Location"));
                            }
                            else
                                invalidUrls.add(con.getHeaderField("Location"));


                        }
                    }
                    else
                        invalidUrls.add(checker);
                }
                else if(checker.substring(0, checker.indexOf(":")).equals("http"))
                {
                    if(http.getHTTPStatus(checker)!=500)
                    {
                        if(http.getHTTPStatus(checker)==200)
                        {
                            httpUrls.add(checker);
                            continue;
                        }

                        else if(http.getHTTPStatus(checker) >= 300 && http.getHTTPStatus(checker) <= 399)
                        {
                            httpUrls.add(checker);
                            URL url = new URL(checker);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.connect();
                            if(con.getHeaderField("Location").startsWith("https"))
                            {
                                httpsUrls.add(con.getHeaderField("Location"));
                            }
                            else if(con.getHeaderField("Location").startsWith("http"))
                            {
                                httpUrls.add(con.getHeaderField("Location"));
                            }
                            else
                                invalidUrls.add(con.getHeaderField("Location"));
                        }
                    }
                    else
                        invalidUrls.add(checker);

                }
            }
            else if(checker.startsWith("www"))
            {
                if(https.getHTTPSStatus("https://" + checker)==200)
                {
                    httpsUrls.add("https://" + checker);
                    continue;
                }
                else if(https.getHTTPSStatus("https://" + checker)>=300 && https.getHTTPSStatus("https://" + checker)<=399)
                {
                    httpsUrls.add("https://" + checker);
                    URL url = new URL("https://" + checker);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.connect();
                    if(con.getHeaderField("Location").startsWith("https"))
                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else if(con.getHeaderField("Location").startsWith("http"))
                    {
                        httpUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        invalidUrls.add(con.getHeaderField("Location"));
                }
                else if(http.getHTTPStatus("http://"+checker)==200)
                {
                    httpUrls.add("http://"+checker);
                }
                else if(http.getHTTPStatus("http://" + checker)>=300 && http.getHTTPStatus("http://" + checker)<=399)
                {
                    httpUrls.add("http://"+checker);
                    URL url = new URL("http://"+checker);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    if(con.getHeaderField("Location").startsWith("https"))
                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else if(con.getHeaderField("Location").startsWith("http"))
                    {
                        httpUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        invalidUrls.add(con.getHeaderField("Location"));
                }
                else
                    invalidUrls.add(checker);
            }

            else
            {


                if(https.getHTTPSStatus("https://www." + checker)==200)
                {

                    httpsUrls.add("https://www."+checker);
                    continue;
                }
                else if(https.getHTTPSStatus("https://" + checker)==200)
                {

                    httpsUrls.add("https://" + checker);
                    continue;
                }
                else if(https.getHTTPSStatus("https://www." + checker)>=300 && https.getHTTPSStatus("https://www." + checker)<=399)
                {
                    httpsUrls.add("https://www." + checker);
                    URL url = new URL("https://www." + checker);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.connect();
                    if(con.getHeaderField("Location").startsWith("https"))
                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else if(con.getHeaderField("Location").startsWith("http"))
                    {
                        httpUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        invalidUrls.add(con.getHeaderField("Location"));
                }
                else if(https.getHTTPSStatus("https://" + checker)>=300 && https.getHTTPSStatus("https://" + checker)<=399)
                {
                    httpsUrls.add("https://" + checker);
                    URL url = new URL("https://" + checker);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.connect();
                    if(con.getHeaderField("Location").startsWith("https"))
                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else if(con.getHeaderField("Location").startsWith("http"))
                    {
                        httpUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        invalidUrls.add(con.getHeaderField("Location"));
                }
                else
                    invalidUrls.add(checker);
            }
        }
    }
}
