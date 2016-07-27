package kg.nongrate.pcglib.objects;

import lombok.Getter;

/**
 * Abstract class as base class for tiles to use in agent trigger and action behavior
 */
public abstract class Tile {
    @Getter private int tileId;
    @Getter private int tileGroup;
    @Getter private String asset;

    public Tile() {
    }

    public Tile(int tileId) {
        this(tileId, null);
    }

    public Tile(int tileId, String asset) {
        this.tileId = tileId;
        this.asset = asset;
    }

    public Tile(int tileId, int tileGroup) {
        this(tileId, tileGroup, null);
    }

    public Tile(int tileId, int tileGroup, String asset) {
        this.tileId = tileId;
        this.tileGroup = tileGroup;
        this.asset = asset;
    }

    boolean tileCompare(Tile secondTile) {
        return tileId == secondTile.tileId;
    }

    boolean tileGroupCompare(Tile secondTile) {
        return tileGroup == secondTile.tileGroup;
    }
}
