package practice.streamsAndLamdas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Streams :
 * The most interesting part about stream is that, it's just an abstraction. It does'nt contains any data. List contains data maps
 * contains data but streams does not. It's consist of function composition or pipeline of functions that enable the
 * flow of data through it.
 *
 * @Performance :
 * Streams are easy to work with, readable, expressive, concise cute but PERFORMANCE ???
 * - Stream's 'lazy evaluation' :
 *   -> streams are lazy
 *   -> 2 operations available - 'intermediate operations' and 'termination operations'
 *   -> intermediate operations : - Operations that are postponed for evaluation
 *                                - For all the intermediate operations, stream just add these functions and pipeline and does not actually do any work.
 *                                - like - filter, map
 *   -> termination operation : - Once termination function is called, stream then triggers the pipeline functions
 *                              - like - sum, findFirst, reduce, collect
 *   -> Instead of taking each function in pipeline and applying it over each element in the list one at a time, stream takes
 *      one element and applies as much as function in the pipeline it can on that element. And only when operations on
 *      that one element is completed, stream take the second element and so on.
 *   -> Hence, the computational complexity will exactly be the same as imperative approach
 *   -> LAZY EVALUATION'S ARE ONLY POSSIBLE IF THE FUNCTIONS DON'T HAVE ANY SIDE EFFECTS
 *   -> Every function that returns a stream from a stream is lazy.
 * - Every single function in streams is cohesive : responsible for only task assign to that.
 * @Characteristics :
 * -> SIZED : Streams can be sized or unsized (boundless | without any limits)
 * -> ORDERED : Streams can be ordered or unordered
 * -> Distinct : Streams can be distinct or non-distinct
 * -> SORTED : Streams could be sorted or unsorted
 *
 * - - Check the source of stream : if it's sized, stream is bound to be sized
 * - Eg - list.stream() -> sized, ordered, non-distinct, non-sorted
 * - Original source dictates the properties, but we can change the properties along the way.
 *
 * @Infinite-Streams : check method - infiniteStream()
 *
 */
public class StreamsDepth {

  /**
   * Block some of the data and let others go through depending on the lambda you provided | or the data that you put in the
   * test method of the Predicate functional interface.
   *
   * Parameter : filter of stream<T> takes input as Predicate<T>
   *
   * - It keeps focus on it's swimlanes | handling one element at a time and not looking right or left on any other element
   * @param numbers
   */
  private void filter(List<Integer> numbers) {

    numbers
      .stream()
      .filter(new Predicate<Integer>() {
        @Override
        public boolean test(Integer e) {
          return e % 2 == 0;
        }
      });

    numbers
      .stream()
      .filter(e -> e % 2 == 0);

    numbers
      .stream()
      .filter(Utility::isEven);
  }

  /**
   * map() -> transforming function
   * - It transforms the data coming from stream into another stream
   * - number of output == number of input
   * - No gurantee on the type of output with respect to the type of output
   * - Parameter : Stream<T> map takes Function<T, R> to return Stream<R>
   *
   * - It keeps focus on it's swimlanes | handling one element at a time and not looking right or left on any other element
   * @param numbers
   */
  private void map(List<Integer> numbers) {

    numbers
      .stream()
      .map(new Function<Integer, Object>() {
        @Override
        public Object apply(Integer integer) {
          return String.valueOf(integer);
        }
      });

    numbers
      .stream()
      .map(String::valueOf); // Returns streams transforming integer to String

    numbers
      .stream()
      .map(Utility::isEven); //Returns streams of true or false

    numbers
      .stream()
      .map(e -> Utility.multiply(e, 2)); //Returns streams by doubling each element

  }

  /*
    reduce() - cuts across the swimlanes | will start from the first node
    - it takes an initialization value. Then takes the value of the first element, performs some operation and store that result in
    a variable and carry it forward to further elements.

    - reduce on stream<T> takes two parameters :
      -> 1. parameter of type T
      -> 2. second Parameter is of type BiFunction <R, T, R> to produce a result of R
    - reduce brings values together whereas filter and map just moves value forward or transforms them
    - reduce may transforms a stream in a single value or it can transform it to a non stream/concrete type
    - there are some specialized reduce also like sum, max etc.
   */
  private void reduce(List<Integer> numbers) {

    numbers
      .stream()
      .reduce(0, Utility::multiply);

    numbers
      .stream()
      .mapToInt(Integer::valueOf)
      .sum();

  }

  private void optionalUtility(List<Integer> numbers) {

    //Given an ordered list, find the double of the first even number greater than 3

    Optional<Integer> integerOptional = numbers
      .stream()
      .filter(e -> e > 3)
      .filter(Utility::isEven)
      .map(e -> Utility.multiply(e, 2))
      .findFirst();

    if (integerOptional.isPresent()) {
      Integer firstNumber = integerOptional.get();
    }
  }

  /**
   * Just because you can parallelize, doesn't mean you should
   * @param numbers
   */
  private void parallelStream(List<Integer> numbers) {

  }

  /*
    - unsized Stream | boundless Stream
    - Intermediate operation | gives a pipeline head, you need to provide a termination operation to actually execute it
    -
   */
  private void infiniteStream() {

    //Start with 100
    //Create a series 100, 101, 102.....

    Stream.iterate(100, new UnaryOperator<Integer>() {
      @Override
      public Integer apply(Integer integer) {
        return integer + 1;
      }
    });

    Stream.iterate(100, e -> e + 1);

    /*
      Problem : given a number k and a count n, find the Sum of double of n even numbers starting from k,
      where sqrt of each number is greater than 20.
     */

    int k = 40, n = 100;

    int sum = Stream
              .iterate(k, e -> e + 1)
              .filter(Utility::isEven)
              .filter(e -> Math.sqrt(e) > 20)
              .limit(n)
              .mapToInt(e -> Utility.multiply(e, 2))
              .sum();
  }

  public static class CollectDepth {

    /**
     * - Collect is a reduce operation as well.
     * @param numbers
     */
    private void collectToList(List<Integer> numbers) {

      //double the even values and put that to a list

      Stream<Integer> streamOfEvenValues = numbers
        .stream()
        .filter(Utility::isEven)
        .map(e -> Utility.multiply(e, 2));

      // wrong way of doing this
      //mutability is okay, sharing is nice, shared mutability is devils work
      List<Integer> doubleOfEven = new ArrayList<>();
      streamOfEvenValues
        .forEach(doubleOfEven::add);

      //Write way
      doubleOfEven = streamOfEvenValues
        .collect(Collectors.toList());
    }

    private void collectWithMap() {
      //Convert to a map

      List<Utility.Person> people = Utility.createDummyPeople();

      //Create a map with name and age as a key and person as value
      people
        .stream()
        .collect(Collectors.toMap(
          person -> person.getName() + "-" + person.getAge(),
          person -> person
        ));

      people
        .stream()
        .collect(Collectors.toMap(
          new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
              Utility.Person person = (Utility.Person) o;
              return person.getName() + "-" + person.getAge();
            }
          },
          new Function<Utility.Person, Object>() {
            @Override
            public Object apply(Utility.Person person) {
              return person;
            }
          }
        ));
    }

    private void collectWithGroupingBy() {

      //***Grouping***!

      //Create a map of people with key as gender and values as all the person with that gender

      List<Utility.Person> people = Utility.createDummyPeople();

      Map<Utility.Gender, List<Utility.Person>> genderListMap = people
        .stream()
        .collect(
          Collectors.groupingBy(person -> person.getGender())
        );

      //***Grouping And Mapping By***!

      //Create a map of people with key as gender and values as the ages of the person with that gender
      Map<Utility.Gender, List<Integer>> genderWithAgesMap = people
        .stream()
        .collect(
          Collectors
            .groupingBy(
              Utility.Person::getGender,
              Collectors.mapping(person -> person.getAge(), Collectors.toList())) // Mapping map the values of the group result to a suitable collector
        );

    }


  }
}
