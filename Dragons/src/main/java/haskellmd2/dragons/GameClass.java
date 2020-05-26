package haskellmd2.dragons;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameClass {

    public static List<Player> players_in_game = new ArrayList<Player>();
    public static Boolean task1 = false;

    private WorldEditPlugin world_edit;
    private String path;
    private Dragons plug;
    private static int taskid;

    //драконы
    public static List<EnderDragon> drags = new ArrayList<EnderDragon>();


    public GameClass (Dragons plugin){
        this.world_edit = plugin.world_edit;
        this.path = plugin.floder;
        this.plug = plugin;
    }

    //создаём арену.
    @Deprecated
    public void loadArea(World world, String str, Vector origin) throws DataException, IOException, MaxChangedBlocksException {
        File file = new File(str);
        EditSession es = new EditSession(new BukkitWorld(world), 999999999);
        CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
        cc.paste(es, origin, false);
    }

    public void dragon_spawn(Location loc1 /* Location loc2,Location loc3,Location loc4*/){
        drags.add((EnderDragon) loc1.getWorld().spawnEntity(loc1, EntityType.ENDER_DRAGON));
//        drags[1] = (EnderDragon) loc2.getWorld().spawnEntity(loc1, EntityType.ENDER_DRAGON);
//        drags[2] = (EnderDragon) loc3.getWorld().spawnEntity(loc1, EntityType.ENDER_DRAGON);
//        drags[3] = (EnderDragon) loc4.getWorld().spawnEntity(loc1, EntityType.ENDER_DRAGON);
    }

    public static void DragonControler(Location loc1, Player player,EnderDragon drag1){
        DragonMove move1 = new DragonMove(drag1,loc1,player,Dragons.plugin);
        System.out.println(players_in_game.get(0).getLocation());
        move1.move();
    }

    public void Start_Game(){
        Location loc1 = new Location(Bukkit.getWorld(Dragons.world_name),Dragons.plugin.getConfig().getInt("arens.arena.dragons.d1.x"),Dragons.plugin.getConfig().getInt("arens.arena.dragons.d1.y"),Dragons.plugin.getConfig().getInt("arens.arena.dragons.d1.z"));
        dragon_spawn(loc1);
        DragonControler(loc1, players_in_game.get(0), drags.get(0));

        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Dragons.plugin, new Runnable(){
            @Override
            public void run() {
                System.out.println(task1);
                if(!task1){
                    DragonControler(drags.get(0).getLocation(), players_in_game.get(0), drags.get(0));
                }
            }
        },0L,5L);

    }


}
