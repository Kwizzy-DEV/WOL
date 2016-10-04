package fr.kwizzy.waroflegions.util.bukkit.builder;

import fr.kwizzy.waroflegions.util.storage.JSONCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Par Alexis le 04/10/2016.
 */

public class JsonMessageBuidler {

    private JSONArray jsonArray = new JSONArray();
    private LinkedHashMap<String, JComp> creatorHashMap = new LinkedHashMap<>();

    public JComp newJComp(String text){
        JComp jc = new JComp(this, text);
        return jc;
    }

    private void build(){
        creatorHashMap.entrySet().forEach(x -> jsonArray.put(x.getValue().getJson()));
    }

    public void send(Player... player){
        build();
        for (Player p : player) {
            if(p == null)
                continue;
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + jsonArray.toString() );
        }
    }

    public void send(Collection<? extends Player> player){
        Player[] t = new Player[player.size()];
        send(player.toArray(t));
    }

    public void send(){
        send(Bukkit.getOnlinePlayers());
    }




    public class JComp {

        private JsonMessageBuidler jmb;
        private JSONCreator j = new JSONCreator(new JSONObject());

        public JComp(JsonMessageBuidler jsonMessageBuidler, String text) {


            this.jmb = jsonMessageBuidler;

            j.set("text", text.replace("{}", "").replace("[]", ""));
            jmb.creatorHashMap.put(text, this);
        }

        public JComp addText(String text) {
            return new JComp(jmb, text);
        }

        public JComp addCommandExecutor(String command){
            j.set("clickEvent.action", "run_command");
            j.set("clickEvent.value", command);
            return this;
        }

        public JComp addCommandSuggest(String command){
            j.set("clickEvent.action", "suggest_command");
            j.set("clickEvent.value", command);
            return this;
        }

        public JComp addHoverEvent(String... hover){
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < hover.length; i++) {
                b.append(hover[i]);
                if(i != hover.length-1)
                    b.append("\n");
            }
            j.set("hoverEvent.action", "show_text");
            j.set("hoverEvent.value", b.toString());
            return this;
        }

        public JComp addChatSuggest(String chat){
            j.set("insertion", chat);
            return this;
        }

        public JComp addUrl(String url){
            j.set("clickEvent.action", "open_url");
            j.set("clickEvent.value", url);
            return this;
        }

        public JComp addColor(ChatColor c){
            j.set("color", c.name().toLowerCase());
            return this;
        }

        public JSONObject getJson() {
            return j.getJson();
        }
    }



}
