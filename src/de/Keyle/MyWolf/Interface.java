/*
* Copyright (C) 2011 Keyle
*
* This file is part of MyWolf.
*
* MyWolf is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* MyWolf is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with MyWolf. If not, see <http://www.gnu.org/licenses/>.
*/

package de.Keyle.MyWolf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import shamebot.Map.MapColor;
import shamebot.Map.MapImage;
import de.Keyle.MyWolf.MapMenu.MapMenu;
import de.Keyle.MyWolf.util.MyWolfConfig;
import de.Keyle.MyWolf.util.MyWolfUtil;
import de.Keyle.MyWolf.Wolves.BehaviorState;

public class Interface
{
	public MapMenu Menu;
	public Wolves Wolf;

	MapImage mapImage = new MapImage(0, 0, 128, 128);
	BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = image.createGraphics();
	Font WolfFont = LoadWolfFont();

	Image small = loadImages("single.png");
	Image large = loadImages("double.png");

	public Interface(Wolves Wolf)
	{
		this.Wolf = Wolf;
		Menu = new MapMenu(Wolf, g);
	}

	private Font LoadWolfFont()
	{
		try
		{
			File f = new File(MyWolf.Plugin.getDataFolder().getPath() + File.separator + "WolfsRain.ttf");
			FileInputStream in = new FileInputStream(f);
			return Font.createFont(Font.TRUETYPE_FONT, in);
		}
		catch (Exception e)
		{
			return new Font("Verdana", 1, 12);
		}

	}

	private Image loadImages(String Filename)
	{
		try
		{
			return ImageIO.read(new File(MyWolf.Plugin.getDataFolder().getPath() + File.separator + "images" + File.separator + Filename));
		}
		catch (Exception e)
		{
			MyWolfUtil.Log.info("[MyWolf] can't load a image: " + Filename);
			return null;
		}
	}

	public void drawInterface()
	{
		g.setBackground(new MapColor(null, null).getColor());
		mapImage.fill(new MapColor(MapColor.Hue.White, MapColor.Brightness.High));
		g.clearRect(0, 0, 128, 128);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 25, 9);

		g.setColor(Color.BLUE);
		FontRenderContext frc = g.getFontRenderContext();
		TextLayout tl = new TextLayout(Wolf.Name, WolfFont.deriveFont(12), frc);
		tl.draw(g, 30, 5 + 7);
		g.setColor(Color.BLACK);

		Font f = new Font("Arial", 1, 10);
		if (Wolf.isDead)
		{
			g.setColor(Color.RED);
		}
		else if (Wolf.getHealth() > Wolf.HealthMax / 3 * 2)
		{
			g.setColor(Color.GREEN);
		}
		else if (Wolf.getHealth() > Wolf.HealthMax / 3 * 1)
		{
			g.setColor(Color.YELLOW);
		}
		else
		{
			g.setColor(Color.RED);
		}
		if(Wolf.isDead == true)
		{
			tl = new TextLayout("HP: DEAD", f, frc);
		}
		else
		{
			tl = new TextLayout("HP: " + (int) Wolf.getHealth() + " / " + Wolf.HealthMax, f, frc);
		}
		tl.draw(g, 8, 25);

		g.setColor(Color.BLACK);
		f = new Font("Arial", 1, 10);
		tl = new TextLayout("EXP: " + ((int) Wolf.Experience.getExp()), f, frc);
		tl.draw(g, 8, 34);

		g.setColor(Color.BLACK);
		f = new Font("Arial", 1, 10);
		tl = new TextLayout("Lv" + Wolf.Experience.getLevel(), f, frc);
		tl.draw(g, 100, 5 + 7);

		if (MyWolfUtil.hasSkill(Wolf.Abilities, "InventoryLarge"))
		{
			g.drawImage(large, 107, 20, 20, 10, null);
		}
		else if (MyWolfUtil.hasSkill(Wolf.Abilities, "InventorySmall"))
		{
			g.drawImage(small, 117, 20, 10, 10, null);
		}

		if (Wolf.Behavior == BehaviorState.PASSIV)
		{
			g.setColor(Color.YELLOW);
		}
		else if (Wolf.Behavior == BehaviorState.FRIENDLY)
		{
			g.setColor(Color.GREEN);
		}
		else if (Wolf.Behavior == BehaviorState.AGRESSIV)
		{
			g.setColor(Color.RED);
		}

		g.drawOval(107, 35, 7, 7);
		g.fillOval(107, 35, 7, 7);

		Menu.draw(5, 50, 128, 128);

		MapImage.setXCenter((short) MyWolfConfig.WolfInterfaceMapId, Wolf.getPlayer().getWorld(), 500);
		MapImage.setZCenter((short) MyWolfConfig.WolfInterfaceMapId, Wolf.getPlayer().getWorld(), 500);

		mapImage.drawImage((short) 0, (short) 0, image);
		if (Wolf.getPlayer() != null && Wolf.getPlayer().isOnline())
		{
			mapImage.send(Wolf.getPlayer(), (short) MyWolfConfig.WolfInterfaceMapId);
		}
	}
}
