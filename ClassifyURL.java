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
            if(websites.get(i).startsWith("http"))
            {

                if(http.getHTTPStatus(websites.get(i))<200 || http.getHTTPStatus(websites.get(i))>399)
                {
                    invalidUrls.add(websites.get(i));

                }
                else if (websites.get(i).substring(0, websites.get(i).indexOf(":")).equals("http"))
                {
                    httpUrls.add(websites.get(i));
                    if(http.getHTTPStatus(websites.get(i))>=300 && http.getHTTPStatus(websites.get(i))<=399)
                    {
                        URL url = new URL(websites.get(i));
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.connect();
                        if(con.getHeaderField("Location").startsWith("https"))
                        {
                            httpsUrls.add(con.getHeaderField("Location"));
                        }
                        else
                            httpUrls.add(url.toString());
                    }

                }
                else if(https.getHTTPSStatus(websites.get(i))<200 || https.getHTTPSStatus(websites.get(i))>399)
                {
                    invalidUrls.add(websites.get(i));
                }
                else if (websites.get(i).substring(0, websites.get(i).indexOf(":")).equals("https")  )
                {
                    httpsUrls.add(websites.get(i));

                    if(https.getHTTPSStatus(websites.get(i))>=300 && http.getHTTPStatus(websites.get(i))<=399)
                    {
                        URL url = new URL(websites.get(i));
                        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                        con.connect();
                        if(con.getHeaderField("Location").startsWith("https"))
                        {
                            httpsUrls.add(con.getHeaderField("Location"));
                        }
                        else
                            httpUrls.add(url.toString());
                    }
                }
                else
                    continue;
            }

            else if(websites.get(i).startsWith("www"))
            {
                String checker = new String(websites.get(i));

                if(https.getHTTPSStatus("https://" + checker)<200 || https.getHTTPSStatus("https://" + checker) >399 )
                {

                    invalidUrls.add(checker);
                }

                else if(https.getHTTPSStatus("https://" + checker)==200)
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
                    else
                        httpUrls.add(con.getHeaderField("Location"));
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
                    if(con.getHeaderField("Location").startsWith("https"))                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        httpUrls.add(con.getHeaderField("Location"));
                }
                else
                    invalidUrls.add(checker);
            }
            else
            {
                String checker = new String(websites.get(i));
                if(https.getHTTPSStatus("https://" + checker)<200 || https.getHTTPSStatus("https://" + checker) >399 )
                {
                    invalidUrls.add(checker);
                }

                else if(https.getHTTPSStatus("https://" + checker)==200)
                {
                    httpsUrls.add("https://" + checker);
                    continue;
                }
                else if(https.getHTTPSStatus("https://" + checker)>=300 && https.getHTTPSStatus("https://" + checker)<=399)
                {

                    URL url = new URL("https://" + checker);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.connect();
                    if(con.getHeaderField("Location").startsWith("https"))
                    {
                        httpsUrls.add(con.getHeaderField("Location"));
                    }
                    else
                        httpUrls.add(con.getHeaderField("Location"));
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
                    else
                        httpUrls.add(con.getHeaderField("Location"));
                }
                else
                    invalidUrls.add(checker);
            }
        }


    }
}
