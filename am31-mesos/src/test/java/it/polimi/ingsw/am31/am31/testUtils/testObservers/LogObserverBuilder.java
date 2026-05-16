package it.polimi.ingsw.am31.am31.testUtils.testObservers;

import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class LogObserverBuilder implements FluentBuilder<LogObserver> {

    private boolean logBuildingsUpdate;
    private boolean logScoresUpdate;
    private boolean logTribeUpdate;
    private boolean logRoundStatusUpdate;
    private boolean logPlayersListUpdate;
    private boolean logCardLineUpdate;
    private boolean logOfferTrackUpdate;
    private boolean logTurnOrderUpdate;
    private boolean logGameStartUpdate;
    private boolean logGameEndUpdate;
    private boolean logGameCrashUpdate;
    private boolean logGameEventResolveUpdate;

    boolean isLogBuildingsUpdate() {
        return logBuildingsUpdate;
    }

    boolean isLogScoresUpdate() {
        return logScoresUpdate;
    }

    boolean isLogTribeUpdate() {
        return logTribeUpdate;
    }

    boolean isLogRoundStatusUpdate() {
        return logRoundStatusUpdate;
    }

    boolean isLogPlayersListUpdate() {
        return logPlayersListUpdate;
    }

    boolean isLogCardLineUpdate() {
        return logCardLineUpdate;
    }

    boolean isLogOfferTrackUpdate() {
        return logOfferTrackUpdate;
    }

    boolean isLogTurnOrderUpdate() {
        return logTurnOrderUpdate;
    }

    boolean isLogGameStartUpdate() {
        return logGameStartUpdate;
    }

    boolean isLogGameEndUpdate() {
        return logGameEndUpdate;
    }

    boolean isLogGameCrashUpdate() {
        return logGameCrashUpdate;
    }

    boolean isLogGameEventResolveUpdate() {
        return logGameEventResolveUpdate;
    }

    LogObserverBuilder() {
        reset();
    }


    @Override
    public void reset() {
        logBuildingsUpdate = false;
        logScoresUpdate = false;
        logTribeUpdate = false;
        logRoundStatusUpdate = false;
        logPlayersListUpdate = false;
        logCardLineUpdate = false;
        logOfferTrackUpdate = false;
        logTurnOrderUpdate = false;
        logGameStartUpdate = false;
        logGameEndUpdate = false;
        logGameEventResolveUpdate = false;
        logGameCrashUpdate = false;
    }

    public LogObserverBuilder buildingsUpdate() {
        logBuildingsUpdate = true;
        return this;
    }

    public LogObserverBuilder scoresUpdate() {
        logScoresUpdate = true;
        return this;
    }

    public LogObserverBuilder tribeUpdate() {
        logTribeUpdate = true;
        return this;
    }

    public LogObserverBuilder roundStatusUpdate() {
        logRoundStatusUpdate = true;
        return this;
    }

    public LogObserverBuilder playersListUpdate() {
        logPlayersListUpdate = true;
        return this;
    }

    public LogObserverBuilder cardLineUpdate() {
        logCardLineUpdate = true;
        return this;
    }

    public LogObserverBuilder offerTrackUpdate() {
        logOfferTrackUpdate = true;
        return this;
    }

    public LogObserverBuilder turnOrderUpdate() {
        logTurnOrderUpdate = true;
        return this;
    }

    public LogObserverBuilder gameStartUpdate() {
        logGameStartUpdate = true;
        return this;
    }

    public LogObserverBuilder gameEndUpdate() {
        logGameEndUpdate = true;
        return this;
    }

    public LogObserverBuilder gameEventResolveUpdate() {
        logGameEventResolveUpdate = true;
        return this;
    }

    public LogObserverBuilder gameCrashUpdate() {
        logGameCrashUpdate = true;
        return this;
    }

    @Override
    public LogObserver build() {
        LogObserver observer = new LogObserver(this);
        reset();
        return observer;
    }
}
