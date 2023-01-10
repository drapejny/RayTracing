package by.slizh.raytracing.frame;


import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    // Resolution
    JLabel lbViewportResolution;
    JSlider sdViewportResolution;

    // Field of view
    JLabel lbFOV;
    JSlider sdFOV;

    // Light coords
    JLabel lbLightCoords;
    JSpinner spLightX, spLightY, spLightZ;
    JButton btnChangeLight;

    // Solid info
    JLabel lbReflectivity;
    JSlider sdReflectivity;
    JLabel lbRed;
    JSlider sdRed;
    JLabel lbGreen;
    JSlider sdGreen;
    JLabel lbBlue;
    JSlider sdBlue;

    public SettingsPanel(ViewPort viewport) {
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
        gbc.gridy = 2;
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
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdFOV, gbc);
        this.add(sdFOV);

        lbLightCoords = new JLabel("Light coordinates");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 5, 0, 0);
        gbPanel0.setConstraints(lbLightCoords, gbc);
        this.add(lbLightCoords);

        SpinnerModel spmLightX = new SpinnerNumberModel(0, -1000, 1000, 10);
        spLightX = new JSpinner(spmLightX);
        spLightX.setEditor(new JSpinner.NumberEditor(spLightX, "#"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(spLightX, gbc);
        this.add(spLightX);

        SpinnerModel spmLightY = new SpinnerNumberModel(50, -1000, 1000, 10);
        spLightY = new JSpinner(spmLightY);
        spLightY.setEditor(new JSpinner.NumberEditor(spLightY, "#"));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbPanel0.setConstraints(spLightY, gbc);
        this.add(spLightY);

        SpinnerModel spmLightZ = new SpinnerNumberModel(-100, -1000, 1000, 10);
        spLightZ = new JSpinner(spmLightZ);
        spLightZ.setEditor(new JSpinner.NumberEditor(spLightZ, "#"));
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbPanel0.setConstraints(spLightZ, gbc);
        this.add(spLightZ);

        btnChangeLight = new JButton("Change light");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbPanel0.setConstraints(btnChangeLight, gbc);
        this.add(btnChangeLight);

        lbReflectivity = new JLabel("Solid Reflectivity");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbReflectivity, gbc);
        this.add(lbReflectivity);

        sdReflectivity = new JSlider();
        sdReflectivity.setMinimum(0);
        sdReflectivity.setMaximum(100);
        sdReflectivity.setValue(0);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdReflectivity, gbc);
        this.add(sdReflectivity);

        lbRed = new JLabel("Solid Red");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbRed, gbc);
        this.add(lbRed);

        sdRed = new JSlider();
        sdRed.setMinimum(1);
        sdRed.setMaximum(255);
        sdRed.setValue(1);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdRed, gbc);
        this.add(sdRed);

        lbGreen = new JLabel("Solid Green");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbGreen, gbc);
        this.add(lbGreen);

        sdGreen = new JSlider();
        sdGreen.setMinimum(1);
        sdGreen.setMaximum(255);
        sdGreen.setValue(1);
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdGreen, gbc);
        this.add(sdGreen);

        lbBlue = new JLabel("Solid Blue");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbPanel0.setConstraints(lbBlue, gbc);
        this.add(lbBlue);

        sdBlue = new JSlider();
        sdBlue.setMinimum(1);
        sdBlue.setMaximum(255);
        sdBlue.setValue(1);
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints(sdBlue, gbc);
        this.add(sdBlue);

        sdViewportResolution.addChangeListener(e -> viewport.setResolution(sdViewportResolution.getValue() / 100f));

        sdFOV.addChangeListener(e -> viewport.setFOV(sdFOV.getValue()));

        btnChangeLight.addActionListener(e -> viewport.setLightPoint((int) spLightX.getValue(), (int) spLightY.getValue(), (int) spLightZ.getValue()));

        sdReflectivity.addChangeListener(e -> viewport.setReflectivity(sdReflectivity.getValue() / 100.f));

        sdRed.addChangeListener(e -> viewport.setRed(sdRed.getValue()));

        sdGreen.addChangeListener(e -> viewport.setGreen(sdGreen.getValue()));

        sdBlue.addChangeListener(e -> viewport.setBlue(sdBlue.getValue()));
    }
}
