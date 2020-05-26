package haskellmd2.dragons;

import com.sk89q.worldedit.internal.annotation.Direction;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


import java.security.cert.TrustAnchor;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

public class DragonMove {


    private EnderDragon drag;
    private World w;
    private double raznica_x,raznica_y,raznica_z, x_plus,y_plus,z_plus, x,y,z ;
    private float yaw , pitch;
    private Location loc1, loc2;
    private Dragons main;
    public Player player;
    private int  taskid;
    private int max1;

    public DragonMove(EnderDragon dragon, Location loc_start , Player pl, Dragons plug) {

        this.player = pl;
        this.drag = dragon;
        this.w = loc_start.getWorld();
        x = y = z = 0;
        this.main = plug;

        raznica_x = loc_start.getX() - pl.getLocation().getX();
        raznica_y = loc_start.getY() - pl.getLocation().getY();
        raznica_z = loc_start.getZ() - pl.getLocation().getZ();

        loc1 = loc_start;
        loc2 = pl.getLocation();

        max1 = Math.max((int)raznica_x, (int)raznica_z);
        max1 = Math.max(max1, (int)raznica_y);
        if(max1 != 0) {
            x_plus = abs(raznica_x / max1);
            y_plus = abs(raznica_y / max1);
            z_plus = abs(raznica_z / max1);
        }else
        {
            x_plus = z_plus = y_plus = 0;
        }
        Double tga = pl.getLocation().getX()/pl.getLocation().getZ();
        pitch = 0;
        yaw  = 100f;//(float) atan(tga);
        System.out.println(raznica_x+" "+" "+y_plus+" " +" "+raznica_z+"      "+max1);//del
    }

    void move() {


        if (max1 != 0){

            Vector dir = loc1.getDirection();
            GameClass.task1 = true;
            System.out.println(GameClass.task1);
            taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
                Location loc_real;

                @Override
                public void run() {

                    loc_real = drag.getLocation();
                    //ось x
                    if (Math.round(abs(raznica_x)) >= Math.round(x)) {
                        if (raznica_x > 0) {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX() - x_plus, loc_real.getY(), loc_real.getZ(), yaw , pitch));
                        } else {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX() + x_plus, loc_real.getY(), loc_real.getZ(), yaw, pitch).setDirection(dir));
                        }
                        x = x + x_plus;
                    } else System.out.println("x - stop");// x_run=true;
                    loc_real = drag.getLocation();

                    //ось y
                    if ((int) abs(raznica_y) >= y) {
                        if (raznica_y > 0) {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX(), loc_real.getY() - y_plus, loc_real.getZ(), yaw , pitch));
                        } else {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX(), loc_real.getY() + y_plus, loc_real.getZ(), yaw , pitch));
                            ;
                        }
                        y = y + y_plus;
                    } else System.out.println("y - stop");//y_run=true;
                    loc_real = drag.getLocation();

                    //ось z
                    if (abs(raznica_z) >= z) {
                        if (raznica_z > 0) {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX(), loc_real.getY(), loc_real.getZ() - z_plus, yaw , pitch));
                        } else {
                            drag.teleport(new Location(Bukkit.getWorld("world"), loc_real.getX(), loc_real.getY(), loc_real.getZ() + z_plus, yaw , pitch));
                        }
                        z = z_plus + z;
                    } else System.out.println("z - stop"); // z_run=true;

                    System.out.println(x + " " + " " + y + " " + z);

                    //стопим
                    if (abs(raznica_x) <= x && /*abs(raznica_y)<=y &&*/ abs(raznica_z) <= z) {
                        System.out.println("stop!");
                        stop();
                    }


                }
            }, 0L, 1L);
        }else {
            restart();
        }
    }
     void stop(){
        Bukkit.getServer().getScheduler().cancelTask(taskid);
        GameClass.task1 = false;
     }
     void restart(){
         GameClass.task1 = false;
     }
}
