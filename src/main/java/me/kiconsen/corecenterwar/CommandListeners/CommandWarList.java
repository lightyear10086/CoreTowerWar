package me.kiconsen.corecenterwar.CommandListeners;

import me.kiconsen.corecenterwar.CoreCenterWar;
import me.kiconsen.corecenterwar.WarRoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandWarList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
        for(WarRoom wr: CoreCenterWar.rooms){
            sender.sendMessage("开放房间："+wr.getRoomId());
        }
        return true;
    }
}
