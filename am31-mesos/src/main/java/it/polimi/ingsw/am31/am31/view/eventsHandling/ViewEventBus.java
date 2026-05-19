package it.polimi.ingsw.am31.am31.view.eventsHandling;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


//Source for this implementation: https://medium.com/@sadegh.dehghani1992/how-it-works-eventbus-cef03ac2a12f

public class ViewEventBus implements IEventBus {

    private final Set<Object> subscribers;


    public ViewEventBus(){
        subscribers = ConcurrentHashMap.newKeySet();
    }

    @Override
    public boolean register(Object obj){
        return subscribers.add(obj);
    }

    //Don't forget to call this method when unmounting the scene!
    @Override
    public boolean unregister(Object obj) {
        return subscribers.remove(obj);
    }

    @Override
    public void post(ViewEvent event) {
        for(Object subscriber : subscribers){

            //getDeclaredMethods cerca solo i metodi dichiarati fisicamente nella classe concreta che stai ispezionando, senza guardare le sue superclassi.
            //noi usiamo il metodo onConnectionLost(ConnectionLostEvent event) nella classe BaseController (astratta) cosi che in ogni
            //scena del gioco se crasha il server arriva l'allert. getMethods risale la gerarchia e restituisce tutti i metodi pubblici
            //anche delle superclassi
            for(Method method : subscriber.getClass().getMethods()){

                Annotation annot =  method.getAnnotation(Subscribe.class);

                //1- Checks if the annotation is present
                //2- Checks if it takes only one parameter
                //3- Checks if the parameter type is the same as the ViewEvent posted
                //If all checks are passed, the corresponding method is called

                if(annot != null &&
                    method.getParameterCount() == 1 &&
                    method.getParameterTypes()[0].getName().equals(event.getClass().getName())) {
                    //Methods that handle exception should never throw exception.
                    //This is an additional guard.
                    try {
                        method.invoke(subscriber, event);
                    } catch (Exception e) {
                        //Ignore the exception
                    }
                }

            }
        }
    }

}
