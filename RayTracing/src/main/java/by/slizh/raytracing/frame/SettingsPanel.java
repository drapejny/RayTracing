package by.slizh.raytracing.frame;


import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private int selectedSkyboxIndex;
    private JSpinner spImageWidth, spImageHeight;

    public SettingsPanel(ViewPort viewport) {
        // Resolution
        JLabel lbViewportResolution;
        JSlider sdViewportResolution;

        // Field of view
        JLabel lbFOV;
        JSlider sdFOV;

        JLabel lbLightHA;
        JLabel lbLighVA;
        JSlider sdLightHA, sdLightVA;
        JLabel lbLightDist;
        JSlider sdLightDistance;

        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbPanel0);

        lbViewportResolution = new JLabel("Viewport resolution");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 5, 0, 0);
        gbPanel0.setConstraints(lbViewportResolution, gbc);
        this.add(lbViewportResolution);

        sdViewportResolution = new JSlider();
        sdViewportResolution.setMinimum(1);
        sdViewportResolution.setMaximum(100);
        sdViewportResolution.setValue(25);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdViewportResolution, gbc);
        this.add(sdViewportResolution);


        lbFOV = new JLabel("Field of vision");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbFOV, gbc);
        this.add(lbFOV);

        sdFOV = new JSlider();
        sdFOV.setMinimum(10);
        sdFOV.setMaximum(120);
        sdFOV.setValue(60);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdFOV, gbc);
        this.add(sdFOV);

        lbLightHA = new JLabel("Light horizontal angle");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 5, 0, 0);
        gbPanel0.setConstraints(lbLightHA, gbc);
        this.add(lbLightHA);

        sdLightHA = new JSlider();
        sdLightHA.setMinimum(0);
        sdLightHA.setMaximum((int) (Math.PI * 200));
        sdLightHA.setValue(sdLightHA.getMinimum());
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdLightHA, gbc);
        this.add(sdLightHA);

        lbLighVA = new JLabel("Light vertical angle");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbLighVA, gbc);
        this.add(lbLighVA);


        sdLightVA = new JSlider();
        sdLightVA.setMinimum(0);
        sdLightVA.setMaximum((int) (Math.PI * 200));
        sdLightVA.setValue(sdLightVA.getMaximum());
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdLightVA, gbc);
        this.add(sdLightVA);

        lbLightDist = new JLabel("Light distance");
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbLightDist, gbc);
        this.add(lbLightDist);

        sdLightDistance = new JSlider();
        sdLightDistance.setMinimum(0);
        sdLightDistance.setMaximum(200);
        sdLightDistance.setValue(20);
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdLightDistance, gbc);
        this.add(sdLightDistance);

        sdViewportResolution.addChangeListener(e -> viewport.setResolution(sdViewportResolution.getValue() / 100f));

        sdFOV.addChangeListener(e -> viewport.setFOV(sdFOV.getValue()));


    }
}
