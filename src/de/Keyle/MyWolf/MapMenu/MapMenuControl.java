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

package de.Keyle.MyWolf.MapMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import de.Keyle.MyWolf.Skill.MyWolfSkill;
import de.Keyle.MyWolf.event.MapMenuEvent;
import de.Keyle.MyWolf.util.MyWolfUtil;
import de.Keyle.MyWolf.MyWolf;
import de.Keyle.MyWolf.Wolves;

public class MapMenuControl
{
	public String Name;
	public String Text;

	public boolean Next = true;

	public boolean Visible = true;

	public MyWolfSkill Method;

	final public void setMethod(MyWolfSkill Method)
	{
		this.Method = Method;
	}

	public void run(Wolves wolf, Object arg)
	{
		MyWolf.Plugin.getServer().getPluginManager().callEvent(new MapMenuEvent(Name, wolf.getPlayer(), this, wolf.getPlayer().getLocation(), Method));
		if (Method != null)
		{
			MyWolfUtil.Log.info("1");
			Method.run(wolf, null);
		}
	}

	public MapMenuControl(String Name)
	{
		this.Name = Name;
		this.Text = Name;
	}

	public int draw(int X, int Y, int Width, int Height, Graphics2D g, boolean Selected)
	{
		if (Visible == true)
		{
			FontRenderContext frc = g.getFontRenderContext();
			Font f = new Font("Arial", 1, 10);
			TextLayout tl = new TextLayout(Text, f, frc);
			tl.draw(g, X - 7 + 20, Y + 7);
			if (Selected)
			{
				g.setColor(Color.BLUE);
				g.drawOval(X - 3, Y + 2, 3, 3);
				g.setColor(Color.BLACK);
			}
			return 10;
		}
		return Y;
	}

	public boolean setNext(boolean updown)
	{
		return Next;
	}
}
