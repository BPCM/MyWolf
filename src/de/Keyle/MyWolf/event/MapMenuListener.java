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

package de.Keyle.MyWolf.event;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import de.Keyle.MyWolf.event.LevelUpEvent;

public class MapMenuListener extends CustomEventListener implements Listener
{
	public void onMenuClicked(MapMenuEvent event)
	{
	}

	@Override
	public void onCustomEvent(Event event)
	{
		if (event instanceof LevelUpEvent)
		{
			onMenuClicked((MapMenuEvent) event);
		}
	}
}
