package fr.kwizzy.waroflegions.util.bukkit.builder;

import fr.kwizzy.waroflegions.util.storage.JSONStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Par Alexis le 04/10/2016.
 */

public class JsonMessageBuidler {

    private JSONArray jsonArray = new JSONArray();
    private LinkedHashMap<String, JComp> creatorHashMap = new LinkedHashMap<>();

    public JComp newJComp(String text){
        return new JComp(this, text);
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
        private JSONStorage j = new JSONStorage();

        public JComp(JsonMessageBuidler jsonMessageBuidler, String text) {


            this.jmb = jsonMessageBuidler;

            j.put("text", text.replace("{}", "").replace("[]", ""));
            jmb.creatorHashMap.put(text, this);
        }

        public JComp addText(String text) {
            return new JComp(jmb, text);
        }

        public JComp addCommandExecutor(String command){
            j.put("clickEvent.action", "run_command");
            j.put("clickEvent.value", command);
            return this;
        }

        public JComp addCommandSuggest(String command){
            j.put("clickEvent.action", "suggest_command");
            j.put("clickEvent.value", command);
            return this;
        }

        public JComp addHoverEvent(String... hover){
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < hover.length; i++) {
                b.append(hover[i]);
                if(i != hover.length-1)
                    b.append("\n");
            }
            j.put("hoverEvent.action", "show_text");
            j.put("hoverEvent.value", b.toString());
            return this;
        }

        public JComp addChatSuggest(String chat){
            j.put("insertion", chat);
            return this;
        }

        public JComp addUrl(String url){
            j.put("clickEvent.action", "open_url");
            j.put("clickEvent.value", url);
            return this;
        }

        public JComp addColor(ChatColor c){
            j.put("color", c.name().toLowerCase());
            return this;
        }

        public JSONObject getJson() {
            return j.getFullJson();
        }
    }



}
