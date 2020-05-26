package haskellmd2.dragons;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Dragons extends JavaPlugin {

    public static Dragons plugin;
    public static String world_name;

    public WorldEditPlugin world_edit;
    public String floder;
    private Logger log;


    private void getWorldEdit(){
        world_edit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        if(world_edit instanceof WorldEditPlugin){
            System.out.println("worldEdit ok");
        }else{
            world_edit=null;
        }
    }

    void set_text_sing(String wn){

        int x = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.x");
        int y = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.y");
        int z = (int) Dragons.plugin.getConfig().get("arens.arena.sing_pos.z");
        Block b = new Location(Bukkit.getWorld(wn),x,y,z).getBlock();
        if(b.getState() instanceof Sign){
            BlockState state = b.getState();
            Sign sign = (Sign)state;

            sign.setLine(0,ChatColor.DARK_PURPLE+"Dragons");
            sign.setLine(1,ChatColor.YELLOW+"Присоединиться");
            sign.setLine(3,ChatColor.GREEN+"0/"+(int) Dragons.plugin.getConfig().get("max_players"));
            sign.update(true);;

        }
    }


    @Deprecated
    @Override
    public void onEnable() {
        plugin = this;
        log  = Logger.getLogger("Minecraft");
        log.info("Dragons ok");

        //create config
        File config = new File(getDataFolder()+File.separator+"config.yml");
        if(!config.exists()) {
            log.info("Config is created . . . .");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        //get path
        floder = getDataFolder()+File.separator;

        //регистрируем хук
        Bukkit.getPluginManager().registerEvents(new Hook(),this);

        //get world
        world_name = (String) Dragons.plugin.getConfig().get("world_name");

        //sing text
        set_text_sing(world_name);

        //GameClass
        GameClass game =new GameClass(this);

        //spawn arena
//        try {
//            game.loadArea(Bukkit.getWorld("world"),floder+"/schematics/1.schematic",new Vector(100,100,100));
//        } catch (DataException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (MaxChangedBlocksException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
