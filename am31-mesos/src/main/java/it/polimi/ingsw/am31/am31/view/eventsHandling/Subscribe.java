package it.polimi.ingsw.am31.am31.view.eventsHandling;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Retention policy forces Java to keep the annotation even during RUNTIME and not discard
//it at compile time
@Retention(RetentionPolicy.RUNTIME)

//@Target defines that the only possible subscribers are methods
@Target({ElementType.METHOD})

public @interface Subscribe { }
