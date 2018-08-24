import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Simon Baslé
 */
public class ComplexDebuggingExample {

    public static void main(String[] args) {
        //this cause additional steps to record assembly information, but also the number of stack frames increases per each operator applied.
        Hooks.onOperatorDebug();
        try {
            Flux<Integer> source;
            if ((System.currentTimeMillis() / 1000) % 2 == 0) {
                source = Flux.range(1, 3)
                        .map(i -> (i == 3) ? null : i);
            }
            else {
                source = Flux.range(1, 4)
                        .map(i -> (i == 4) ? null : i);
            }
            StringWriter sw = new StringWriter();

            source.map(i -> "value " + i)
                  .subscribe(System.out::println, t -> t.printStackTrace(new PrintWriter(sw))
            );

            String debugStack = sw.toString();
            System.out.println(debugStack);
        }
        finally {
            Hooks.resetOnOperatorDebug();
        }
    }
}
