package net.thumbtack.school.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//10//11
@FunctionalInterface
interface MyFunction<T, R> {
    R apply(T arg1);
    //R apply(T arg1, T arg2);
}

public class FunctionsOperations {
    private static String STR = "there is something to do with";

    //1 задание
    public Function<String, List<String>> split1 = (String s) -> Arrays.asList(s.split(" "));
    public Function<List<?>, Integer> count1 = (List<?> l) -> l.size();

    //2 задание, декларацию типов в параметрах можно не указывать, потому что компилятор джава разрешает типы не указывать
    public Function<String, List<String>> split2 = s -> Arrays.asList(s.split(" "));
    public Function<List<?>, Integer> count2 = l -> l.size();

    //3 задание, замена вызова метода на ссылку метода возможна если метод не принимает параметров
    // или в методе теже аргументы, которые в лямбде
    public Function<String, List<String>> splitWithMethodReference = s -> Arrays.asList(s.split(" "));
    public Function<List<?>, Integer> countWithMethodReference = List::size;

    //4 задание. Отличие от count.apply(split.apply(str)) в том, что то что в скобочках будет выполнятся первым всегда
    public Function<String, Integer> splitAndCount = split1.andThen(count1);
    public Function<String, Integer> splitAndCount1 = count1.compose(split1);

    //5
    public Function<String, Person> create = s -> new Person(s);
    public Function<String, Person> create1 = Person::new;

    //6
    public BiFunction<Integer, Integer, Integer> max = Math::max;

    //7
    public Supplier<Date> getCurrentDate = Date::new;

    //8
    public Predicate<Integer> isEven = Objects::nonNull;

    //9
    public BiPredicate<Integer, Integer> areEqual = Integer::equals;

    //10
    public MyFunction<String, List<String>> split10 = (String s) -> Arrays.asList(s.split(" "));
    public MyFunction<List<?>, Integer> count10 = List::size;

    //12.1
    public Person getMothersMotherFather(Person person) {
        Predicate<Person> predicate = Objects::isNull;
        if (predicate.test(person) || predicate.test(person.getMother()) ) {return null;}
        return person.getMother().getFather();
    }

    Function<Person, Person> getMothersMotherFather = person ->
    {
        Predicate<Person> predicate = Objects::isNull;
        if (predicate.test(person) || predicate.test(person.getMother()) ) {return null;}
        return person.getMother().getFather();
    };

    //12.2
    public Function<PersonOp, Optional<PersonOp>> getMothersMotherFatherOptional =
            p -> Optional.ofNullable(p).isPresent() ? p.getMother().flatMap(PersonOp::getFather) : Optional.ofNullable(null);

    //13
    public IntStream transform(IntStream stream, IntUnaryOperator op) {
        stream.map(op).forEach(System.out::println);
        return stream;
    }

    BiFunction<IntStream, IntUnaryOperator, IntStream> transform = (stream, op) -> {
        stream.map(op).forEach(System.out::println);
        return stream;
    };

    //14 parallel() запускает обработку стрим в паралельных потоках, поэтому вывод неупорядоченный
    public IntStream transformParralel(IntStream stream, IntUnaryOperator op) {
        stream.map(op).parallel().forEach(System.out::println);
        return stream;
    }

    //15
    public List<String> getByAgeNameSortedByLength(List<Person> people) {
        return people.stream().filter(person -> person.getAge() > 30)
                .map(person -> person.getName()).distinct().sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    //16
//    List<String> getByAgeNameSortedByCount(List<Person> people) {
//        Map<String, List<Person>> y = people.stream().filter(person -> person.getAge() > 30)
//                .map(person -> person.getName()).sorted(String::compareTo)
//                .collect(Collectors.groupingBy(Person::getName));
//    }

    public List<String> getByAgeNameSortedByCount(List<Person> people) {
        return people.stream().filter(person -> person.getAge() > 30).sorted(Comparator.comparing(Person::getName))
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting())).keySet().stream().collect(Collectors.toList());
    }

    //17
    public Function<List<Integer>, Integer> sum = integers -> integers.stream().reduce((i, i2) -> i+i2).get();
    public Function<List<Integer>, Integer> product = integers -> integers.stream().reduce((i, i2) -> i*i2).get();

    public void doFunctionsOperations() {

        //1 задание
        System.out.println("1 задание");
        List<String> list1 = split1.apply(STR);
        System.out.println(list1);
        int sizeOfList1 = count1.apply(list1);
        System.out.println(sizeOfList1);

        //2 задание
        System.out.println("2 задание");
        List<String> list2 = split2.apply(STR);
        System.out.println(list2);
        int sizeOfList2 = count2.apply(list2);
        System.out.println(sizeOfList2);

        //3 задание
        System.out.println("3 задание");
        List<String> list3 = splitWithMethodReference.apply(STR);
        System.out.println(list3);
        int sizeOfList3 = countWithMethodReference.apply(list3);
        System.out.println(sizeOfList3);

        //4 задание
        System.out.println("4 задание");
        //Integer size1 = split1.andThen(count1).apply(STR);
        //Integer size2 = count1.compose(split1).apply(STR);
        Integer size3 = splitAndCount.apply(STR);
        Integer size4 = splitAndCount1.apply(STR);
        System.out.println((size3+size4)/2);

        //5 задание
        System.out.println("5 задание");
        Person person1 = create.apply("Constantinius");
        Person person2 = create1.apply("Constantinius");

        //6
        System.out.println("6 задание");
        max.apply(1, 4);

        //7
        System.out.println("7 задание");
        getCurrentDate.get();

        //8
        System.out.println("8 задание");
        isEven.test(5);

        //9
        System.out.println("9 задание");
        areEqual.test(4,7);

        //10
        System.out.println("10 задание");
        count10.apply(split10.apply(STR));
    }

}
