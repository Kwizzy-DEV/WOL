package fr.kwizzy.waroflegions.util.bukkit.register;

/**
 * Par Alexis le 24/10/2016.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DontRegister
{

}
