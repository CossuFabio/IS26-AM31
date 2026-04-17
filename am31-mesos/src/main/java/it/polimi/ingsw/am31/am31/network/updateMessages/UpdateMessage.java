package it.polimi.ingsw.am31.am31.network.updateMessages;

public abstract class UpdateMessage {

    private final String updateType;

    protected UpdateMessage(String updateType){
        this.updateType = updateType;
    }

    public String getUpdateType(){return updateType; }

}
