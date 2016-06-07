package org.hv.reversi.game.api;

public enum Color {
    BLACK,
    WHITE;
    
    public Color opposite() {
        if (this.equals(BLACK)) {
            return WHITE;
        }
        return Color.BLACK;
    }
}
