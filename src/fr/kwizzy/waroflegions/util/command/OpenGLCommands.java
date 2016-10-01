package fr.kwizzy.waroflegions.util.command;

import core.opengl.OpenGL;
import core.opengl.constant.Constants;
import core.opengl.display.SimpleIdProvider;
import core.opengl.display.test.TestDisplay;
import core.opengl.effect.AtomEffect;
import core.opengl.math.Matrix4f;
import core.opengl.particle.LavaParticle;
import core.opengl.scheduler.TimeUnit;
import core.opengl.scoreboard.Score;
import core.opengl.scoreboard.Scoreboard;
import core.opengl.selector.Selector;
import core.opengl.text.ColorCharText;
import core.opengl.text.ITextUpdater;
import core.opengl.text.ScrollingText;
import core.opengl.text.TextUpdaterUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenGLCommands implements CommandListener{
    Scoreboard sc = new Scoreboard(Scoreboard.ScoreboardPosition.SIDEBAR , "title" , ChatColor.RED + "Hello");

    @CommandHandler(args = {"selector"} , alias = {"s"} , sender = Player.class)
    public void selector(Command<Player> command){
        if(!checkSender(command))return;
        new Selector(command.s());
    }

    @CommandHandler(args = {"testdisplay"} , alias = {"td"} , sender = Player.class)
    public void display(Command<Player> command){
        if(!checkSender(command))return;
        Selector s = Selector.get(command.s());
        //Todo ! Send Lang Message
        if(!s.hasTwoLocations())return;
        new TestDisplay(s.getFirstLocation() , s.getSecondLocation() , new SimpleIdProvider());
    }

    @CommandHandler(args = {"effect"} , alias = {"e"} , sender = Player.class)
    public void effect(Command<Player> command){
        if(!checkSender(command))return;
        Location loc = command.s().getLocation();
        new AtomEffect(() -> loc , new LavaParticle() , Matrix4f.identity());
    }

    @CommandHandler(args = {"testscoreboard"} , alias = {"ts"} , sender = Player.class)
    public void scoreboard(Command<Player> command){
        System.out.println("Hello");
        if(!checkSender(command))return;
        Score score = new Score("hello" , "1" , 0);
        ITextUpdater updater = TextUpdaterUtils.combine(new ColorCharText(Constants.RAINBOW) , new ScrollingText("Sceat est con , !!!!! je ne l'aime pas abcdefghijefghijklmnopqrstv" , 30));
        updater.schedule(1 , TimeUnit.TICKS);
        score.setUpdaterOnUpdate(updater);
        Score score2 = new Score("hello2" , "2" , 1);
        ColorCharText t = new ColorCharText(Constants.RAINBOW);
        t.setText("Rainbbow");
        score2.setUpdaterOnUpdate(t);
        sc.add(score);
        sc.add(score2);
        sc.add(command.s());
    }

    @CommandHandler(args = {"testscoreboarde"} , alias = {"tse"} , sender = Player.class)
    public void scoreboarde(Command<Player> command){
        if(!checkSender(command))return;
        for(Score s : sc.geScores()){
            s.remove(sc);
        }
    }

    private boolean checkSender(CommandSender sender){
        return OpenGL.getInstance().canExecOGLCmd(sender);
    }

    private boolean checkSender(Command<?> c){
        //Todo ! Send Lang Message
        return checkSender(c.s());
    }
}
