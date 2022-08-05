package in.momin5.projectRAT.request;

import java.io.File;

public interface Request {

    void init() throws Exception;
    String getMessage();
    //File getFile(); Im retarded and forgot we need to send more than 1 file per request :bruh: - momin5
    File[] getFiles();

}
