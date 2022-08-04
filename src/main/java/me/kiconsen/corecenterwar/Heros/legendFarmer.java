package me.kiconsen.corecenterwar.Heros;

import com.sun.tools.javac.jvm.Items;
import de.tr7zw.nbtapi.NBTItem;
import me.kiconsen.corecenterwar.CoreCenterWar;
import me.kiconsen.corecenterwar.Hero;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.GRAY;

public class legendFarmer extends Hero implements Listener {
    private List<Hero> markHeros;
    private BukkitTask waitbt;
    private boolean magicseedcooling=false;
    private boolean shougezhiliancooling=false;
    private boolean shengmingyvfucooling=false;
    public legendFarmer(){
        Name="神农";
        HeroMat=Material.DIAMOND_HOE;
        power= Power.Builder;
        markHeros=new ArrayList<>();
    }
    @Override
    public void ActorInit(){
        ItemStack shengmingyvfu=new ItemStack(Material.IRON_HOE);
        ItemMeta shengmingyvfuItemMeta= shengmingyvfu.getItemMeta();
        NBTItem shengmingyvfunbti=new NBTItem(shengmingyvfu);
        shengmingyvfunbti.setInteger("HideFlags",127);
        shengmingyvfuItemMeta.setDisplayName("生命予赋");
        List<String> smyfloretext=new ArrayList<>();
        smyfloretext.add("对所有被【奇迹之种】标记的友方英雄+4生命"+GRAY+"冷却 8s");
        shengmingyvfuItemMeta.setLore(CoreCenterWar.Instance.getLoreText(smyfloretext));
        shengmingyvfu.setItemMeta(shengmingyvfuItemMeta);
        actor.getInventory().setItem(1,shengmingyvfu);

        ItemStack shougezhilian=new ItemStack(Material.IRON_HOE);
        ItemMeta shougezhilianm= shougezhilian.getItemMeta();
        NBTItem nbti=new NBTItem(shougezhilian);
        nbti.setInteger("HideFlags",127);
        shougezhilianm.setDisplayName("收割之镰");
        List<String> loretext=new ArrayList<>();
        loretext.add("收割之镰:对所有被【奇迹之种】标记的敌方英雄造成8点伤害"+GRAY+"冷却 18s");
        shougezhilianm.setLore(CoreCenterWar.Instance.getLoreText(loretext));
        shougezhilian.setItemMeta(shougezhilianm);
        actor.getInventory().setItem(2,shougezhilian);
    }
    @Override
    public void onRightClick(PlayerInteractEvent e){
        ItemStack item=e.getPlayer().getInventory().getItemInMainHand();
        if(item!=null && item.getType().equals(Material.IRON_HOE) && !shougezhiliancooling){
            if(markHeros.size()>0){
                List<Hero> demaged=new ArrayList<>();
                for(Hero hero:markHeros){
                    hero.actor.damage(8,actor);
                    demaged.add(hero);
                }
                e.getPlayer().setCooldown(Material.IRON_HOE,360);
                shougezhiliancooling=true;
                BukkitTask bt=new BukkitRunnable(){
                    @Override
                    public void run(){
                        shougezhiliancooling=false;
                    }
                }.runTaskLater(CoreCenterWar.Instance,360);
                for(Hero hero:demaged){
                    markHeros.remove(hero);
                }
            }

        }else if(item!=null && item.getType().equals(Material.APPLE) && !shengmingyvfucooling){
            if(markHeros.size()>0){
                List<Hero> cured=new ArrayList<>();
                for(Hero hero:markHeros){
                    if(hero.power.equals(power) && !hero.actor.isDead()){
                        hero.actor.setHealth(hero.actor.getHealth()+4);
                        cured.add(hero);
                    }
                }
                for(Hero hero:cured){
                    markHeros.remove(cured);
                }
                shengmingyvfucooling=true;
                BukkitTask bt=new BukkitRunnable(){
                    @Override
                    public void run(){
                        shengmingyvfucooling=false;
                    }
                }.runTaskLater(CoreCenterWar.Instance,160);
            }
        }
    }
    @Override
    public void onRightClickEntity(PlayerInteractAtEntityEvent e){
        ItemStack item=e.getPlayer().getInventory().getItemInMainHand();
        if(item==null){
            return;
        }
        if(magicseedcooling){
            return;
        }
        if(!e.getRightClicked().getType().equals(EntityType.PLAYER)){
            return;
        }
        magicseedcooling=true;
        for(Hero hero: Room.heroes){
            if(hero.actor.equals((Player) e.getRightClicked()) && !markHeros.contains(hero)){
                markHeros.add(hero);
                if(item.getType().equals(Material.WHEAT_SEEDS)){
                    markHeros.add(hero);
                    actor.sendMessage(ChatColor.GREEN+"已标记 "+hero.Name);
                    e.getPlayer().setCooldown(Material.WHEAT_SEEDS,120);
                    BukkitTask bt=new BukkitRunnable(){
                        @Override
                        public void run() {
                            item.setAmount(item.getAmount()-1);
                            magicseedcooling=false;
                        }
                    }.runTaskLater(CoreCenterWar.Instance,120);
                    break;
                }
            }
        }

    }

    @Override
    public void onPlayerMovement(PlayerMoveEvent ev){
        Material moveto=ev.getTo().getBlock().getType();
        if(moveto.equals(Material.GRASS)){
            for(Hero hero: Room.getRuiners()){
                hero.actor.hidePlayer(CoreCenterWar.Instance,actor);
            }
            if(actor.getInventory().getItem(0)!=null && actor.getInventory().getItem(0).getType().equals(Material.WHEAT_SEEDS) && actor.getInventory().getItem(0).getAmount()==3){
                if(waitbt!=null){
                    waitbt.cancel();
                }
                waitbt=null;
                return;
            }
            if(waitbt==null){
                waitbt= new BukkitRunnable(){
                    public void run(){
                        if(actor.getInventory().getItem(0)!=null && actor.getInventory().getItem(0).getType().equals(Material.WHEAT_SEEDS)){
                            ItemStack magicseed_=actor.getInventory().getItem(0);
                            if(magicseed_.getAmount()<5){
                                magicseed_.setAmount(magicseed_.getAmount()+1);
                            }else{
                                return;
                            }
                        }else{
                            ItemStack magicseed=new ItemStack(Material.WHEAT_SEEDS);
                            actor.getInventory().setItem(0,magicseed);
                        }
                        Bukkit.getLogger().info("获得种子");
                    }
                }.runTaskTimer(CoreCenterWar.Instance,30L,60L);
            }
        }else{
            for(Hero hero:Room.getRuiners()){
                hero.actor.showPlayer(CoreCenterWar.Instance,actor);
            }
            if(waitbt!=null){
                waitbt.cancel();
                waitbt=null;
            }
        }
    }
}
