package fr.kwizzy.waroflegions.util.bukkit.command;

import org.bukkit.command.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {
    String UNDEFINED = "\n\tUNDEFINED\t\n";

    String args() default "";
    String[] alias() default {};
    boolean pattern() default false;
    boolean infinite() default false;

    Class<? extends CommandSender> sender() default CommandSender.class;

}
