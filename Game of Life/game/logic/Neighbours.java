package life.game.logic;

public enum Neighbours {
    NW(-1,-1), N(0,-1), NE(1,-1),
    W(-1,0), E(1,0),
    SW(-1,1), S(0, 1), SE(1,1);

    int x;
    int y;

    Neighbours(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
