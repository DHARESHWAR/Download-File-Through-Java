import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ganeshdhareshwar on 17/09/15.
 */
public class HttpDownload {
    public String fileURL = "http://portal.aauj.edu/e_books/teach_your_self_java_in_21_days.pdf";
    public String saveDir = "/Users/ganeshdhareshwar/Documents/java_code_downloaded";
    private static final int BUFFER_SIZE = 4096;
   public static String fileName = "";

    public  void downloadFile()
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String disposition = httpConn.getHeaderField("Content-Disposition");
            if (disposition != null) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
            }
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Response Code : " + responseCode);
        }
        httpConn.disconnect();
    }

    public static void main(String[] args) {
        HttpDownload download = new HttpDownload();
        try {
            download.downloadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
