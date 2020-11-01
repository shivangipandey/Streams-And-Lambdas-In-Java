package practice.streamsAndLamdas;

import java.util.Arrays;
import java.util.List;

class Utility {

  static boolean isEven(int element) {
    return element % 2 == 0;
  }

  static int multiply(int element, int factor) {
    return element * factor;
  }

  static List<Person> createDummyPeople() {
    return Arrays.asList(
      new Person("Freddie Mercury", 22, Gender.MALE),
      new Person("Sara", 23, Gender.FEMALE),
      new Person("Bob", 24, Gender.MALE),
      new Person("Paula", 25, Gender.FEMALE),
      new Person("Chuck Berry", 26, Gender.FEMALE),
      new Person("Elvis Presley", 27, Gender.MALE),
      new Person("Jimi Hendrix", 28, Gender.FEMALE)
      );
  }

  static class Person {

    private String name;
    private int age;
    private Gender gender;

    Person(String name, int age, Gender gender) {
      this.name = name;
      this.age = age;
      this.gender = gender;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public Gender getGender() {
      return gender;
    }

    public void setGender(Gender gender) {
      this.gender = gender;
    }
  }

  static enum Gender {
    MALE,
    FEMALE;
  }
}
