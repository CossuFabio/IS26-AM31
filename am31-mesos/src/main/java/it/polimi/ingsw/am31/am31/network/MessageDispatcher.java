package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.Messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IErrorVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MessageDispatcher implements IMessageVisitor{

    private final ExecutorService executor;
    private final IUpdateVisitor updateVisitor;
    private final IErrorVisitor errorVisitor;

    public MessageDispatcher(IUpdateVisitor updateVisitor, IErrorVisitor errorVisitor){
        this.executor = Executors.newSingleThreadExecutor();
        this.updateVisitor = updateVisitor;
        this.errorVisitor = errorVisitor;
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
        message.acceptVisit(errorVisitor);
    }

    @Override
    public void visitUpdate(UpdateMessage update){
        update.acceptVisit(updateVisitor);
    }




}
