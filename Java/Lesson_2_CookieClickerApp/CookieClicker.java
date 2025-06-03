package Lesson_2_CookieClickerApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class CookieClicker extends JFrame {

    JTextField counterFeild;

    public CookieClicker() {
        super("Cookie Clicker Game");
        setPreferredSize(new Dimension(400, 600));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        addGUIComponent();
    }

    private void addGUIComponent() {
        SpringLayout springLayout = new SpringLayout();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(springLayout);

        // 1. Banner
        JLabel bannerImg = loadImage("resources/banner.jpeg");
        jPanel.add(bannerImg);

        // Set constraints for the banner
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, bannerImg, 0, SpringLayout.HORIZONTAL_CENTER, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, bannerImg, 10, SpringLayout.NORTH, jPanel);

        // 2. Cookie image
        JButton cookieBtn = loadCookieImg("resources/cookie.jpeg");
        cookieBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int counter = Integer.parseInt(counterFeild.getText());
                counterFeild.setText(Integer.toString(++counter));
            }
        });

        jPanel.add(cookieBtn);

        // Set constraints for the cookie image
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cookieBtn, 0, SpringLayout.HORIZONTAL_CENTER, jPanel);
        springLayout.putConstraint(SpringLayout.NORTH, cookieBtn, 20, SpringLayout.SOUTH, bannerImg);

        // 3. Counter label
        JLabel counterLabel = new JLabel("Clicks: ");
        counterLabel.setFont(new Font("Dialog", Font.PLAIN, 26));
        jPanel.add(counterLabel);

        // 4. Counter field (adjust size to match the label height and width)
        counterFeild = new JTextField(12);  // Slightly wider field (12 columns)
        counterFeild.setHorizontalAlignment(SwingConstants.RIGHT);
        counterFeild.setText("0");
        counterFeild.setEditable(false);
        counterFeild.setPreferredSize(new Dimension(counterLabel.getPreferredSize().width + 40, counterLabel.getPreferredSize().height)); // Match the height of the label
        jPanel.add(counterFeild);

        // Set constraints for the counter label
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, counterLabel, -60, SpringLayout.HORIZONTAL_CENTER, jPanel);  // Left of the field
        springLayout.putConstraint(SpringLayout.NORTH, counterLabel, 20, SpringLayout.SOUTH, cookieBtn);

        // Set constraints for the counter field (aligned with counterLabel)
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, counterFeild, 60, SpringLayout.HORIZONTAL_CENTER, jPanel);  // Right of the label
        springLayout.putConstraint(SpringLayout.NORTH, counterFeild, 20, SpringLayout.SOUTH, cookieBtn);

        // 5. Reset button
        JButton reset = new JButton("Reset");
        reset.setFont(new Font("Dialog", Font.PLAIN, 18));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counterFeild.setText("0");
            }
        });

        jPanel.add(reset);



        // Set constraints for the reset button (below the label and field, centered)
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, reset, 0, SpringLayout.HORIZONTAL_CENTER, jPanel); // Centered
        springLayout.putConstraint(SpringLayout.NORTH, reset, 20, SpringLayout.SOUTH, counterFeild); // Below the counter field

        // Add the panel to the frame
        this.getContentPane().add(jPanel);
    }

    private JButton loadCookieImg(String filename) {
        JButton jButton;
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(filename);
            Image img = ImageIO.read(inputStream);
            jButton = new JButton(new ImageIcon(img));
            return jButton;
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    private JLabel loadImage(String fileName) {
        BufferedImage image;
        JLabel imgContainer;

        try {
            InputStream inputStream = this.getClass().getResourceAsStream(fileName);
            image = ImageIO.read(inputStream);
            imgContainer = new JLabel(new ImageIcon(image));
            return imgContainer;

        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

}
