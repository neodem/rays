package com.neodem.graphics.dd.engine.standalone;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.neodem.graphics.dd.engine.common.Color;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 600;
	private static final String APP_NAME = "Main Frame";

	private Component panel;

	private MainFrame() {
		super(APP_NAME);
		super.setSize(APP_WIDTH, APP_HEIGHT);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new TestCanvas(APP_WIDTH, APP_HEIGHT, Color.lightGray);

		super.add("Center", panel);
		super.setVisible(true);
		
		((GamePanel)panel).init(new Dimension(APP_WIDTH, APP_HEIGHT));
		((GamePanel)panel).startGame();
	}

	public static void main(String argv[]) {
		new MainFrame();
	}
}
