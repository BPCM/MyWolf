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
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftWolf;

import de.Keyle.MyWolf.ConfigBuffer;
import de.Keyle.MyWolf.MyWolf;
import de.Keyle.MyWolf.Wolves;
import de.Keyle.MyWolf.Wolves.BehaviorState;
import de.Keyle.MyWolf.Wolves.WolfState;
import de.Keyle.MyWolf.util.MyWolfConfig;
import de.Keyle.MyWolf.util.MyWolfPermissions;
import de.Keyle.MyWolf.util.MyWolfUtil;

public class MyWolfPlayerListener extends PlayerListener
{
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if (event.getRightClicked() instanceof Wolf && ((Wolf) event.getRightClicked()).isTamed())
		{
			if (event.getPlayer().getItemInHand().getType() == MyWolfConfig.WolfControlItem)
			{
				Wolf w = (Wolf) event.getRightClicked();
				if (ConfigBuffer.mWolves.containsKey(event.getPlayer().getName()))
				{
					if (ConfigBuffer.mWolves.get(event.getPlayer().getName()).getID() == w.getEntityId())
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@Override
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType() == MyWolfConfig.WolfControlItem && ConfigBuffer.mWolves.containsKey(event.getPlayer().getName())) // && cb.cv.WolfControlItemSneak == event.getPlayer().isSneaking()
		{
			Wolves Wolf = ConfigBuffer.mWolves.get(event.getPlayer().getName());
			if (Wolf.Status == WolfState.Here && Wolf.isSitting() == false)
			{
				if (MyWolfPermissions.has(event.getPlayer(), "mywolf.control.walk") == false)
				{
					return;
				}
				Block block = event.getPlayer().getTargetBlock(null, 100);
				if (block != null)
				{
					PathPoint[] loc = { new PathPoint(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ()) };
					EntityWolf wolf = ((CraftWolf) Wolf.Wolf).getHandle();
					wolf.setPathEntity(new PathEntity(loc));
					if (MyWolfPermissions.has(event.getPlayer(), "mywolf.control.attack") == false)
					{
						return;
					}
					for (Entity e : Wolf.Wolf.getNearbyEntities(1, 1, 1))
					{
						if (e instanceof LivingEntity)
						{
							if (Wolf.Behavior == BehaviorState.Raid)
							{
								if (e instanceof Player || (e instanceof Wolf && ((Wolf) e).isTamed() == true))
								{
									continue;
								}
							}
							if (e instanceof Player)
							{
								if ((Player) e == Wolf.getOwner() == false && MyWolfUtil.isNPC((Player) e) == false && e.getWorld().getPVP() == true)
								{
									Wolf.Wolf.setTarget((LivingEntity) e);
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
			Wolves Wolf = ConfigBuffer.mWolves.get(event.getPlayer().getName());

			if (MyWolfUtil.getDistance(Wolf.getLocation(), event.getPlayer().getLocation()) < 75)
			{
				Wolf.createWolf(Wolf.isSitting());
			}
			else
			{
				Wolf.Status = WolfState.Despawned;
			}
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (ConfigBuffer.mWolves.containsKey(event.getPlayer().getName()))
		{
			Wolves Wolf = ConfigBuffer.mWolves.get(event.getPlayer().getName());

			if (Wolf.Status == WolfState.Here)
			{
				Wolf.removeWolf();
				if (Wolf.getLocation() == null)
				{
					Wolf.setLocation(event.getPlayer().getLocation());
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
			Wolves Wolf = ConfigBuffer.mWolves.get(event.getPlayer().getName());

			if (Wolf.Status == WolfState.Here)
			{
				if (Wolf.getLocation().getWorld() != event.getPlayer().getLocation().getWorld())
				{
					if (Wolf.isSitting() == false)
					{
						Wolf.removeWolf();
						Wolf.setLocation(event.getPlayer().getLocation());
						Wolf.createWolf(false);
					}
					else
					{
						Wolf.removeWolf();
					}
				}
				else if (MyWolfUtil.getDistance(Wolf.getLocation(), event.getPlayer().getLocation()) > 75)
				{
					Wolf.removeWolf();
				}
			}
			else if (Wolf.Status == WolfState.Despawned)
			{
				if (Wolf.getLocation().getWorld() == event.getPlayer().getLocation().getWorld())
				{
					if (MyWolfUtil.getDistance(Wolf.getLocation(), event.getPlayer().getLocation()) < 75)
					{
						Wolf.createWolf(Wolf.isSitting());
					}
				}
			}
		}
	}
}
