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
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.Keyle.MyWolf.Wolves;

public class MapMenu
{
	Wolves Wolf;
	public String Selection;
	Map<String, MapMenuControl> Controls = new HashMap<String, MapMenuControl>();
	List<String> ControlsPositions = new ArrayList<String>();
	Graphics2D g;

	public MapMenu(Wolves wolf, Graphics2D g)
	{
		this.Wolf = wolf;
		this.g = g;
	}

	public void run()
	{
		if(Wolf.isDead == false && Wolf.isThere == true)
		{
			Controls.get(Selection).run(Wolf, null);
		}
	}

	public void addControl(MapMenuControl ctrl)
	{
		ControlsPositions.add(ctrl.Name);
		Controls.put(ctrl.Name, ctrl);
		if (Selection == null)
		{
			Selection = ctrl.Name;
		}
	}

	public void setNext(boolean updown)
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
			Controls.get(Selection).setNext(true);
		}
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

	public void draw(int X, int Y, int Width, int Height)
	{
		g.setColor(Color.BLACK);
		for (String Name : ControlsPositions)
		{
			Y += Controls.get(Name).draw(X, Y, Width, Height, g, Selection.equals(Name) ? true : false);
		}
	}

	public Object clone()
	{
		return this.clone();
	}
}
