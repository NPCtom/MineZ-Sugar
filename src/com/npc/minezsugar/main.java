package com.npc.minezsugar;

import java.util.ArrayList;

import net.minecraft.data.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;

public class main extends JavaPlugin
        implements Listener {

    ArrayList<String> insugar = new ArrayList<String>();
    ArrayList<String> cantsugar = new ArrayList<String>();


    public void onEnable() {
        getLogger().info("MineZ Sugar Plugin by NPCtom");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) { final Player player = e.getPlayer();
        if (((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.RIGHT_CLICK_AIR))) &&
                (player.getItemInHand().getType().equals(Material.SUGAR)))
            if (!this.cantsugar.contains(player.getName())) {
                this.insugar.add(player.getName());
                this.cantsugar.add(player.getName());
                ItemStack newItemInHand = player.getItemInHand();
                newItemInHand.setAmount(player.getItemInHand().getAmount() - 1);
                player.setItemInHand(newItemInHand);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 2.0F, 1.0F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 800, 1));

                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
                        {
                            public void run() {
                                if (main.this.insugar.contains(player.getName())) {
                                    main.this.insugar.remove(player.getName());
                                    player.removePotionEffect(PotionEffectType.SPEED);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                }
                            }
                        }
                        , 600L);
                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
                        {
                            public void run() {
                                main.this.cantsugar.remove(player.getName());
                            }
                        }
                        , 601L);
            }
            else {
                player.sendMessage(ChatColor.RED + "You must wait before you can use sugar again!");
            }
    }


    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e) {


        Player player = e.getEntity();
        this.insugar.remove(player.getName());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        this.insugar.remove(e.getPlayer().getName());

    }


}