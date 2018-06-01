import java.util.ArrayList;


/*
This class is used for creating the URL object

*/


public class URLInfo {

    public String name = "";
    public String status = "";
    public String secure = "NA";
    public String validity = "NA";
    public Long expiry = null;
    public ArrayList supportedProtocols = null;
    public String phistank = "";
    public String expiryDate = "";
    public String[] supportedCipherSuites = {};

    public URLInfo(String name, String status, String secure, String validity, String phistank, String expiryDate, Long expiry, ArrayList supportedProtocols, String[] supportedCipherSuites) {
        this.name = name;
        this.status = status;
        this.secure = secure;
        this.validity = validity;
        this.expiry = expiry;
        this.supportedProtocols = supportedProtocols;
        this.phistank = phistank;
        this.expiryDate = expiryDate;
        this.supportedCipherSuites = supportedCipherSuites;
    }
}