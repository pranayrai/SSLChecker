import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/*
This class reads the specified file containing the lists of websites and returns it in an ArrayList
 */
public class ReadReputationCheckFile {

    public ArrayList returnList(String fileName) throws FileNotFoundException {
        ArrayList<String> maliciousWebsites = new ArrayList();
        try {

            Scanner scanner = new Scanner(new File(fileName));
            String[] nextLine;
            while (scanner.hasNext()) {
                nextLine = scanner.nextLine().split(",");

                String website = nextLine[1];

                maliciousWebsites.add(website);

            }
        }
        catch (Exception e){
            System.out.println("File not found");

        }
        maliciousWebsites.remove(maliciousWebsites.get(0));

        return maliciousWebsites;
    }

}
