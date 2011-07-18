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

package de.Keyle.MyWolf.Listeners;

import de.Keyle.MyWolf.event.LevelUpEvent;
import de.Keyle.MyWolf.event.LevelUpListener;

import de.Keyle.MyWolf.ConfigBuffer;

public class MyWolfLevelUpListener extends LevelUpListener
{
	@Override
	public void onLevelUp(LevelUpEvent event)
	{
		if (ConfigBuffer.SkillPerLevel.containsKey(event.getLevel()))
		{
			for (String skill : ConfigBuffer.SkillPerLevel.get(event.getLevel()))
			{
				if (ConfigBuffer.RegisteredSkills.containsKey(skill))
				{
					ConfigBuffer.RegisteredSkills.get(skill).activate(event.getWolf(), null);
				}
			}
		}
	}
}