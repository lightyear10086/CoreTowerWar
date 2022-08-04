package me.kiconsen.corecenterwar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Hero {
    public Player actor;
    public String Name;
    public WarRoom Room;
    public Material HeroMat;
    public enum Power{Builder,Ruiner};
    public Power power;
    public int Level;
    public void onPlayerMovement(PlayerMoveEvent ev){}
    public void onRightClick(PlayerInteractEvent ev){}
    public void onRightClickEntity(PlayerInteractAtEntityEvent ev){}
    public void ActorInit(){}
    public int ReadytoBeHurted(int hurtamount,Hero hurtfrom){
        return hurtamount;
    }
}
