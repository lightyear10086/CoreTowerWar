package me.kiconsen.corecenterwar;

import de.tr7zw.nbtapi.NBTItem;
import dev.sergiferry.playernpc.api.NPCLib;
import me.kiconsen.corecenterwar.CommandListeners.*;
import me.kiconsen.corecenterwar.Heros.legendFarmer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;

import java.util.*;

import static org.bukkit.ChatColor.*;
public final class CoreCenterWar extends JavaPlugin implements Listener {
    public static CoreCenterWar Instance;
    public static List<WarRoom> rooms;
    public static void Loginfo(String info){
        Bukkit.getLogger().info(info);
    }
    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent ev){
        for(WarRoom wr:rooms){
            for(Hero hero:wr.heroes){
                hero.onPlayerMovement(ev);
            }
        }
    }
    @EventHandler
    public void onMarkPlayer(PlayerInteractEvent e){
        for(WarRoom wr:rooms){
            for(Hero hero:wr.heroes){
                hero.onRightClick(e);
            }
        }
    }
    @EventHandler
    public void onRightClickEntity(PlayerInteractAtEntityEvent e){
        for(WarRoom wr:rooms){
            for(Hero hero:wr.heroes){
                hero.onRightClickEntity(e);
            }
        }
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        for(WarRoom wr:rooms){
            for(Hero hero:wr.heroes){
                hero.onRightClick(e);
            }
        }
    }
    @Override
    public void onEnable() {
        if(Instance==null){
            Instance=this;
        }
        NPCLib.getInstance().registerPlugin(this);
        rooms=new ArrayList<WarRoom>();
        // Plugin startup logic
        this.getLogger().info("################");
        this.getLogger().info(RED+"核心塔战场"+WHITE+"已载入");
        this.getLogger().info("################");

        Bukkit.getPluginManager().registerEvents(new CommandListener(),this);
        Bukkit.getPluginManager().registerEvents(new InitListeners(),this);
        Bukkit.getPluginManager().registerEvents(new legendFarmer(),this);
        Bukkit.getPluginManager().registerEvents(this,this);

        Bukkit.getPluginCommand("warroom").setExecutor(new CommandListener());
        Bukkit.getPluginCommand("exitwar").setExecutor(new CommandExitWar());
        Bukkit.getPluginCommand("joinwar").setExecutor(new CommandJoinWar());
        Bukkit.getPluginCommand("warlist").setExecutor(new CommandWarList());
        Bukkit.getPluginCommand("settestnpc").setExecutor(new CommandSetTestNpc());
        Bukkit.getPluginCommand("cooldown").setExecutor(new CommandCoolDown());
    }
    public void hidePlayer(Player from,Player p){
        from.hidePlayer(this,p);
    }
    public Inventory GetHeroChoseGUI(Player owner){
        Inventory inv=Bukkit.createInventory(owner,6*9,"核心战争-选择你的英雄");

        ItemStack shadowEye=new ItemStack(Material.ENDER_EYE);
        ItemMeta shadowmeta=shadowEye.getItemMeta();
        shadowmeta.setDisplayName(RED +"暗影之眼");
        List<String> loretext=new ArrayList<>();
        loretext.add(GRAY+"阵营：拆除");
        loretext.add("影之眼：每过10秒，暗影之眼会获得1个【影之眼】，至多3个，朝指定方向掷出【影之眼】，在其落地后，英雄瞬移至【影之眼】所在坐标"+GRAY+"6s");
        loretext.add("影之镰:每击中敌方【英雄】1次，积累1层【影咒】，在【暗影之眼】死亡后，对敌方所有英雄造成累计【影咒】层数的伤害，随机分配"+GRAY+"0s");
        loretext.add("影割:15秒内每使用【影之眼】瞬移1次，攻击力上升2点，在这期间若击杀#敌方英雄，则立即获得1个新的【影之眼】"+GRAY+"60s");
        shadowmeta.setLore(getLoreText(loretext));
        shadowEye.setItemMeta(shadowmeta);
        NBTItem nbti=new NBTItem(shadowEye);
        nbti.setInteger("HideFlags",127);
        inv.setItem(0,shadowEye);

        ItemStack legendFarmer=new ItemStack(Material.DIAMOND_HOE);
        shadowmeta=legendFarmer.getItemMeta();
        shadowmeta.setDisplayName(RED +"神农");
        loretext=new ArrayList<>();
        loretext.add(GRAY+"阵营：建造");
        loretext.add("奇迹之种：神农位于草丛中时，每过3秒，获得1个【奇迹之种】，至多5个，将奇迹之种右键标记至任意双方英雄"+GRAY+"0s");
        loretext.add("生命予赋：对所有被【奇迹之种】标记的友方英雄+4生命"+GRAY+"8s");
        loretext.add("收割之镰:对所有被【奇迹之种】标记的敌方英雄造成8点伤害"+GRAY+"18s");
        shadowmeta.setLore(getLoreText(loretext));
        legendFarmer.setItemMeta(shadowmeta);
        nbti=new NBTItem(legendFarmer);
        NBTItem legendfarmernbti=new NBTItem(legendFarmer);
        legendfarmernbti.setInteger("HideFlags",127);
        inv.setItem(1,legendFarmer);

        ItemStack legendNinja=new ItemStack(Material.NETHER_STAR);
        shadowmeta=legendNinja.getItemMeta();
        shadowmeta.setDisplayName(RED +"黑刃忍者");
        loretext=new ArrayList<>();
        loretext.add(GRAY+"阵营：拆除");
        loretext.add("魂钉契：重生时选择1个敌方英雄标记之，标记英雄阵亡时可重选");
        loretext.add("疾风杀：黑刃忍者在奔跑时获得+3攻击力加成"+GRAY+"3s");
        loretext.add("影乱意：在附近召唤3个黑刃忍者的复制体，本体与随机一个复制体互换位置，复制体将自动攻击最近的敌方单位，复制体最长持续存在12秒，在本体受到伤害后，复制体立即消失"+GRAY+"6s");
        loretext.add("绝命斩：瞬移至【魂钉契】标记的敌方英雄处，3秒内对标记英雄造成的伤害+6"+GRAY+"30s");
        shadowmeta.setLore(getLoreText(loretext));
        legendNinja.setItemMeta(shadowmeta);
        nbti=new NBTItem(legendNinja);
        legendfarmernbti=new NBTItem(legendNinja);
        legendfarmernbti.setInteger("HideFlags",127);
        inv.setItem(2,legendNinja);
        return inv;
    }
    public void Hurt(int amount,Hero hurtfrom,Hero hurtaim){
        hurtaim.actor.damage(hurtaim.ReadytoBeHurted(amount,hurtfrom), hurtfrom.actor);
    }
    public List<String> getLoreText(List<String> text){
        List<String> retList=new ArrayList<>();
        for(String str:text){
            for(String str_:Arrays.asList(ChatPaginator.wordWrap(str,24))){
                retList.add(str_);
            }
        }
        return retList;
    }
    public void SetStackMeta(ItemStack ist,ItemMeta imt){
        ist.setItemMeta(imt);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("已跃离核心塔战场");
    }
}
