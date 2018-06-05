import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class ApplicationRunner {

    public static void main(String[] args) throws IOException {


        Properties properties = new Properties();
        InputStream inputStream = null;
        inputStream = new FileInputStream("config.properties");
        properties.load(inputStream);

        String fileName = properties.getProperty("filepath");
        String domain = properties.getProperty("otherTopLevelDomainsToCheck");


        ReadFile readFile = new ReadFile();                     // Read File first and get the list of websites
        ArrayList<String> websites = new ArrayList<>();
        websites = readFile.returnList(fileName);
        //websites = readFile.returnList("C:\\Users\\PRRAI\\IdeaProjects\\SSLChecker\\src\\urls.csv");


        ArrayList<String> httpUrls = new ArrayList<String>();       // Classification of urls
        ArrayList<String> httpsUrls = new ArrayList<String>();
        ArrayList<String> invalidUrls = new ArrayList<String>();

        CheckHttpStatus http = new CheckHttpStatus();           // Create Status Checker class
        CheckHttpsStatus https = new CheckHttpsStatus();

        ClassifyURL classifyURL = new ClassifyURL();
        classifyURL.ClassifyURLs(httpUrls, httpsUrls, invalidUrls, websites);   //Classify the websites in 3 categories



        /////// Debugging////////////

        System.out.println("\nHTTPS SITES LIST ");

        for(int k=0;k<httpsUrls.size(); k++)
        {
            System.out.println(httpsUrls.get(k));
        }

        System.out.println("\nHTTP SITES LIST ");
        for(int l=0;l<httpUrls.size(); l++)
        {
            System.out.println(httpUrls.get(l));
        }

        System.out.println("\nINVALID SITES LIST ");
        for(int m=0;m<invalidUrls.size(); m++)
        {
            System.out.println(invalidUrls.get(m));
        }
        System.out.println("\n");


        ////////////////////////////////

        ArrayList<URLInfo> URLObjects = new ArrayList<URLInfo>();           // Global Objects



        for(int y=0; y<httpsUrls.size(); y++)                               // perform the process for https sites
        {
            System.out.println("Reading.....     "  + httpsUrls.get(y).substring(httpsUrls.get(y).indexOf(":")+3));
            URL url = new URL(httpsUrls.get(y));
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();


            Expiry expiry = new Expiry();
            Long expires = expiry.returnExpiry(url, connection);

            String validity = "";
            if(expires>0)
            {
                validity="Valid";
            }
            else
                validity = "Invalid";

            PhistankCheck phistankCheck = new PhistankCheck();
            String phistank = phistankCheck.PhisCheck(url.toString());

            FindProtocols findProtocols = new FindProtocols();
            ArrayList protocols = findProtocols.getSupportedProtocols(url, connection);

            ExpiryDate expiryDate = new ExpiryDate();
            String expiryD = expiryDate.returnExpiryDate(url, connection);

            CipherSuites cipherSuites = new CipherSuites();
            String[] CS = cipherSuites.getCipherSuites(url.toString());

            OtherTopLevelDomains otherTopLevelDomains = new OtherTopLevelDomains();
            ArrayList TLDs = otherTopLevelDomains.findOtherDomains(url.toString(), domain);

            URLInfo urlInfo = new URLInfo(httpsUrls.get(y).substring(httpsUrls.get(y).indexOf(":")+3), "Live", "Yes", validity, phistank, expiryD, expires, TLDs, protocols, CS);
            URLObjects.add(urlInfo);
            System.out.println("Read.........         " + httpsUrls.get(y).substring(httpsUrls.get(y).indexOf(":")+3));
        }

        for(int z=0; z<httpUrls.size();z++)                                 // perform the process for http sites
        {
            System.out.println("Reading....." + httpUrls.get(z).substring(httpUrls.get(z).indexOf(":")+3));
            URL url = new URL(httpUrls.get(z));
            HttpURLConnection connection;

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            PhistankCheck phistankCheck = new PhistankCheck();
            String phistank = phistankCheck.PhisCheck(url.toString());

            OtherTopLevelDomains otherTopLevelDomains = new OtherTopLevelDomains();
            ArrayList TLDs = otherTopLevelDomains.findOtherDomains(url.toString(), domain);

            URLInfo urlInfo = new URLInfo(httpUrls.get(z).substring(httpUrls.get(z).indexOf(":")+3),"Live", "No", "NA", phistank, "NA", null, null, TLDs,null  );
            URLObjects.add(urlInfo);
            System.out.println("Read....." + httpUrls.get(z).substring(httpUrls.get(z).indexOf(":")+3));
        }

        for(int x=0; x<invalidUrls.size();x++)                                  // perform the process for invalid sites
        {
            URLInfo urlInfo = new URLInfo(invalidUrls.get(x), "Invalid URL", "NA", "NA", "NA", "NA", null, null, null,null);
            URLObjects.add(urlInfo);
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-HH-mm-a");

        PrintWriter pw = new PrintWriter(new File(dateFormat.format(date) + "-output.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("name, Site status, HTTPS?, cert date valid?, trustworthy?, expiry date, cert expires in(days),other registered TLDs, supported protocols,  enabled cipher suites\n");

        for(int f=0; f<URLObjects.size(); f++)                      // first create an output containing all the results
        {
            URLInfo urlInfo = URLObjects.get(f);
            sb.append(urlInfo.name+"," + urlInfo.status + "," + urlInfo.secure + "," + urlInfo.validity + "," + urlInfo.phistank +"," + urlInfo.expiryDate + "," + urlInfo.expiry + ",");


            
            if(urlInfo.otherDomains.size() != 0)
            {
                for(int g=0; g<urlInfo.otherDomains.size(); g++)
                {
                    sb.append(urlInfo.otherDomains.get(g) + " ");
                }
            }
            else
                sb.append("None");

            sb.append(",");

            if(urlInfo.supportedProtocols != null)
            {
                for(int g=0; g<urlInfo.supportedProtocols.size(); g++)
                {
                    sb.append(urlInfo.supportedProtocols.get(g) + " ");
                }
            }
            else
                sb.append("NA");


            sb.append(",");


            if(urlInfo.supportedCipherSuites!=null)
            {
                for(int j=0; j<urlInfo.supportedCipherSuites.length; j++)
                {
                    sb.append(urlInfo.supportedCipherSuites[j] + " ");
                }
            }
            else
                sb.append("NA");
            sb.append("\n");
        }

        pw.write(sb.toString());
        pw.close();

        PrintWriter pw1 = new PrintWriter(new File(dateFormat.format(date) + "-VRM_output.csv"));
        StringBuilder sb1 = new StringBuilder();
        sb1.append("name, Site status, HTTPS?, cert date valid?, trustworthy?, expiry date, cert expires in(days), other registered TLDs, supported protocols, enabled cipher suites\n");


        for(int i=0; i<URLObjects.size(); i++)                              // create output containing urls with immediate attention
        {
            URLInfo urlInfo = URLObjects.get(i);
            if(urlInfo.expiry!=null) {
                if (urlInfo.expiry < 90)
                {
                    sb1.append(urlInfo.name + "," + urlInfo.status + "," + urlInfo.secure + "," + urlInfo.validity + "," + urlInfo.phistank + "," + urlInfo.expiryDate + "," + urlInfo.expiry + ",");



                    if(urlInfo.otherDomains.size() != 0)
                    {
                        for(int g=0; g<urlInfo.otherDomains.size(); g++)
                        {
                            sb1.append(urlInfo.otherDomains.get(g) + " ");
                        }
                    }
                    else
                        sb1.append("None");

                    sb.append(",");

                    if (urlInfo.supportedProtocols != null) {
                        for (int g = 0; g < urlInfo.supportedProtocols.size(); g++) {
                            sb1.append(urlInfo.supportedProtocols.get(g) + " ");
                        }
                    } else
                        sb1.append("NA");


                    sb1.append(",");
                    if (urlInfo.supportedCipherSuites != null) {
                        for (int j = 0; j < urlInfo.supportedCipherSuites.length; j++) {
                            sb1.append(urlInfo.supportedCipherSuites[j] + " ");
                        }
                    } else
                        sb1.append("NA");
                    sb1.append("\n");
                }
            }
        }
        pw1.write(sb1.toString());
        pw1.close();

    }

}
