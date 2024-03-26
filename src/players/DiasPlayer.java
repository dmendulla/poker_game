package players;

import game.HandRanks;
import game.Player;

public class DiasPlayer extends Player {

    public DiasPlayer(String name) {
        super(name);
    }
// basic NPC code so it could operate as others
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
// I made my NPC to never fold, had to make him aggressive individual
    @Override
    protected boolean shouldFold() {
        return false;
    }
// just wrote a code to make sure my NPC wouldn't any illegal moves
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
    // my NPC will use call only if he doesn't have good deck of cards in his hands
    @Override
    protected boolean shouldCall() {
        boolean isBet = getGameState().isActiveBet();
        boolean lessThanTwoPairHand = evaluatePlayerHand().getValue() != HandRanks.TWO_PAIR.getValue();
        boolean lessThanThreeKindHand = evaluatePlayerHand().getValue() != HandRanks.THREE_OF_A_KIND.getValue();
        return isBet && lessThanTwoPairHand && lessThanThreeKindHand;
    }
// wanted to make my NPC to raise if he had pair, two or three
    @Override
    protected boolean shouldRaise() {
        boolean hasPairHand = evaluatePlayerHand().getValue() <= HandRanks.PAIR.getValue();
        boolean hasTwoPairHand = evaluatePlayerHand().getValue() <= HandRanks.TWO_PAIR.getValue();
        boolean hasThreeKindHand = evaluatePlayerHand().getValue() <= HandRanks.THREE_OF_A_KIND.getValue();
        return hasPairHand || hasTwoPairHand || hasThreeKindHand;
    }
// If my NPC have a better deck of cards such of those below, he will be complete savage
    @Override
    protected boolean shouldAllIn() {
        boolean fullHouse = evaluatePlayerHand().getValue() <= HandRanks.FULL_HOUSE.getValue();
        boolean fourKind = evaluatePlayerHand().getValue() <= HandRanks.FOUR_OF_A_KIND.getValue();
        boolean flush = evaluatePlayerHand().getValue() <= HandRanks.FLUSH.getValue();
        boolean royalFlush = evaluatePlayerHand().getValue() <= HandRanks.ROYAL_FLUSH.getValue();
        return fullHouse || fourKind || flush || royalFlush;
    }
}