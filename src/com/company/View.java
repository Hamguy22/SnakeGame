package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class View {
    private Controller controller;
    private JLabel jLabel;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void create(int width, int height) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Snake");
        jFrame.setSize(width, height);
        jFrame.setLayout(null);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setUndecorated(true);

        jLabel = new JLabel();
        jFrame.add(jLabel);
        jLabel.setBounds(0,0, width, height);

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPress(e.getKeyCode());
            }
        });

        jFrame.setVisible(true);
    }

    public void setImage(BufferedImage bufferedImage) {
        jLabel.setIcon(new ImageIcon(bufferedImage));
    }


}
