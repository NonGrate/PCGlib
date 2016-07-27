package kg.nongrate.pcglib;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kg.nongrate.pcglib.objects.Point;
import kg.nongrate.pcglib.objects.Rectangle;
import kg.nongrate.pcglib.utils.Utils;

/**
 * Object describing level of platform-type game
 */
public class Level {
    private int[][] tileMatrix;

    public int[][] getTileMatrix() {
        return tileMatrix;
    }

    public Level(int width, int height) {
        this.tileMatrix = new int[width][height];
    }

    // TODO: Change to second method call (DRY)
    public void set(int x, int y, int tileId) {
        if (x >= 0 && y >= 0 && x < tileMatrix.length && y < tileMatrix[0].length) {
            tileMatrix[x][y] = tileId;
        }
    }

    public void set(Point position, int tileId) {
        if (position.getX() >= 0 && position.getY() >= 0 && position.getX() < tileMatrix.length && position.getY() < tileMatrix[0].length) {
            tileMatrix[position.getX()][position.getY()] = tileId;
        }
    }

    public int[][] getTileMatrixById(int... tileIds) {
        List<Integer> list = new ArrayList<>();
        for (int tileId : tileIds) {
            list.add(tileId);
        }
        int[][] result = Utils.cloneArray(tileMatrix);
        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[0].length; y++) {
                if (result[x][y] != 0) {
                    if (list.indexOf(result[x][y]) == -1) {
                        result[x][y] = 0;
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<Rectangle> getTileGroups(int... tileIds) {
        int[][] level = getTileMatrixById(tileIds);
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        Rectangle foundRectangle;
        while ((foundRectangle = Utils.findRectangle(level)) != null) {
            System.out.println(String.format(Locale.getDefault(), "Rectangle. left: %d, top: %d, bottom: %d, right: %d", foundRectangle.getLeft(),
                    foundRectangle.getTop(), foundRectangle.getBottom(), foundRectangle.getRight()));
            rectangles.add(foundRectangle);
            for (int i = foundRectangle.getLeft(); i < foundRectangle.getRight(); i++) {
                for (int j = foundRectangle.getTop(); j < foundRectangle.getBottom(); j++) {
                    level[i][j] = 0;
                }
            }
        }
        return rectangles;
    }

    public Point getLevelSize() {
        if (tileMatrix.length > 0) {
            return new Point(tileMatrix.length, tileMatrix[0].length);
        }
        return new Point(0, 0);
    }
}
