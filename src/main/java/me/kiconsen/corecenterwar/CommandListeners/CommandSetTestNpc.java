package me.kiconsen.corecenterwar.CommandListeners;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;
import me.kiconsen.corecenterwar.CoreCenterWar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CommandSetTestNpc implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player p=(Player)sender;
        NPC.Personal npc= NPCLib.getInstance().generatePersonalNPC(p, CoreCenterWar.Instance,"test",p.getLocation());
        npc.setText("TEST");
        npc.create();
        npc.show();
        return true;
    }
}
