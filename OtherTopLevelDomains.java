import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OtherTopLevelDomains {

    public ArrayList<String> findOtherDomains(String url, String domains) throws MalformedURLException {
        String[] domain = domains.split(" ");
        URL urlToCheck = new URL(url);
        CheckHttpStatus checkHttpStatus = new CheckHttpStatus();
        ArrayList<String> otherTLDs = new ArrayList<>();


        for(int i=0; i<domain.length;i++ )
        {

            String[] domainNameParts = urlToCheck.getHost().split("\\.");
            if (!domainNameParts[domainNameParts.length - 1].equals(domain[i]))
            {
                String newUrl = urlToCheck.toString().replace(domainNameParts[domainNameParts.length - 1], domain[i]);
                if (checkHttpStatus.getHTTPStatus(newUrl) >= 200 && checkHttpStatus.getHTTPStatus(newUrl) <= 399)
                {
                    otherTLDs.add(domain[i]);
                }
                else
                    continue;
            }
            else
                continue;
        }

        return otherTLDs;
    }

}
