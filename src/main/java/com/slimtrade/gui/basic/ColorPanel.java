package com.slimtrade.gui.basic;

import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel implements IColorable {

    public ColorPanel(){
        super();
    }

    public ColorPanel(LayoutManager layout){
        super(layout);
    }

    @Override
    public void updateColor() {
        //TODO
    }

}
