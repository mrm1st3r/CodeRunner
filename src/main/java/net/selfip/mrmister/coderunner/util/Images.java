package net.selfip.mrmister.coderunner.util;

import net.selfip.mrmister.coderunner.CodeRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utility class for loading images.
 */
public final class Images {

    private static final String IMAGE_PATH = "/";

    private Images() throws InstantiationException {
        throw new InstantiationException("You shall not construct");
    }

    public static BufferedImage loadImage(String fileName) throws IOException {
        File file = new File(CodeRunner.class.getResource(IMAGE_PATH + fileName).getFile());
        return ImageIO.read(file);
    }

    /**
     * load an image.
     * @param path image location
     * @param num number of single images (side by side)
     * @return array of all images
     */
    public static BufferedImage[] loadAnimation(String path, int num) throws IOException {
        BufferedImage[] anim = new BufferedImage[num];
        BufferedImage src = loadImage(path);
        for (int i = 0; i < num; i++) {
            anim[i] = src.getSubimage(
                    i * src.getWidth() / num,
                    0,
                    src.getWidth() / num,
                    src.getHeight());
        }

        return anim;
    }
}
