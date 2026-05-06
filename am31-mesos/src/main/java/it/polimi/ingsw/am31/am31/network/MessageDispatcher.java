package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.Messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateHandler;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MessageDispatcher implements IMessageVisitor{

    private final ExecutorService executor;
    private final UpdateHandler updateHandler;
    private final ErrorHandler errorHandler;

    public MessageDispatcher(UpdateHandler updateHandler, ErrorHandler errorHandler){
        this.executor = Executors.newSingleThreadExecutor();
        this.updateHandler = updateHandler;
        this.errorHandler = errorHandler;
    }

    public void submit(Message message) {
        if (message == null || !message.checkValidity()) return;
        executor.submit(() -> {
            try {message.acceptVisit(this);}
            catch (Exception e) { System.out.println(e.getMessage()); }
        });
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void visitError(ErrorMessage message) {
        errorHandler.handleErrorMessage(message);
    }

    @Override
    public void visitUpdate(UpdateMessage update){
        updateHandler.handleUpdate(update);
    }




}
