package me.kiconsen.corecenterwar.CommandListeners;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class CommandCoolDown implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label,String[] args){
        int i=Integer.parseInt(args[0]);
        Player p=(Player)sender;
        p.setCooldown(Material.WHEAT_SEEDS,i);
        return true;
    }
}
