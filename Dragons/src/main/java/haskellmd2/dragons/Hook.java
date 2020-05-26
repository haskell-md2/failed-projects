package haskellmd2.dragons;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class Hook implements Listener {

    @EventHandler
    void onClick(PlayerInteractEvent e){
        Action a =e.getAction();

        //клик по табличке
        if(a!=Action.LEFT_CLICK_AIR && a!=Action.LEFT_CLICK_BLOCK && a!=Action.RIGHT_CLICK_AIR) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                     int x = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.x");
                     int y = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.y");
                     int z = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.z");
                     if(e.getClickedBlock().getX()==x && e.getClickedBlock().getY()==y && e.getClickedBlock().getZ()==z){
                         GameClass.players_in_game.add(e.getPlayer());
                         new GameClass(Dragons.plugin).Start_Game();
                }
            }
        }
    }
}
