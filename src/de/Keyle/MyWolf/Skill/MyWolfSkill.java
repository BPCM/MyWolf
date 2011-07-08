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

package de.Keyle.MyWolf.Skill;

import de.Keyle.MyWolf.Wolves;

public class MyWolfSkill
{
	protected String Name;

	public String getName()
	{
		return this.Name;
	}

	public void run(Wolves wolf, Object args)
	{
	}

	public void activate(Wolves wolf, Object args)
	{
		wolf.Abilities.put(this.Name, true);
	}
}
