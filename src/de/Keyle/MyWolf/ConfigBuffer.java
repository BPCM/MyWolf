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

package de.Keyle.MyWolf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.config.Configuration;

import de.Keyle.MyWolf.util.MyWolfConfig;
import de.Keyle.MyWolf.Skill.MyWolfSkill;
import de.Keyle.MyWolf.util.MyWolfLanguage;

public class ConfigBuffer
{
	public static PluginDescriptionFile pdfFile = MyWolf.Plugin.getDescription();

	public static Configuration WolvesConfig;
	public static MyWolfConfig cv;
	public static MyWolfLanguage lv;

	public static List<Player> WolfChestOpened = new ArrayList<Player>();

	public static Map<String, Wolves> mWolves = new HashMap<String, Wolves>();
	public static Map<Integer, List<String>> SkillPerLevel = new HashMap<Integer, List<String>>();
	public static Map<String, MyWolfSkill> RegisteredSkills = new HashMap<String, MyWolfSkill>();

	public static void registerSkill(String Name, MyWolfSkill Skill) throws Exception
	{
		if (RegisteredSkills.containsKey(Name) == false)
		{
			RegisteredSkills.put(Name, Skill);
		}
		else
		{
			throw new Exception("There is already a skill registered for " + Name);
		}
	}
}
