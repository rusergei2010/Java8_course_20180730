package b_streams.part2;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import b_streams.data.Person;

public class CollectorsExample2 {

    private static Stream<Person> getPersonStream() {
        return Stream.of(
                new Person("a", "a", 1),
                new Person("b", "b", 2),
                new Person("c", "c", 3),
                new Person("d", "d", 4),
                new Person("e", "e", 5)
        );
    }

    @Test
    public void testInt() {

        int sumAge = getPersonStream()
                .mapToInt(Person::getAge)
                .sum();
        System.out.println("sumAge = " + sumAge);

        OptionalDouble average = getPersonStream()
                .mapToInt(Person::getAge)
                .average();
        System.out.println("average = " + average);

        IntSummaryStatistics intSummaryStatistics = getPersonStream()
                .mapToInt(Person::getAge)
                .summaryStatistics();
        System.out.println("intSummaryStatistics = " + intSummaryStatistics);

    }

    public void testCollection() {
        Object[] objectsPerson = getPersonStream().toArray();
        System.out.println("objectsPerson = " + Arrays.toString(objectsPerson));


        Person[] people = getPersonStream().toArray(size -> new Person[size]);
        System.out.println("people = " + Arrays.toString(people));


        List<Person> personList =
                getPersonStream().collect(Collectors.toList());
        System.out.println("personList = " + personList);


        Set<Person> personSet =
                getPersonStream().collect(Collectors.toSet());
        System.out.println("personSet = " + personSet);


        LinkedHashSet<Person> personLinkedHashSet =
                getPersonStream().collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
        System.out.println("personLinkedHashSet = " + personLinkedHashSet);


        Map<String, String> map =
                getPersonStream().collect(Collectors.toMap(
                        Person::getFirstName,
                        Person::getLastName
                ));
        System.out.println("map = " + map);

        map =
                getPersonStream().collect(Collectors.toMap(
                        Person::getFirstName,
                        Person::getLastName,
                        (lastName1, lastName2) -> lastName1 + lastName2
                ));
        System.out.println("map = " + map);

        map =
                getPersonStream().collect(Collectors.toMap(
                        Person::getFirstName,
                        Person::getLastName,
                        (lastName1, lastName2) -> lastName1 + lastName2,
                        () -> new LinkedHashMap<>()
                ));
        System.out.println("map = " + map);


    }

    @Test
    public void testMapping() {
        Set<String> set =
                getPersonStream().collect(Collectors.mapping(
                        Person::getFirstName,
                        Collectors.toSet()
                        )
                );

        System.out.println("set = " + set);
    }

    @Test
    public void testGrouping() {
        Map<Integer, List<Person>> map1 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3
                ));
        System.out.println("map1 = " + map1);

        Map<Integer, Set<Person>> map2 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3,
                        Collectors.toSet()
                ));
        System.out.println("map2 = " + map2);

        Map<Integer, Set<String>> map3 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3,
                        Collectors.mapping(
                                Person::getFirstName,
                                Collectors.toSet()
                        )
                ));
        System.out.println("map3 = " + map3);

        TreeMap<Integer, Set<String>> map4 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3,
                        TreeMap::new,
                        Collectors.mapping(
                                Person::getFirstName,
                                Collectors.toSet()
                        )
                ));
        System.out.println("map4 = " + map4);
    }

    @Test
    public void testPartitioning() {
        Map<Boolean, List<Person>> map1 =
                getPersonStream().collect(Collectors.partitioningBy(
                        p -> p.getAge() > 3
                ));
        System.out.println("map1 = " + map1);

        Map<Boolean, Set<String>> map2 =
                getPersonStream().collect(Collectors.partitioningBy(
                        p -> p.getAge() > 3,
                        Collectors.mapping(
                                Person::getFirstName,
                                Collectors.toSet()
                        )
                ));
        System.out.println("map2 = " + map2);
    }


    @Test
    public void testAndThen() {

        ImmutableList<Person> persons =
                getPersonStream().collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> ImmutableList.copyOf(list)
                ));
        System.out.println("persons = " + persons);
    }

    @Test
    public void testOdd() {

        Map<Integer, Long> map1 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3,
                        Collectors.mapping(
                                Person::getFirstName,
                                Collectors.counting()
                        )
                ));
        System.out.println("map1 = " + map1);

        Map<Integer, Double> map2 = getPersonStream().collect(Collectors.groupingBy(
                p -> p.getAge() % 3,
                Collectors.mapping(
                        Person::getFirstName,
                        Collectors.averagingInt(String::length)
                )
        ));
        System.out.println("map2 = " + map2);

        Map<Integer, Optional<String>> map3 =
                getPersonStream().collect(Collectors.groupingBy(
                        p -> p.getAge() % 3,
                        Collectors.mapping(
                                Person::getFirstName,
                                Collectors.reducing((s1, s2) -> s1 + " " + s2)
                        )
                ));
        System.out.println("map3 = " + map3);
    }

}
