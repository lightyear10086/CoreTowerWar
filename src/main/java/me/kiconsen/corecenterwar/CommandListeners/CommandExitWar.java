package me.kiconsen.corecenterwar.CommandListeners;

import me.kiconsen.corecenterwar.CoreCenterWar;
import me.kiconsen.corecenterwar.WarRoom;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CommandExitWar implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
        if(sender instanceof Player){
            Player p=(Player) sender;
            for(WarRoom wr: CoreCenterWar.rooms){
                if(wr.hasPlayer(p)){
                    wr.KickPlayerFromRoom(p);
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED+"你没有加入任何一场对局");
        }

        return true;
    }
}
