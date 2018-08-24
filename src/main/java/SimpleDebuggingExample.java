import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Simon Baslé
 */
public class SimpleDebuggingExample {

    public static void main(String[] args) {
        //this cause additional steps to record assembly information, but also the number of stack frames increases per each operator applied.
        Hooks.onOperatorDebug();

        try {
            StringWriter sw = new StringWriter();

            //this operator actually delegates to Flux.just
            Flux.range(1,1)
                    //this operator uses the lambda, which causes the exception
                    //putting a breakpoint here, we'd like a smaller stacktrace in the debug view
                    .map(v -> {throw new IllegalStateException("boom" + v);})
                    .subscribe(System.out::println,
                            t -> t.printStackTrace(new PrintWriter(sw))
                    );

            String debugStack = sw.toString();
            System.out.println(debugStack);
        }
        finally {
            Hooks.resetOnOperatorDebug();
        }
    }
}
