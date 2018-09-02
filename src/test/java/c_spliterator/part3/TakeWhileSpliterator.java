package c_spliterator.part3;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;

/**
 * Created by EE on 2018-02-06.
 */
public class TakeWhileSpliterator<T> implements Spliterator<T> {
    private final Stream<T> innerStream;
    private Predicate<T> predicate;
    private final Spliterator<T> innerSpliterator;
    private final List<T> container = new ArrayList<T>(1);
    private boolean finished = false;

    public TakeWhileSpliterator(Stream<T> innerStream, Predicate<T> predicate) {
        this.innerStream = innerStream;
        this.predicate = predicate;
        innerSpliterator = innerStream.spliterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (finished) {
            return false;
        }
        final boolean innerAdvanced = innerSpliterator.tryAdvance(container::add);
        if (innerAdvanced) {
            T t = container.remove(0);
            final boolean predicateResult = predicate.test(t);
            if (predicateResult) {
                action.accept(t);
                return true;
            } else {
                finished = true;
            }
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        final Spliterator<T> innerSubSpliterator = innerSpliterator.trySplit();

        final TakeWhileSpliterator subSpliterator = new TakeWhileSpliterator(
            StreamSupport.stream(innerSubSpliterator, false),
            predicate
        );

        predicate = predicate.and(t -> !subSpliterator.finished);

        return subSpliterator;
    }

    @Override
    public long estimateSize() {
        return innerSpliterator.estimateSize();
    }

    @Override
    public int characteristics() {
        return innerSpliterator.characteristics();
    }


    public static void main(String[] args) {
        Stream<Integer> integerStream = ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).stream();

        takeWhile4(integerStream, i -> i % 9 != 0).forEach(System.out::println);
    }

    public static <T> Stream takeWhile(Stream<T> innerStream, Predicate<T> predicate) {
        return StreamSupport.stream(
            new TakeWhileSpliterator(innerStream, predicate), false
        );
    }

    public static <T> Stream takeWhile2(Stream<T> innerStream, Predicate<T> predicate) {
        final TakeWhileSpliterator spliterator1 = new TakeWhileSpliterator(innerStream, predicate);
        final Spliterator spliterator2 = spliterator1.trySplit();

        return Stream.of(spliterator2, spliterator1)
            .flatMap(spl -> StreamSupport.stream(spl, false));
    }

    public static <T> Stream takeWhile4(Stream<T> innerStream, Predicate<T> predicate) {
        final TakeWhileSpliterator spliteratorRR = new TakeWhileSpliterator(innerStream, predicate);
        final Spliterator spliteratorLR = spliteratorRR.trySplit();
        final Spliterator spliteratorRL = spliteratorRR.trySplit();
        final Spliterator spliteratorLL = spliteratorLR.trySplit();

        return Stream.of(spliteratorLL, spliteratorLR, spliteratorRL, spliteratorRR)
            .flatMap(spl -> StreamSupport.stream(spl, false));
    }
}
