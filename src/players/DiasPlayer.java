package players;

import game.HandRanks;
import game.GameState;
import game.PlayerActions;
import game.Player;

public class DiasPlayer extends Player {
    public DiasPlayer(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        if (shouldFold()) {
            fold();
        } else if (shouldCheck()) {
            check();
        } else if (shouldCall()) {
            call();
        } else if (shouldRaise()) {
            raise(getGameState().getTableMinBet()); // Example: always raises the minimum bet
        } else if (shouldAllIn()) {
            allIn();
        }
    }

    @Override
    protected boolean shouldFold() {
        if (isHandBelowThreshold()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean shouldCheck() {
        return evaluatePlayerHand().getValue() < HandRanks.HIGH_CARD.getValue();
    }
    @Override
    protected boolean shouldCall() {
        return getGameState().isActiveBet();
    }

    @Override
    protected boolean shouldRaise() {
        return evaluatePlayerHand().getValue() >= HandRanks.TWO_PAIR.getValue()
                || evaluatePlayerHand().getValue() >= HandRanks.THREE_OF_A_KIND.getValue();
    }

    @Override
    protected boolean shouldAllIn() {
        return evaluatePlayerHand().getValue() >= HandRanks.FULL_HOUSE.getValue()
                || evaluatePlayerHand().getValue() >= HandRanks.FOUR_OF_A_KIND.getValue()
                || evaluatePlayerHand().getValue() >= HandRanks.FLUSH.getValue()
                || evaluatePlayerHand().getValue() >= HandRanks.ROYAL_FLUSH.getValue();
    }


    private boolean isHandBelowThreshold() {
        // Example: Fold if the highest card is below 8
        return getHighestCardValue() < 8;
    }
        private int getHighestCardValue() {
            int highestValue = 0;
            for (game.Card card : getHandCards()) {
                int cardValue = card.getValue();
                if (cardValue > highestValue) {
                    highestValue = cardValue;
                }
            }
            return highestValue;
        }
}
