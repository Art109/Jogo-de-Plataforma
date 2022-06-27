package com.arthur.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.arthur.entities.Player;
import com.arthur.main.Game;



public class UI {
	


	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(10, 10, 250, 30);
		g.setColor(Color.GREEN);
		g.fillRect(10, 10,(int)(Player.vida/100 * 250), 30);
		g.setColor(Color.white);
		g.drawRect(10,10,250,30);
	}
	
}
