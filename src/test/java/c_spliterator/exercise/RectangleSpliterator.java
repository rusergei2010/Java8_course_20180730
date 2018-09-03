package c_spliterator.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] array;

    private int startInclusiveRaw;
    private int endExclusiveRaw;
    private int startInclusiveCol;
    private int currentCol;
    private int endExclusiveCol;

    public RectangleSpliterator(int[][] array) {
        //TODO
        this(array, 0, array.length, 0, 0, array[0].length);
    }

    private RectangleSpliterator(int[][] array, int startInclusiveRaw, int endExclusiveRaw, int startInclusiveCol,
                                 int currentCol, int endExclusiveCol) {

        super((endExclusiveRaw - startInclusiveRaw) * (endExclusiveCol - startInclusiveCol),
                Spliterator.IMMUTABLE
                        | Spliterator.ORDERED
                        | Spliterator.SIZED
                        | Spliterator.SUBSIZED
                        | Spliterator.NONNULL);

        this.array = array;
        this.startInclusiveRaw = startInclusiveRaw;
        this.endExclusiveRaw = endExclusiveRaw;
        this.startInclusiveCol = startInclusiveCol;
        this.currentCol = currentCol;
        this.endExclusiveCol = endExclusiveCol;
    }

    @Override
    public RectangleSpliterator trySplit() {

        // TODO
        int raws = endExclusiveRaw - startInclusiveRaw;
        int cols = endExclusiveCol - startInclusiveCol;

        if (raws < 2 && cols < 2) {
            return null;
        }

        final RectangleSpliterator currentResult;
        int mid;

        if (raws >= cols) {

            mid = startInclusiveRaw + raws / 2;
            currentResult = new RectangleSpliterator(array, startInclusiveRaw, mid, startInclusiveCol,
                    currentCol, endExclusiveCol);

            startInclusiveRaw = mid;
            currentCol = startInclusiveCol;
        } else {
            mid = startInclusiveCol + cols / 2;

            if (currentCol >= mid) {
                currentResult = new RectangleSpliterator(array, ++startInclusiveRaw, endExclusiveRaw,
                        startInclusiveCol, startInclusiveCol, mid);
            } else {
                currentResult = new RectangleSpliterator(array, startInclusiveRaw, endExclusiveRaw, startInclusiveCol,
                        currentCol, mid);

                currentCol = mid;
            }

            startInclusiveCol = mid;
        }

        return currentResult;
    }

    @Override
    public long estimateSize() {
        //TODO
        return (endExclusiveRaw - startInclusiveRaw - 1) * endExclusiveCol + (endExclusiveCol - currentCol);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {

        // TODO
        if (startInclusiveRaw < endExclusiveRaw) {
            action.accept(array[startInclusiveRaw][currentCol]);

            ++currentCol;
            if (currentCol == endExclusiveCol) {
                ++startInclusiveRaw;
                currentCol = startInclusiveCol;
            }

            return true;
        } else {
            return false;
        }
    }
}