import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    
    

//These methods are from lecture in order to print out the HTML contents of a page
    
    public static ArrayList<String> getURLContent(String urlString) {
        URLGetter url = new URLGetter(urlString);
        ArrayList<String> l = url.getContents();
        return l;
    }
    
    public static void getHTML(String urlName) {
        try {
            URLConnection url = new URL(urlName).openConnection();
            Scanner scanner = new Scanner(url.getInputStream());
            while (scanner.hasNext()) {
                String curr = scanner.next();
                System.out.println(curr);
            }
            
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + urlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
}
