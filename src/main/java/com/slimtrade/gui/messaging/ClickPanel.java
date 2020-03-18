package com.slimtrade.gui.messaging;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ClickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Color backgroundDefault;
	public Color backgroundHover;
	
	public Color textDefault;
	public Color textHover;

	public Border borderDefault;
	public Border borderHover;
	
	public JLabel label = new JLabel();

	
	//TODO : Add label?
	public ClickPanel() {
		JPanel p = this;
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				p.setBackground(backgroundHover);
				p.setForeground(textHover);
				p.setBorder(borderHover);
			}

			public void mouseExited(MouseEvent e) {
				p.setBackground(backgroundDefault);
				p.setForeground(textDefault);
				p.setBorder(borderDefault);
			}
		});
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public void refresh(){
		this.setBackground(backgroundDefault);
		this.setForeground(textDefault);
		this.setBorder(borderDefault);
	}
}
