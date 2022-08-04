package me.kiconsen.corecenterwar.CommandListeners;

import me.kiconsen.corecenterwar.CoreCenterWar;
import me.kiconsen.corecenterwar.WarRoom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandJoinWar implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args){
        if(sender instanceof Player){
            Player p=(Player)sender;
            for(WarRoom wr:CoreCenterWar.rooms){
                if(wr.hasPlayer(p)){
                    p.sendMessage(ChatColor.RED+"你已经在一场对局中了，请先输入/exitwar退出对局");
                    return true;
                }
            }
            for(WarRoom wr: CoreCenterWar.rooms){
                if(wr.getRoomId().equals(args[0])){
                    if(wr.joinCheck()){
                        wr.JoinRoom(p);
                        p.sendMessage("成功加入房间:"+wr.getRoomId());
                        Bukkit.getLogger().info("玩家 "+p.getName()+"加入房间"+wr.getRoomId());
                        p.openInventory(CoreCenterWar.Instance.GetHeroChoseGUI(p));
                    }else{
                        p.sendMessage("此战场人数已满");
                    }
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED+"没有找到这个ID的房间");
        }
        return true;
    }
}
