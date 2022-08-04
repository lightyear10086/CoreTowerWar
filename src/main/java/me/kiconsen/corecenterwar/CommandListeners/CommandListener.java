package me.kiconsen.corecenterwar.CommandListeners;

import me.kiconsen.corecenterwar.CoreCenterWar;
import me.kiconsen.corecenterwar.Hero;
import me.kiconsen.corecenterwar.Heros.legendFarmer;
import me.kiconsen.corecenterwar.Heros.shadowEye;
import me.kiconsen.corecenterwar.WarRoom;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandListener implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
        if(sender instanceof Player){
            Player p=(Player)sender;
            for (WarRoom wr :
                    CoreCenterWar.rooms) {
                if(wr.players.contains(p)){
                    sender.sendMessage("你已经在一场对局中了");
                    return false;
                }
            }
        }

        sender.sendMessage("一场核心战争已开启");
        WarRoom newroom=new WarRoom();
        CoreCenterWar.rooms.add(newroom);
        newroom.JoinRoom((Player) sender);
        sender.sendMessage("房间ID："+newroom.getRoomId());
        if(sender instanceof Player){
            Player p=(Player)sender;
//            Inventory inv=Bukkit.createInventory(p,6*9,"核心战争-选择你的英雄");
//
//            ItemStack shadowEye=new ItemStack(Material.ENDER_EYE);
//            inv.setItem(0,shadowEye);
//            ItemStack legendFarmer=new ItemStack(Material.DIAMOND_HOE);
//            inv.setItem(1,legendFarmer);
            p.openInventory(CoreCenterWar.Instance.GetHeroChoseGUI(p));
        }

        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked().getOpenInventory().getTitle().equals("核心战争-选择你的英雄"))
            e.setCancelled(true);
        if(e.getRawSlot()<0 || e.getRawSlot()>e.getInventory().getSize() || e.getInventory()==null)
            return;
        Player p=(Player)e.getWhoClicked();

        Hero newhero=new Hero();
        switch (e.getRawSlot()){
            case 0:
                newhero=new shadowEye();
                break;
            case 1:
               newhero=new legendFarmer();
               break;
        }
        p.sendMessage("你将扮演 "+newhero.Name);

        newhero.actor=p;
        newhero.ActorInit();
        for(WarRoom wr:CoreCenterWar.Instance.rooms){
            for(Player _p:wr.players){
                _p.sendMessage("玩家"+_p.getName()+"将扮演 "+newhero.Name);
                if(_p.equals(p)){
                    newhero.Room=wr;
                    wr.heroes.add(newhero);
                }
            }
        }
        p.closeInventory();
    }
}
