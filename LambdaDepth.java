package practice.streamsAndLamdas;

import java.util.Arrays;
import java.util.List;

/**
 * @Lambda - Lambdas are cute little anonymous functions
 *         - It can be used with any interface where a single abstract method exist
 *
 */
public class LambdaDepth {

  public static void commonExamples() {

    List<Integer> numbers = Arrays.asList(2,2,6,3,3,5,5);

    //Print all the elements
    numbers.forEach(System.out::print);

    //Convert all to string and then print
    numbers.stream()
      .map(String::valueOf)
      .forEach(element -> System.out.print(element+" "));

    //XOR All the elements
    System.out.print(
      numbers.stream()
        .reduce(0, ((totalXor, element) -> totalXor ^ element))
    );

    //Sum All the elements
    System.out.print(
      numbers.stream()
        .reduce(0, Integer::sum)
    );

    //Print by formatting the string

    System.out.print(
    numbers
      .stream()
      .map(String::valueOf)
      .reduce("START-", (carryString, elementString) -> carryString.concat(elementString)+"-")
      //.reduce("START-", String::concat)
    );

    //Sum even numbers
    sumEvenNumbers(numbers);

    //Sum of double of even numbers
    sumOfDoubleOfEvenNumbers(numbers);
  }

  private static int sumOfDoubleOfEvenNumbers(List<Integer> numbers) {

    return numbers
      .stream()
      //.filter(element -> isEven(element))
      .filter(Utility::isEven)
      .mapToInt(element -> Utility.multiply(element, 2))
      .sum(); // specialized reduce

     /*

    Method 2 :

    return numbers
      .stream()
      .filter(element -> element % 2 == 0)
      //.reduce(0, (carryInteger, element) -> (carryInteger + (element*2)));
      .map(element -> element * 2)
      .reduce(0, Integer::sum);

     */

  }

  private static int sumEvenNumbers(List<Integer> numbers) {

    return numbers
      .stream()
      .filter(element -> element % 2 == 0) //It takes predicate (needs to be a boolean statement)
      .reduce(0, Integer::sum);

    /*

    Expanding :

    return numbers
      .stream()
      .filter(new Predicate<Integer>() {
        @Override
        public boolean test(Integer integer) {
          return integer % 2 == 0;
        }
      })
      .reduce(0, new BinaryOperator<Integer>() {
        @Override
        public Integer apply(Integer integer, Integer integer2) {
          return integer + integer2;
        }
      });
     */

  }
}
