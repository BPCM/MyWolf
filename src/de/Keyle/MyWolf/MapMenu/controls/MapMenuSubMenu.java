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

package de.Keyle.MyWolf.MapMenu.controls;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.Keyle.MyWolf.MyWolf;
import de.Keyle.MyWolf.Wolves;
import de.Keyle.MyWolf.MapMenu.*;
import de.Keyle.MyWolf.event.MapMenuEvent;

public class MapMenuSubMenu extends MapMenuControl
{
	Map<String, MapMenuControl> Controls = new HashMap<String, MapMenuControl>();
	List<String> ControlsPositions = new ArrayList<String>();
	public String Selection;

	public MapMenuSubMenu(String Name)
	{
		super(Name);
		this.Text = Name;
		Controls.put("Back", new MapMenuSubMenuBack("Back"));
		ControlsPositions.add("Back");
	}

	public void addControl(MapMenuControl ctrl)
	{
		String tmpback = ControlsPositions.get(ControlsPositions.size() - 1);
		ControlsPositions.remove(ControlsPositions.size() - 1);
		ControlsPositions.add(ctrl.Name);
		ControlsPositions.add(tmpback);

		Controls.put(ctrl.Name, ctrl);

		Selection = ControlsPositions.get(0);
	}

	@Override
	public boolean setNext(boolean updown)
	{
		if (Controls.get(Selection).Next)
		{
			if (updown)
			{
				if (ControlsPositions.indexOf(Selection) + 1 == ControlsPositions.size())
				{
					Selection = ControlsPositions.get(0);
				}
				else
				{
					Selection = ControlsPositions.get(ControlsPositions.indexOf(Selection) + 1);
				}
			}
			else
			{
				if (ControlsPositions.indexOf(Selection) - 1 < 0)
				{
					Selection = ControlsPositions.get(ControlsPositions.size() - 1);
				}
				else
				{
					Selection = ControlsPositions.get(ControlsPositions.indexOf(Selection) - 1);
				}
			}
		}
		else
		{
			Controls.get(Selection).setNext(updown);
		}
		return Next;
	}

	public List<MapMenuControl> getControlList()
	{
		List<MapMenuControl> tmpctrl = new ArrayList<MapMenuControl>();
		for (String Name : Controls.keySet())
		{
			tmpctrl.add(Controls.get(Name));
		}
		return tmpctrl;
	}

	public MapMenuControl getControl(String Name)
	{
		if (ControlsPositions.contains(Name))
		{
			return Controls.get(Name);
		}
		return null;
	}

	@Override
	public int draw(int X, int Y, int Width, int Height, Graphics2D g, boolean Selected)//, Object[] args)
	{
		int momY = Y;
		if (this.Visible == true)
		{
			FontRenderContext frc = g.getFontRenderContext();
			Font f = new Font("Arial", 1, 10);
			TextLayout tl = new TextLayout(Text, f, frc);
			tl.draw(g, X - 7 + 20, Y + 7);

			tl = new TextLayout(">", f, frc);
			tl.draw(g, X - 7 + 10, Y + 7);

			if (Selected)
			{
				g.setColor(Color.BLUE);
				g.drawOval(X - 3, Y + 2, 3, 3);
				g.setColor(Color.BLACK);
			}
			momY += 10;
			if (Next == false)
			{
				for (String ctrlName : ControlsPositions)
				{
					momY += Controls.get(ctrlName).draw(X + 10, momY, Width, Height, g, Selection.equals(ctrlName) ? true : false);
				}
			}
		}
		return momY - Y;
	}

	@Override
	public void run(Wolves wolf, Object arg)
	{
		if (Next == true)
		{
			MyWolf.Plugin.getServer().getPluginManager().callEvent(new MapMenuEvent(Name, wolf.getPlayer(), this, wolf.getPlayer().getLocation(), Method));
			Next = false;
			if (Method != null)
			{
				Method.run(wolf, null);
			}
		}
		else
		{
			Controls.get(Selection).run(wolf, this);
		}
	}
}
