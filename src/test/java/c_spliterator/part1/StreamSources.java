package c_spliterator.part1;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class StreamSources {

    @Test
    public void empty() {
        Stream<String> stream = Stream.empty();

        assertEquals(Collections.emptyList(), stream.collect(toList()));
    }

    @Test
    public void of() {
        List<String> list = Stream.of("John")
                .collect(toList());

        assertEquals(ImmutableList.of("John"), list);


        list = Stream.of("John", "Bob")
                .collect(toList());

        assertEquals(ImmutableList.of("John", "Bob"), list);


        String[] strings = {"John", "Bob"};
        list = Stream.of(strings)
                .collect(toList());

        assertEquals(ImmutableList.of("John", "Bob"), list);
    }


    @Test
    public void builder() {
        Stream<Object> objectStream = Stream.builder()
                .add("John")
                .add("Bob")
                .build();

        List<Object> objectList = objectStream.collect(toList());

        assertEquals(ImmutableList.of("John", "Bob"), objectList);


        Stream<String> stringStream = Stream.<String>builder()
                .add("John")
                .add("Bob")
                .build();

        List<String> stringList = stringStream.collect(toList());

        assertEquals(ImmutableList.of("John", "Bob"), stringList);
    }

    @Test
    public void range() {
        IntStream oneToFive = IntStream.range(1, 6);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, oneToFive.toArray());


        oneToFive = IntStream.rangeClosed(1, 5);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, oneToFive.toArray());
    }

    @Test
    public void viaFunctions() {
        Stream<String> stream = Stream.generate(() -> "John");

        assertEquals(ImmutableList.of("John", "John", "John", "John", "John"), stream.limit(5).collect(toList()));


        stream = Stream.iterate("John", s -> s + "1");

        assertEquals(ImmutableList.of("John", "John1", "John11", "John111", "John1111"), stream.limit(5).collect(toList()));


        IntStream intStream = IntStream.iterate(5, i -> i * 2);

        assertArrayEquals(new int[]{5, 10, 20, 40, 80}, intStream.limit(5).toArray());
    }

    @Test
    public void random() {
        DoubleStream randomDoubles = DoubleStream.generate(Math::random);

        System.out.println("randomDoubles.limit(5).collect(toList()) = " + randomDoubles.limit(5).boxed().collect(toList()));


        IntStream randomInts = new Random().ints();

        System.out.println("randomInts.limit(5).collect(toList()) = " + randomInts.limit(5).boxed().collect(toList()));

    }

    @Test
    public void collections() {
        int[] ints = {5, 10, 20, 40, 80};

        assertArrayEquals(ints, Arrays.stream(ints).toArray());


        Set<String> strings = ImmutableSet.of("John", "Bob");

        Stream<String> sequentialStream = strings.stream();
        assertEquals(strings, sequentialStream.collect(toSet()));

        Stream<String> parallelStream = strings.parallelStream();
        assertEquals(strings, parallelStream.collect(toSet()));
    }

    @Test
    public void string() {
        String packageName = "part3.exercise.johnny.guitar";
        StringWriter sw = new StringWriter();

        IntStream charsStream = packageName.chars();
        charsStream
                .filter(c -> Character.isDigit((char) c))
                .forEachOrdered(sw::write);

        assertEquals("3", sw.toString());

        IntStream codePointsStream = "part3.exercise.johnny.guitar".codePoints();
        String string = codePointsStream
                .filter(codePoint -> !Character.isDigit(codePoint))
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                )
                .toString();

        assertEquals("part.exercise.johnny.guitar", string);

    }

    @Test
    public void pattern() {
        String packageName = "part3.exercise.johnny.guitar";
        List<String> packages = Pattern.compile("\\.")
                .splitAsStream(packageName)
                .collect(toList());

        assertEquals(ImmutableList.of("part3", "exercise", "johnny", "guitar"), packages);
    }

    @Test
    public void lines() {
        Path path = Paths.get("sample", "part1", "example", "ArrayExample.java");
        try (Stream<String> lines = Files.lines(path)) {
            List<String> fiveLines = lines.limit(5)
                    .collect(toList());
            assertEquals(
                    ImmutableList.of("package spliterators.part1.example;",
                            "",
                            "import java.util.Spliterator;",
                            "import java.util.Spliterators;",
                            "import java.util.function.IntConsumer;"),
                    fiveLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void files() {
        Path path = Paths.get("sample");

        try (Stream<Path> fileTree = Files.walk(path)) {
            List<Path> paths = fileTree
                    .collect(toList());
            assertEquals(
                    ImmutableList.of(
                            Paths.get("sample"),
                            Paths.get("sample", "example"),
                            Paths.get("sample", "example", "StreamSources.java"),
                            Paths.get("sample", "part1"),
                            Paths.get("sample", "part1", "example"),
                            Paths.get("sample", "part1", "example", "ArrayExample.java"),
                            Paths.get("sample", "part1", "example", "ArrayIntBenchmark.java"),
                            Paths.get("sample", "part1", "example", "RandomIntegers.java"),
                            Paths.get("sample", "part1", "exercise"),
                            Paths.get("sample", "part1", "exercise", "RectangleSpliterator.java"),
                            Paths.get("sample", "part1", "exercise", "RectangleSpliteratorExercise.java")
                    ),
                    paths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
