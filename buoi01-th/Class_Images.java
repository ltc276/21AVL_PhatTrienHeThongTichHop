package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Class_Images {

    // Doc file anh thanh byte[]
    public static byte[] readFile(File path) {

        try {
            FileInputStream fis = new FileInputStream(path);

            byte[] buf = new byte[1024];

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }

            fis.close();

            return bos.toByteArray();

        } catch (IOException ex) {

            Logger.getLogger(Class_Images.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return null;
    }

    // Luu byte[] thanh file anh
    public static void saveFile(File path, String tfile, byte[] bfile) {

        try {

            BufferedImage img = ImageIO.read(
                    new ByteArrayInputStream(bfile)
            );

            ImageIO.write(img, tfile, path);

        } catch (IOException ex) {

            Logger.getLogger(Class_Images.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}