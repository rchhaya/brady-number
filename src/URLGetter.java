
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that will use HTTP to get the contents of a page.
 * @author swapneel
 *
 */
public class URLGetter {
    
    private URL url;
    private HttpURLConnection httpConnection;
    
    
    
    
    //To test the URLGetter methods
    public static void main(String[] args) {
        
        try {
            URL u = new URL("www.cis.upenn.edu");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
        URLGetter url = 
                new URLGetter("https://en.wikipedia.org/wiki/List_of_endangered_arthropods");
        
        url.printStatusCode(); 
        
        ArrayList<String> page = url.getContents();
        int cnt = 0;
        for (String line : page) {
            if (cnt < 400) {
                System.out.println(line);
            }
            cnt++;
        }
        */
        
    }
    
    public URLGetter(String url) {
        try {
            this.url = new URL(url);
            
            URLConnection connection = this.url.openConnection();
            httpConnection = (HttpURLConnection) connection;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method will print the status codes from the connection.
     */
    public void printStatusCode() {
        try {
            int code = httpConnection.getResponseCode();
            String message = httpConnection.getResponseMessage();
            
            if (code != HttpURLConnection.HTTP_OK) {
                String redirectUrl = httpConnection.getHeaderField("Location");
                System.out.println("new url: " + redirectUrl);
            }
            
            System.out.println(code + " : " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The method will return the contents of the page
     * @return the arraylist of strings for each line on the page
     */
    public ArrayList<String> getContents() {
        ArrayList<String> contents = new ArrayList<String>();
        
        try {
            Scanner in = new Scanner(httpConnection.getInputStream());
            
            while (in.hasNextLine()) {
                String line = in.nextLine();
                contents.add(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return contents;
        
    }

}

