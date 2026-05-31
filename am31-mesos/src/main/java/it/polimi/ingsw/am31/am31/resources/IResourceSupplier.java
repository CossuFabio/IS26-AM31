package it.polimi.ingsw.am31.am31.resources;

/**
 * Supplier of game resources. Implementations load the resource once and return it on each call.
 */
public interface IResourceSupplier<T> {
    /** @return the resource provided by this supplier */
    T getResources();
}
