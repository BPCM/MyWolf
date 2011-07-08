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

import java.util.ArrayList;
import java.util.List;
import de.Keyle.MyWolf.MyWolf;
import de.Keyle.MyWolf.Wolves;

public class MapMenuTask
{
	private static List<Wolves> Wolf = new ArrayList<Wolves>();
	private int Timer = -1;
	
	public void Stop()
	{
		MyWolf.Plugin.getServer().getScheduler().cancelTask(Timer);
		this.Timer = -1;		
	}
	
	public void addInterface(Wolves Wolf)
	{
		if(MapMenuTask.Wolf.contains(Wolf) == false && this.Timer != -1)
		{
			MapMenuTask.Wolf.add(Wolf);
		}
	}

	public void Start()
	{
		if(this.Timer != -1)
		{
			MyWolf.Plugin.getServer().getScheduler().cancelTask(Timer);
			this.Timer = -1;
		}
		this.Timer = MyWolf.Plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(MyWolf.Plugin, new Runnable()
		{
			public void run()
			{
				if(MapMenuTask.Wolf.size() > 0)
				{
					for(Wolves w : MapMenuTask.Wolf)
					{
						if(w != null)
						{
							w.Mapinterface.drawInterface();
						}
						MapMenuTask.Wolf.remove(w);
					}
				}
			}
		}, 0L, 20L);
	}
}
