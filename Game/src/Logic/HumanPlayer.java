package Logic;

/**
 * Created by f00 on 23.03.16.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, Token player, int numPiecesPerPlayer) throws GameException {
        super(player, numPiecesPerPlayer);
        this.name = name;
    }

    @Override
    public boolean isAI() {
        return false;
    }
}