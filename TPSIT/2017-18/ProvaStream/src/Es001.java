
import java.util.List;
import java.util.Arrays;
import java.util.stream.*;

public class Es001
{

    // stream inherited from Collection
    public static void main(String[] args)
    {
        System.out.println("\n ** 1 ** \n");
        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        System.out.println("\n ** 2 ** \n");
        Arrays.asList("a1", "a2", "a3")
                .stream()
                // Returns an Optional describing the first element of this stream, or an empty Optional if the stream is empty.
                .findFirst()
                // If a value is present, invoke the specified consumer with the value, otherwise do nothing.
                .ifPresent(System.out::println);

        System.out.println("\n ** 3 ** \n");
        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("\n ** 4 ** \n");
        IntStream.range(1, 5)
                .forEach(System.out::println);

        System.out.println("\n ** 5 ** \n");
        Arrays.stream(new int[]
        {
            1, 2, 3
        })
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);

        System.out.println("\n ** 6 ** \n");
        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

    }
}
