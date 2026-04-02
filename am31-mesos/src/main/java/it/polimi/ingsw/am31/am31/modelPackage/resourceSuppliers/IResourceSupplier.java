package it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers;

import java.io.IOException;

public interface IResourceSupplier<T> {
    T getResources() throws IOException;
}
