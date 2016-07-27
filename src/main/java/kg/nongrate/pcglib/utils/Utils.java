package kg.nongrate.pcglib.utils;

import kg.nongrate.pcglib.Generator;
import kg.nongrate.pcglib.objects.Rectangle;

/**
 * Created by arseniii on 3/12/16.
 */
public final class Utils {
    public final static int[][] EMPTY_ARRAY = new int[0][0];

    public static int[][] trim(int[][] array) {
        if (array.length == 0) {
            return EMPTY_ARRAY;
        }
        return trimRows(trimColumns(array));
    }

    private static int[][] trimRows(int[][] array) {
        int top = 0, bottom = array[0].length - 1;
        // this loop goes until all rows are checked or an item found
        topLoop:
        while (true) {
            if (top == array.length) {
                // if all rows are processed and no items found - return zero-array
                return EMPTY_ARRAY;
            }
            for (int[] row : array) {
                if (row[top] != 0) {
                    break topLoop;
                }
            }
            ++top;
        }

        // this loop goes until all rows are checked or an item found
        bottomLoop:
        while (true) {
            if (bottom < 0) {
                // if all rows are processed and no items found - return zero-array
                return EMPTY_ARRAY;
            }
            for (int[] row : array) {
                if (row[bottom] != 0) {
                    break bottomLoop;
                }
            }
            --bottom;
        }

        int columnLength = bottom - top + 1;
        int[][] newArray = new int[array.length][columnLength];
        // Trim empty rows of the array
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], top, newArray[i], 0, columnLength);
        }

        return newArray;
    }

    private static int[][] trimColumns(int[][] array) {
        int left = 0, right = array.length - 1;
        // this loop goes until all rows are checked or an item found
        leftLoop:
        while (true) {
            if (left == array.length) {
                // if all columns are processed and no items found - return zero-array
                return EMPTY_ARRAY;
            }
            for (int item : array[left]) {
                if (item != 0) {
                    break leftLoop;
                }
            }
            ++left;
        }

        // this loop goes until all rows are checked or an item found
        rightLoop:
        while (true) {
            if (right < 0) {
                // if all columns are processed and no items found - return zero-array
                return EMPTY_ARRAY;
            }
            for (int item : array[right]) {
                if (item != 0) {
                    break rightLoop;
                }
            }
            --right;
        }

        int rowLength = right - left + 1;
        int[][] newArray = new int[rowLength][array.length];
        // Trim empty columns of the array
        System.arraycopy(array, left, newArray, 0, rowLength);

        return newArray;
    }

    public static Rectangle findRectangle(int[][] level) {
        Rectangle rectangle = new Rectangle();

        // searching of first non empty tile
        // columns
        boolean emptyArray = true;
        outerLoop:
        for (int i = 0; i < level.length; i++) {
            // items of each column
            for (int j = 0; j < level[i].length; j++) {
                if (level[i][j] != 0) {
                    rectangle.setLeft(i);
                    rectangle.setRight(i + 1);
                    rectangle.setTop(j);
                    rectangle.setBottom(j + 1);
                    emptyArray = false;
                    break outerLoop;
                }
            }
        }

        if (emptyArray) {
            return null;
        }

        // searching for last non empty tile
        boolean rightCheck = true, bottomCheck = true;
        int step = 0;
        for (int i = rectangle.getLeft(); i < level.length - 1; i++) {
            for (int j = rectangle.getTop(); j < level[i].length - 1; j++) {
                step++;
                //                System.err.println("STEP: " + step);
                //                System.err.println("rightCheck: " + rightCheck);
                //                System.err.println("bottomCheck: " + bottomCheck);
                //                System.err.println("Rectangle left: " + rectangle.getLeft());
                //                System.err.println("Rectangle top: " + rectangle.getTop());
                //                System.err.println("Rectangle bottom: " + rectangle.getBottom());
                //                System.err.println("Rectangle right: " + rectangle.getRight());

                if (!rightCheck && !bottomCheck) {
                    return rectangle;
                }

                rightCheck = rectangle.getRight() < level.length;
                if (rightCheck) {
                    // checking if next column is filled by this height
                    boolean verticalFilled = true;
                    for (int vertical = rectangle.getTop(); vertical < rectangle.getBottom(); vertical++) {
                        //                        System.out.println("vertical: " + vertical);
                        //                        System.err.println("level[" + (rectangle.getRight()) + "].length: " + level[rectangle.getRight()]
                        // .length);
                        //                        System.err.println("level[" + (rectangle.getRight()) + "][vertical]: " + level[rectangle.getRight
                        // ()][vertical]);
                        if (level[rectangle.getRight()][vertical] == 0) {
                            verticalFilled = false;
                            break;
                        }
                    }
                    if (verticalFilled) {
                        rectangle.setRight(rectangle.getRight() + 1);
                    } else {
                        rightCheck = false;
                    }
                }
                bottomCheck = rectangle.getBottom() < level[i].length;
                if (bottomCheck) {
                    // checking if next row is filled by this width
                    boolean horizontalFilled = true;
                    for (int horizontal = rectangle.getLeft(); horizontal < rectangle.getRight(); horizontal++) {
                        //                        System.err.println("horizontal: " + horizontal);
                        //                        System.err.println("level.length: " + level.length);
                        if (level[horizontal][rectangle.getBottom()] == 0) {
                            horizontalFilled = false;
                            break;
                        }
                    }
                    if (horizontalFilled) {
                        rectangle.setBottom(rectangle.getBottom() + 1);
                    } else {
                        bottomCheck = false;
                    }
                }
            }
            //            try {
            //                Thread.sleep(500);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
        }

        return null;
    }

    public static void printLevel() {
        printLevel(Generator.getLevel().getTileMatrix());
    }

    public static void printLevel(int[][] level) {
        for (int r2 = 0; r2 < level[0].length; r2++) {
            for (int c2 = 0; c2 < level.length; c2++) { System.out.print(level[c2][r2] + " "); }
            System.out.println();
        }
    }

    public static int[][] cloneArray(int[][] array) {
        int[][] myInt = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            myInt[i] = array[i].clone();
        }
        return myInt;
    }
}
