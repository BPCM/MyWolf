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

package de.Keyle.MyWolf.Skill.Skills;

import de.Keyle.MyWolf.ConfigBuffer;
import de.Keyle.MyWolf.Wolves;
import de.Keyle.MyWolf.Wolves.BehaviorState;
import de.Keyle.MyWolf.Skill.MyWolfSkill;

public class Behavior extends MyWolfSkill
{
	public Behavior()
	{
		this.Name = "Behavior";
		ConfigBuffer.RegisteredSkills.put(this.Name, this);
	}

	@Override
	public void run(Wolves Wolf, Object args)
	{
		if (de.Keyle.MyWolf.util.MyWolfUtil.hasSkill(Wolf.Abilities, "Behavior"))
		{
			if (Wolf.Behavior == BehaviorState.PASSIV)
			{
				Wolf.Behavior = BehaviorState.FRIENDLY;
			}
			else if (Wolf.Behavior == BehaviorState.FRIENDLY)
			{
				Wolf.Behavior = BehaviorState.AGRESSIV;
				Wolf.AgressiveTimer();
			}
			else if (Wolf.Behavior == BehaviorState.AGRESSIV)
			{
				Wolf.Behavior = BehaviorState.PASSIV;
			}
		}
	}
}
