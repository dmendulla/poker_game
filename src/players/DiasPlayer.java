package players;

import game.HandRanks;
import game.Player;

public class DiasPlayer extends Player {

    public DiasPlayer(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        //printExampleStateInformation();
        if(getGameState().isActiveBet()) {
            if (getGameState().getNumRoundStage() == 0) {
                // I know there is a bet pre-flop
            }
        }
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
        return false;
    }

    @Override
    protected boolean shouldCheck() {
        boolean noBet = !getGameState().isActiveBet();
        boolean lessThanPair = evaluatePlayerHand().getValue() != HandRanks.PAIR.getValue();
        boolean lessThanTwoPairHand = evaluatePlayerHand().getValue() != HandRanks.TWO_PAIR.getValue();
        boolean lessThanThreeKindHand = evaluatePlayerHand().getValue() != HandRanks.THREE_OF_A_KIND.getValue();
        boolean lessThanStraightHand = evaluatePlayerHand().getValue() != HandRanks.STRAIGHT.getValue();
        boolean lessThanFlushHand = evaluatePlayerHand().getValue() != HandRanks.FLUSH.getValue();
        return noBet && lessThanPair && lessThanTwoPairHand && lessThanThreeKindHand && lessThanStraightHand && lessThanFlushHand;
    }
    @Override
    protected boolean shouldCall() {
        boolean isBet = getGameState().isActiveBet();
        boolean lessThanTwoPairHand = evaluatePlayerHand().getValue() != HandRanks.TWO_PAIR.getValue();
        boolean lessThanThreeKindHand = evaluatePlayerHand().getValue() != HandRanks.THREE_OF_A_KIND.getValue();
        return isBet && lessThanTwoPairHand && lessThanThreeKindHand;
    }

    @Override
    protected boolean shouldRaise() {
        boolean hasPairHand = evaluatePlayerHand().getValue() <= HandRanks.PAIR.getValue();
        boolean hasTwoPairHand = evaluatePlayerHand().getValue() <= HandRanks.TWO_PAIR.getValue();
        boolean hasThreeKindHand = evaluatePlayerHand().getValue() <= HandRanks.THREE_OF_A_KIND.getValue();
        return hasPairHand || hasTwoPairHand || hasThreeKindHand;
    }

    @Override
    protected boolean shouldAllIn() {
        boolean fullHouse = evaluatePlayerHand().getValue() <= HandRanks.FULL_HOUSE.getValue();
        boolean fourKind = evaluatePlayerHand().getValue() <= HandRanks.FOUR_OF_A_KIND.getValue();
        boolean flush = evaluatePlayerHand().getValue() <= HandRanks.FLUSH.getValue();
        boolean royalFlush = evaluatePlayerHand().getValue() <= HandRanks.ROYAL_FLUSH.getValue();
        return fullHouse || fourKind || flush || royalFlush;
    }
}