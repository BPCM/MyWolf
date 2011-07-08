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

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import de.Keyle.MyWolf.MapMenu.MapMenuControl;
import de.Keyle.MyWolf.Skill.MyWolfSkill;

public class MapMenuEvent extends Event implements Cancellable
{
	private static final long serialVersionUID = -28500399990260455L;

	protected String ID;
	protected boolean cancelled;
	protected Location location;
	protected MapMenuControl Control;
	protected Player player;
	protected MyWolfSkill Method;

	public MapMenuEvent(String ID, Player player, MapMenuControl Control, Location location, MyWolfSkill Method)
	{
		super("LevelUpEvent");
		this.location = location;
		this.ID = ID;
		this.player = player;
		this.Control = Control;
		this.Method = Method;
	}

	public MyWolfSkill getMethod()
	{
		return Method;
	}

	public String getControlID()
	{
		return ID;
	}

	public Player getPlayer()
	{
		return player;
	}

	public Location getLocation()
	{
		return location;
	}

	public MapMenuControl getControl()
	{
		return Control;
	}

	@Override
	public boolean isCancelled()
	{
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel)
	{
		this.cancelled = cancel;
	}
}
