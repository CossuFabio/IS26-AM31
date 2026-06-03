package it.polimi.ingsw.am31.am31.view.eventsHandling;

/**
 * Event bus interface for the view layer.
 * <p>
 * Allows objects to subscribe to and receive {@link ViewEvent}s. Once registered,
 * a subscriber's methods annotated with {@link Subscribe} are invoked whenever a
 * matching event is posted. Threading guarantees depend on the implementation.
 * </p>
 */
public interface IEventBus {

    /**
     * Registers an object as a subscriber to this event bus.
     * Once registered, the object's methods annotated with {@link Subscribe}
     * will be invoked when a matching {@link ViewEvent} is posted.
     *
     * @param obj the subscriber to register
     * @return {@code true} if the subscriber was added; {@code false} if already registered
     */
    boolean register(Object obj);

    /**
     * Removes an object from this event bus, stopping it from receiving further events.
     * Must be called when a scene or component is unmounted to prevent stale listeners.
     *
     * @param obj the subscriber to remove
     * @return {@code true} if the subscriber was removed; {@code false} if it was not registered
     */
    boolean unregister(Object obj);

    /**
     * Dispatches a {@link ViewEvent} to all registered subscribers.
     * Each subscriber whose handler method is annotated with {@link Subscribe} and whose
     * parameter type matches the runtime type of {@code event} will be invoked.
     *
     * @param event the event to dispatch
     */
    void post(ViewEvent event);
}
