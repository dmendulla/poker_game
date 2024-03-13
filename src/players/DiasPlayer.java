package players;

import game.Player;

public class DiasPlayer extends Player {
    public DiasPlayer(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        if(getGameState().isActiveBet()) {
            if(getGameState().getNumRoundStage() == 0) {
                // i know there is a bet pre flop
            }
        }
    }

    @Override
    protected boolean shouldFold() {
        return true;
    }

    @Override
    protected boolean shouldCheck() {
        return false;
    }

    @Override
    protected boolean shouldCall() {
        return false;
    }

    @Override
    protected boolean shouldRaise() {
        return false;
    }

    @Override
    protected boolean shouldAllIn() {
        return false;
    }
}
