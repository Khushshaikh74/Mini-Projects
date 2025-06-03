package Lesson_4_DiceGameApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageService {

    public static JLabel loadImage(String filepath){
        BufferedImage image;
        JLabel imageContainer;

        try {
            InputStream inputStream = ImageService.class.getResourceAsStream(filepath);
            image = ImageIO.read(inputStream);
            imageContainer = new JLabel(new ImageIcon(image));
            return imageContainer;
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public static void updateImg(JLabel imgContainer, String filepath){
        BufferedImage image;
        try{
            InputStream inputStream = ImageService.class.getResourceAsStream(filepath);
            image = ImageIO.read(inputStream);
            imgContainer.setIcon(new ImageIcon(image));
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }
}
