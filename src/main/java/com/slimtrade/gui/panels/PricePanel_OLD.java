package com.slimtrade.gui.panels;

import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.PaintedPanel;
import com.slimtrade.gui.enums.POEImage;

import javax.swing.*;
import java.awt.*;

public class PricePanel_OLD extends PaintedPanel {

//	private JLabel label = new JLabel();

	public PricePanel_OLD(String price, double quant, boolean paren) {
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		label = new JLabel();
		String num = quant > 0 ? TradeUtility.getFixedDouble(quant, paren) + " " : "" ;
		POEImage poeImage = TradeUtility.getPOEImage(price.toLowerCase());
		if(poeImage!=null){
			label.setText(num);
			this.add(label, gc);
			gc.gridx++;
			IconPanel img = new IconPanel(poeImage.getImage(20), 25);
			this.add(img, gc);
		}else{
			label.setText(num + price);
			this.add(label, gc);
		}
	}
	
	public JLabel getLabel(){
		return this.label;
	}

}
