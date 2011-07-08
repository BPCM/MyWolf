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

import net.minecraft.server.EntityWolf;
import net.minecraft.server.PathEntity;
import net.minecraft.server.PathPoint;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftWolf;

import de.Keyle.MyWolf.ConfigBuffer;
import de.Keyle.MyWolf.MyWolf;
import de.Keyle.MyWolf.util.MyWolfConfig;
import de.Keyle.MyWolf.util.MyWolfPermissions;
import de.Keyle.MyWolf.util.MyWolfUtil;

public class MyWolfPlayerListener extends PlayerListener
{
	@Override
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType() == MyWolfConfig.WolfControlItem && ConfigBuffer.mWolves.containsKey(event.getPlayer().getName())) // && cb.cv.WolfControlItemSneak == event.getPlayer().isSneaking()
		{
			if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isThere = true && ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false && ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf.isSitting() == false)
			{
				if (MyWolfPermissions.has(event.getPlayer(), "mywolf.control.walk") == false)
				{
					return;
				}
				Block block = event.getPlayer().getTargetBlock(null, 100);
				if (block != null)
				{
					PathPoint[] loc = { new PathPoint(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()) };
					EntityWolf wolf = ((CraftWolf) ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf).getHandle();
					wolf.setPathEntity(new PathEntity(loc));
					if (MyWolfPermissions.has(event.getPlayer(), "mywolf.control.attack") == false)
					{
						return;
					}
					for (Entity e : ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf.getNearbyEntities(1, 1, 1))
					{
						if (e instanceof LivingEntity)
						{
							if (e instanceof Player)
							{
								if (((Player) e).equals(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getPlayer()) == false && MyWolfUtil.isNPC((Player) e) == false && e.getWorld().getPVP() == true)
								{
									ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf.setTarget((LivingEntity) e);
								}
							}
							else
							{
								ConfigBuffer.mWolves.get(event.getPlayer().getName()).Wolf.setTarget((LivingEntity) e);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		if (ConfigBuffer.mWolves.containsKey(event.getPlayer().getName()))
		{
			double dist = Math.sqrt(Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getX() - event.getPlayer().getLocation().getX(), 2.0D) + Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getZ() - event.getPlayer().getLocation().getZ(), 2.0D));
			if (dist < 75)
			{
				ConfigBuffer.mWolves.get(event.getPlayer().getName()).createWolf(ConfigBuffer.mWolves.get(event.getPlayer().getName()).isSitting);
			}
			else
			{
				ConfigBuffer.mWolves.get(event.getPlayer().getName()).isThere = false;
			}
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (ConfigBuffer.mWolves.containsKey(event.getPlayer().getName()))
		{
			if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isThere == true && ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
			{
				ConfigBuffer.mWolves.get(event.getPlayer().getName()).removeWolf();
				if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation() == null)
				{
					ConfigBuffer.mWolves.get(event.getPlayer().getName()).Location = event.getPlayer().getLocation();
				}
			}
			MyWolf.Plugin.SaveWolves(ConfigBuffer.WolvesConfig);
		}
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event)
	{
		if (ConfigBuffer.mWolves.containsKey(event.getPlayer().getName()))
		{
			if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isThere == false && ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
			{
				double dist = Math.sqrt(Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getX() - event.getPlayer().getLocation().getX(), 2.0D) + Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getZ() - event.getPlayer().getLocation().getZ(), 2.0D));
				if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).RespawnTime == 0 && dist < 75 && ConfigBuffer.mWolves.get(event.getPlayer().getName()).Location.getWorld() == event.getPlayer().getLocation().getWorld())
				{
					if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
					{
						ConfigBuffer.mWolves.get(event.getPlayer().getName()).createWolf(true);
					}
				}
			}
			else
			{
				if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false && ConfigBuffer.mWolves.get(event.getPlayer().getName()).isSitting() == true)
				{

					double dist = Math.sqrt(Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getX() - event.getPlayer().getLocation().getX(), 2.0D) + Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getZ() - event.getPlayer().getLocation().getZ(), 2.0D));
					if (dist > 75 || ConfigBuffer.mWolves.get(event.getPlayer().getName()).Location.getWorld() != event.getPlayer().getLocation().getWorld())
					{
						ConfigBuffer.mWolves.get(event.getPlayer().getName()).removeWolf();
					}
					else if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isThere == false && ConfigBuffer.mWolves.get(event.getPlayer().getName()).RespawnTime == 0)
					{
						if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
						{
							ConfigBuffer.mWolves.get(event.getPlayer().getName()).createWolf(true);
						}
					}
				}
				else if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
				{

					double dist = Math.sqrt(Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getX() - event.getPlayer().getLocation().getX(), 2.0D) + Math.pow(ConfigBuffer.mWolves.get(event.getPlayer().getName()).getLocation().getZ() - event.getPlayer().getLocation().getZ(), 2.0D));
					if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).RespawnTime == 0 && dist > 75 || ConfigBuffer.mWolves.get(event.getPlayer().getName()).Location.getWorld() != event.getPlayer().getLocation().getWorld() && ConfigBuffer.mWolves.get(event.getPlayer().getName()).isDead == false)
					{
						ConfigBuffer.mWolves.get(event.getPlayer().getName()).removeWolf();
						ConfigBuffer.mWolves.get(event.getPlayer().getName()).Location = event.getPlayer().getLocation();
						ConfigBuffer.mWolves.get(event.getPlayer().getName()).createWolf(false);
					}
				}
			}
		}
	}
}
