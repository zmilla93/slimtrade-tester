package com.slimtrade.gui.options.basics;

import com.slimtrade.gui.FrameManager;

import javax.swing.*;
import java.awt.*;

public class LabelComponentPanel extends JPanel {

    public LabelComponentPanel(JLabel label, Component component) {
        super(FrameManager.gridBag);
        this.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        gc.anchor = GridBagConstraints.WEST;
        this.add(label, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        this.add(component, gc);

    }

}
