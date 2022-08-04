package me.kiconsen.corecenterwar.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinRoomEvent extends Event implements Cancellable {
    private  static final HandlerList handlers=new HandlerList();

    private boolean cancelledFlag=false;
    private Player p;

    public PlayerJoinRoomEvent(Player p){
        this.p=p;
    }
    public Player getPlayer(){
        return p;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }
    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled(){
        return cancelledFlag;
    }
    @Override
    public void setCancelled(boolean cancelledFlag){
        this.cancelledFlag=cancelledFlag;
    }
}
