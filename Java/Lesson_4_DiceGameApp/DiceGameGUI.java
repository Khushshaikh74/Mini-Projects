package Lesson_4_DiceGameApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceGameGUI extends JFrame {
    public DiceGameGUI() {
        super("Double Dice Rolling Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600); // Explicitly set the frame size
        setResizable(false);
        setLocationRelativeTo(null);

        AddGUIComp();
    }

    private void AddGUIComp() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null); // Using null layout for custom positioning

        // 1. Banner
        JLabel bannerImg = ImageService.loadImage("Resources/banner.jpeg"); // Assuming loadImage returns JLabel
        if (bannerImg != null) {
            bannerImg.setBounds(40, 20, 500, 150); // Adjusted position and size
            jPanel.add(bannerImg);
        }

        // 2. Dice One
        JLabel diceOneImg = ImageService.loadImage("Resources/dice1.png");
        if (diceOneImg != null) {
            diceOneImg.setBounds(60, 200, 220, 220);
            jPanel.add(diceOneImg);
        }

        // 3. Dice Two
        JLabel diceTwoImg = ImageService.loadImage("Resources/dice1.png");
        if (diceTwoImg != null) {
            diceTwoImg.setBounds(320, 200, 220, 220);
            jPanel.add(diceTwoImg);
        }

        // 4. Roll Button
        Random rand = new Random();
        JButton rollBtn = new JButton("Roll!");
        rollBtn.setBounds(200, 470, 200, 50);
        rollBtn.setFont(new Font("Dialog", Font.BOLD, 26));
        rollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollBtn.setEnabled(false);

                // Roll for three seconds
                long startTime = System.currentTimeMillis();
                Thread rollThread = new Thread(() -> {
                    try {
                        while ((System.currentTimeMillis() - startTime) / 1000F < 3) {
                            int diceOne = rand.nextInt(6) + 1; // Generate random number between 1 and 6
                            int diceTwo = rand.nextInt(6) + 1;

                            // Update dice images
                            ImageService.updateImg(diceOneImg, "Resources/dice" + diceOne + ".png");
                            ImageService.updateImg(diceTwoImg, "Resources/dice" + diceTwo + ".png");

                            // Repaint the frame
                            SwingUtilities.invokeLater(() -> {
                                repaint();
                            });

                            // Sleep the thread for 60 milliseconds
                            Thread.sleep(60);
                        }
                    } catch (InterruptedException ex) {
                        System.out.println("Thread Interrupted: " + ex.getMessage());
                    } finally {
                        // Re-enable the button safely on the Event Dispatch Thread
                        SwingUtilities.invokeLater(() -> rollBtn.setEnabled(true));
                    }
                });

                rollThread.start(); // Start the thread
            }
        });
        jPanel.add(rollBtn);

        // Set the panel as the content pane
        this.setContentPane(jPanel);
    }
}
