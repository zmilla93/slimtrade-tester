package com.slimtrade.gui.stash.helper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.slimtrade.App;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.ButtonType;
import com.slimtrade.core.observing.poe.PoeInteractionEvent;
import com.slimtrade.core.observing.poe.PoeInteractionListener;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicPanel;

public class StashHelper extends JPanel {

	private static final long serialVersionUID = 1L;

	public static int width = 150;
	public static int height = 40;

	// private int x;
	// private int y;
	private int borderThickness = 2;
	// private String itemName;

	public ItemHighlighter itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	public Timer highlighterTimer = new Timer(2000, hideHighlighter);

	private PoeInteractionListener poeInteractionListener = App.macroEventManager;

	public StashHelper(TradeOffer trade, Color colorBackground, Color colorForeground) {
		this.setLayout(FrameManager.gridBag);
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createLineBorder(colorForeground, borderThickness));

		String fixedStashtabName = trade.stashtabName == null ? "(~price " + Double.toString(trade.priceCount).replaceAll("[.]0", "") + " " + trade.priceTypeString + ")" : trade.stashtabName;
		JLabel stashLabel = new JLabel(fixedStashtabName);
		stashLabel.setForeground(colorForeground);
		JLabel itemLabel = new JLabel(trade.itemName);
		itemLabel.setForeground(colorForeground);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		this.add(stashLabel, gc);
		gc.gridy++;
		this.add(itemLabel, gc);

		// ITEM HIGHLIGHTER
		this.setBackground(colorBackground);
		if (trade.stashtabName != null) {
			itemHighlighter = new ItemHighlighter(trade.stashType, trade.stashtabX, trade.stashtabY, colorBackground);

			this.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if(App.saveManager.saveFile.enableItemHighlighter) {
						highlighterTimer.stop();
						itemHighlighter.updatePos(12);
						itemHighlighter.setVisible(true);
						itemHighlighter.repaint();
					}
				}
			});

			this.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					highlighterTimer.restart();
				}
			});
		}
		
		this.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), ButtonType.SEARCH, trade));
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					hideStashHelper();
					FrameManager.stashHelperContainer.pack();
				}
			}
		});
	}

	private void hideStashHelper() {
		this.setVisible(false);
		if(itemHighlighter != null){
			itemHighlighter.setVisible(false);
		}
	}

}
