package by.slizh.raytracing;

import by.slizh.raytracing.frame.ViewPort;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame= new JFrame();
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ViewPort viewPort = new ViewPort(frame);
        frame.add(viewPort);
        frame.setVisible(true);

    }
}