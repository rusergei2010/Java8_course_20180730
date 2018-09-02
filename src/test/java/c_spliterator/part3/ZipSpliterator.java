package c_spliterator.part3;

import java.util.Iterator;
import java.util.Random;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.ImmutableList;

/**
 * Created by EE on 2018-02-05.
 */
public class ZipSpliterator<L, R, T> implements Spliterator<T>{
    private final Stream<L> names;
    private final Stream<R> numbers;
    private final Iterator<L> namesIterator;
    private final Iterator<R> numbersIterator;
    private final BiFunction<L, R, T> combiner;

    public ZipSpliterator(
        Stream<L> names,
        Stream<R> numbers,
        BiFunction<L, R, T> combiner) {
        this.names = names;
        this.numbers = numbers;
        namesIterator = names.iterator();
        numbersIterator = numbers.iterator();
        this.combiner = combiner;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (namesIterator.hasNext() && numbersIterator.hasNext()){
            final L name = namesIterator.next();
            final R number = numbersIterator.next();
            final T paired = combiner.apply(name, number);
            action.accept(paired);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return 0;
    }

    public static void main(String[] args) {
        final Stream<String> names = ImmutableList.of("Nikki", "Alex", "Andrew", "Gleb").stream();
        final Stream<Integer> numbers = new Random().ints(1, 5).boxed();

        zip(names, numbers, (s, i) -> s + " " + i.toString()).forEach(System.out::println);
        zip(IntStream.iterate(1, i -> i+1).boxed(),
            IntStream.iterate(1, i -> -i).boxed(),
            (i1, i2) -> i1 * i2).limit(20).forEach(System.out::println);
    }

    private static <L, R, T> Stream<T> zip(Stream<L> names, Stream<R> numbers, BiFunction<L, R ,T> zipper) {
        return StreamSupport.stream(new ZipSpliterator<>(names, numbers, zipper),false);
    }
}
