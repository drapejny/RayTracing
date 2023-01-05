package by.slizh.raytracing;

import by.slizh.raytracing.frame.SettingsPanel;
import by.slizh.raytracing.frame.ViewPort;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JDialog settingsDialog = new JDialog(frame, "Settings");
        ViewPort viewPort = new ViewPort(frame);
        frame.add(viewPort);
        frame.setVisible(true);

        SettingsPanel settingsPanel = new SettingsPanel(viewPort);
        settingsDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        settingsDialog.setSize(350, 600);
        settingsDialog.add(settingsPanel);
        settingsDialog.setVisible(true);

    }
}