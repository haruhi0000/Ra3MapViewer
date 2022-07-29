import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TgaConverter {
    public static void main(String[] args) {
        System.out.println("Starting... Please wait a few minutes!");

        String path = TgaConverter.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        File folder;
        String outFolderName;

        folder = new File(path);
        outFolderName = folder.getAbsolutePath().concat("/output/");

        if (!folder.isDirectory())
            return;

        File outFolder = new File(outFolderName);
        File inputFolder = new File(folder.getAbsolutePath().concat("/input/"));
        if (!outFolder.exists())
            outFolder.mkdir();

        for (File f : inputFolder.listFiles()) {
            try {
                if (!f.getName().endsWith(".tga"))
                    continue;
                System.out.println(f.getName());
                File newImage = new File(outFolderName.concat(f.getName().replaceAll(".tga", ".png")));

                if (!newImage.exists()) {
                    BufferedImage newBufferedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

                    newBufferedImage.createGraphics().drawImage(TargaReader.getImage(f), 0, 0, Color.WHITE, null);


                    newImage.createNewFile();

                    // записываем новое изображение в формате jpg
                    ImageIO.write(newBufferedImage, "png", newImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finish!");


    }
}
