package me.kiconsen.corecenterwar;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarRoom {
    private String roomId;
    public List<Hero> heroes;
    public List<Player> players;

    public WarRoom(){
        heroes =new ArrayList<Hero>();
        players=new ArrayList<>();
        roomId=String.valueOf(this.hashCode());
    }
    public String getRoomId(){
        return roomId;
    }
    public boolean joinCheck(){
        if(heroes.size()==10){
            return false;
        }
        return true;
    }
    public boolean hasPlayer(Player p){
        if(players.contains(p)){
            return true;
        }
        return false;
    }
    public List<Hero> getBuilders(){
        List<Hero> builders=new ArrayList<>();
        for(Hero h:heroes){
            if(h.power== Hero.Power.Builder){
                builders.add(h);
            }
        }
        return builders;
    }

    public List<Hero> getRuiners(){
        List<Hero> ruiners=new ArrayList<>();
        for(Hero h:heroes){
            if(h.power== Hero.Power.Ruiner){
                ruiners.add(h);
            }
        }
        return ruiners;
    }
    public void KickPlayerFromRoom(Player p){
        if(players.contains(p)){

            for (Hero _p :
                    heroes) {
                _p.actor.sendMessage("玩家"+p.getName()+"已退出战场");
            }
        }
        players.remove(p);
        Hero h=null;
        for(Hero _p:heroes){
            if(_p.actor==p){
                h=_p;
            }
        }
        heroes.remove(h);
    }
    public void GameInit(){

    }
    public boolean JoinRoom(Player h){
        if(heroes.size()<10){
            players.add(h);
            for(Player p:players){
                p.sendMessage("玩家"+h.getName()+"已加入战场!");
            }
            return true;
        }
        return false;
    }
}
