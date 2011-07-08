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
import de.Keyle.MyWolf.util.MyWolfConfig;
import de.Keyle.MyWolf.util.MyWolfLanguage;
import de.Keyle.MyWolf.util.MyWolfUtil;

public class Pickup extends MyWolfSkill
{
	public Pickup()
	{
		this.Name = "Pickup";
		ConfigBuffer.RegisteredSkills.put(Name, this);
	}

	@Override
	public void run(Wolves wolf, Object args)
	{
		if (de.Keyle.MyWolf.util.MyWolfUtil.hasSkill(wolf.Abilities, "Pickup"))
		{
			boolean Enabled;
			if(args != null)
			{
				Enabled = (Boolean) args;
			}
			else
			{
				if(wolf.DropTimer == -1)
				{
					Enabled = true;
				}
				else
				{
					Enabled = false;
				}
			}
			if (Enabled == true)
			{
				wolf.DropTimer();
				MyWolfUtil.sendMessage(wolf.getPlayer(), "Pickup activated!");
			}
			else
			{
				wolf.StopDropTimer();
				MyWolfUtil.sendMessage(wolf.getPlayer(), "Pickup stopped!");
			}
		}
	}

	@Override
	public void activate(Wolves wolf, Object args)
	{
		if (de.Keyle.MyWolf.util.MyWolfUtil.hasSkill(wolf.Abilities, "Pickup") == false)
		{
			wolf.Abilities.put("Pickup", true);
			MyWolfUtil.sendMessage(wolf.getPlayer(), MyWolfUtil.SetColors(MyWolfLanguage.getString("Msg_AddPickup")).replace("%wolfname%", wolf.Name).replace("%range%", "" + MyWolfConfig.WolfPickupRange));
		}
	}
}
