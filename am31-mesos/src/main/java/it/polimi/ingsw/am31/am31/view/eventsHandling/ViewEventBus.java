package it.polimi.ingsw.am31.am31.view.eventsHandling;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Implementation of the {@link IEventBus} interface.
 * This implementation registers subscribers in a thread safe data structure. When an event is posted, this class uses reflection
 * to scan for methods with the {@link Subscribe} annotation and the corresponding {@link ViewEvent} as parameter. It calls method only if their unique parameter
 * is the matching ViewEvent type.
 * Warning: this class is not thread safe: since the subscribers may register or remove other subscribers, this pattern cannot guarantee thread safety. This task must be
 * performed listener-side.
 */
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
