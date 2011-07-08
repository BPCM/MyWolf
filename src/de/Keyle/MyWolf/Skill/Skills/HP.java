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
import de.Keyle.MyWolf.Skill.MyWolfSkill;
import de.Keyle.MyWolf.util.MyWolfLanguage;
import de.Keyle.MyWolf.util.MyWolfUtil;

public class HP extends MyWolfSkill
{
	public HP()
	{
		this.Name = "HP";
		ConfigBuffer.RegisteredSkills.put(Name, this);
	}

	@Override
	public void activate(Wolves wolf, Object args)
	{
		wolf.HealthMax += 1;
		wolf.setWolfHealth(wolf.HealthMax);
		MyWolfUtil.sendMessage(wolf.getPlayer(), MyWolfUtil.SetColors(MyWolfLanguage.getString("Msg_AddHP").replace("%wolfname%", wolf.Name)));
	}
}
