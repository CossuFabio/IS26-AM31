package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.messages.Message;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateHandler;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Receives incoming {@link Message} and dispatches them asynchronously to the appropriate
 * handler ({@link UpdateHandler} or {@link ErrorHandler}) using the visitor pattern. The internal executor works as
 * a buffer that immediately frees the connection thread.
 * Ordered processing is guaranteed by the single-threaded executor.
 */
public class MessageDispatcher implements IMessageVisitor{

    private final ExecutorService executor;
    private final UpdateHandler updateHandler;
    private final ErrorHandler errorHandler;

    public MessageDispatcher(UpdateHandler updateHandler, ErrorHandler errorHandler){
        this.executor = Executors.newSingleThreadExecutor();
        this.updateHandler = updateHandler;
        this.errorHandler = errorHandler;
    }

    /**
     * Enqueues a message for asynchronous dispatch.
     * @param message the message to dispatch; ignored invalid
     */
    public void submit(Message message) {
        if (message == null || !message.checkValidity()) return;
        executor.submit(() -> {
            try {message.acceptVisit(this);}
            catch (Exception e) { System.out.println(e.getMessage()); }
        });
    }

    /**
     * Shuts down the executor, waiting up to 2 seconds for pending messages to complete.
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    /** Forwards to the {@link ErrorHandler}.
     * @param message error object to dispatch */
    @Override
    public void visitError(ErrorMessage message) {
        errorHandler.handleErrorMessage(message);
    }

    /** Forwards  to the {@link UpdateHandler}.
     * @param update update object to dispatch */
    @Override
    public void visitUpdate(UpdateMessage update){
        updateHandler.handleUpdate(update);
    }




}
