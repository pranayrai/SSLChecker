import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/*
This class reads the specified file containing the lists of websites and returns it in an ArrayList
 */
public class ReadFile {

    public ArrayList returnList(String fileName) throws FileNotFoundException {
        ArrayList<String> websites = new ArrayList();
        try {

            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNext()) {
                String website = scanner.next();
                if(website.substring(website.length()-1).equals("/"))
                {
                    website = website.substring(0, website.length()-1);
                }
                websites.add(website);
            }
        }
        catch (Exception e){
            System.out.println("File not found");

        }

        return websites;
    }

}