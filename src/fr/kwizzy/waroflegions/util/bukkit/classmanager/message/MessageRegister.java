package fr.kwizzy.waroflegions.util.bukkit.classmanager.message;

import static fr.kwizzy.waroflegions.util.bukkit.classmanager.ClassUtil.*;

import fr.kwizzy.waroflegions.util.java.Tuple;
import fr.kwizzy.waroflegions.util.storage.ISaveable;
import fr.kwizzy.waroflegions.util.storage.JSONStorage;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Par Alexis le 27/10/2016.
 */


public class MessageRegister implements ISaveable
{

    private JSONStorage message = new JSONStorage("data/message");
    private final File jar;
    private final String packagame;

    public MessageRegister(JavaPlugin java, String packagame){
        this.jar = getJar(java);
        this.packagame = packagame;
    }

    public void init()
    {


        List<Tuple<Class, Field, String, String>> tuple = getMessages();
        tuple.forEach(t -> {
            String path = t.getA().getSimpleName().concat(".").concat(t.getC());
            String d = t.getD();
            if(!message.contain(path))
                message.put(path, d.replace("§", "&"));
        });

        JSONObject fullJson = message.getFullJson();
        Collection<JSONObject> jsonObjects = new ArrayList<>();
        fullJson.keySet().forEach(e -> jsonObjects.add(fullJson.getJSONObject(e)));
        jsonObjects.forEach(e ->
            e.keySet().forEach(s -> {
                Object obj = e.get(s);
                if(obj instanceof String){
                    String o = (String) obj;
                    setValueField(tuple, s.replace("&", "§"), o);
                }
            })
        );

        message.save();
        message.disable();
    }

    private void setValueField(List<Tuple<Class, Field, String, String>> tuple, String fieldName, String value)
    {
        tuple.stream().filter(e -> e.getC().equals(fieldName)).forEach(e -> set(e.getB(), value));
    }

    private List<Tuple<Class, Field, String, String>> getMessages()
    {
        List<Tuple<Class, Field, String, String>> messages = new ArrayList<>();
        getFields().forEach(e -> {
            String value = (String) get(e);
            String fieldName = e.getName();
            messages.add(new Tuple<>(e.getDeclaringClass(), e, fieldName, value));
        });
        return messages;
    }

    private Collection<Field> getFields()
    {
        Collection<Field> fields = new ArrayList<>();
        Set<Class<?>> classes = getClasses(jar, packagame);
        classes.stream()
        .forEach(e -> {
            Field[] f = e.getDeclaredFields();
            for (Field field : f)
            {
                field.setAccessible(true);
                if(hasAnnotation(field, Message.class) && get(field) != null)
                    fields.add(field);
            }
        });
        return fields;
    }

    @Override
    public void save()
    {
        message.save();
    }
}
