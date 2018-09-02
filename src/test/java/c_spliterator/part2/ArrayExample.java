package c_spliterator.part2;

import static java.util.Spliterator.CONCURRENT;
import static java.util.Spliterator.IMMUTABLE;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterator.SIZED;
import static java.util.Spliterator.SORTED;
import static java.util.Spliterator.SUBSIZED;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;

public class ArrayExample {
    public static class IntArraySpliterator extends Spliterators.AbstractIntSpliterator {

        private final int[] array;
        private int startInclusive;
        private final int endExclusive;

        public IntArraySpliterator(int[] array) {
            this(array, 0, array.length);
        }

        private IntArraySpliterator(int[] array, int startInclusive, int endExclusive) {
            super(endExclusive - startInclusive,
                    IMMUTABLE
                            | ORDERED
                            | SIZED
                            | SUBSIZED
                            | NONNULL);
            this.array = array;
            this.startInclusive = startInclusive;
            this.endExclusive = endExclusive;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (startInclusive >= endExclusive) {
                return false;
            }

            final int value = array[startInclusive];
            startInclusive += 1;
            action.accept(value);

            return true;
        }

        @Override
        public long estimateSize() {
            return endExclusive - startInclusive;
        }

        @Override
        public OfInt trySplit() {
            final int length = endExclusive - startInclusive;
            if (length < 2) {
                return null;
            }

            final int mid = startInclusive + length / 2;

            final IntArraySpliterator res = new IntArraySpliterator(array, startInclusive, mid);
            startInclusive = mid;
            return res;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            for (int i = startInclusive; i < endExclusive; i++) {
                action.accept(array[i]);
            }
            startInclusive = endExclusive;
        }
    }

    public static class OfIteratorExample {
        public static void main(String[] args) {

            ImmutableList<String> list = ImmutableList.of("1", "3", "2", "1", "3", "2", "1", "3", "8", "1", "3", "2", "1", "3", "2", "1", "3", "2");

            Spliterator<String> spliterator = Spliterators.spliterator(list, IMMUTABLE | SIZED | CONCURRENT);
            System.out.println(StreamSupport.stream(spliterator, true).max(String::compareTo));

            spliterator = Spliterators.spliterator(list.iterator(), list.size(), IMMUTABLE | SIZED | CONCURRENT);
            System.out.println(StreamSupport.stream(spliterator, true).max(String::compareTo));

            spliterator = Spliterators.spliterator(list.iterator(), list.size(), IMMUTABLE | SIZED | CONCURRENT | SORTED | ORDERED | CONCURRENT | SUBSIZED);
            System.out.println(StreamSupport.stream(spliterator, true).max(String::compareTo));

            spliterator = Spliterators.spliterator(list, IMMUTABLE | SIZED | CONCURRENT | SORTED | ORDERED | CONCURRENT);
            System.out.println(StreamSupport.stream(spliterator, true).max(String::compareTo));

            int[] arrayInt = new int[]{1, 3, 2, 1, 3, 2, 1, 3, 8, 1, 3, 2, 1, 3, 2, 1, 3, 2};

            Spliterator.OfInt spliteratorInt = Spliterators.spliterator(arrayInt, IMMUTABLE | SIZED | CONCURRENT);
            System.out.println(StreamSupport.intStream(spliteratorInt, true).max());

            spliteratorInt = Spliterators.spliterator(arrayInt, IMMUTABLE | SIZED | CONCURRENT | SORTED | ORDERED | CONCURRENT);
            System.out.println(StreamSupport.intStream(spliteratorInt, false).max());


        }

    }
}
