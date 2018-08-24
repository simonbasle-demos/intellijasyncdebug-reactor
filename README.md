# intellijasyncdebug-reactor
exploring the async stacktrace feature of IntelliJ with Reactor


## `PublishOnTest`
Attempts at defining async stacktraces for scheduling is a bit too complicated, due to the number of possible execution model combinations.

egor from JetBrains offered this configuration, to be explored again:

```xml
<capture-points>
  <capture-point enabled="false" class-name="reactor.core.scheduler.Scheduler$Worker" method-name="schedule" capture-key-expression="param_0" insert-class-name="reactor.core.scheduler.WorkerTask" insert-method-name="call" insert-key-expression="task" />
</capture-points>
``` 

## `*DebuggingExample`
Async stacktraces may help during debugging when `Hooks.operatorDebugMode()` is activated.

These 2 configurations help by shortcircuiting the whole detail of the stacktrace back to the **assembly** point instead of the _subscription_ point :tada:

```xml
<capture-points>
  <capture-point enabled="false" class-name="reactor.core.publisher.Flux" method-name="onAssembly" capture-key-expression="param_0" insert-class-name="reactor.core.publisher.Flux" insert-method-name="subscribe" insert-key-expression="this" />
  <capture-point enabled="false" class-name="reactor.core.publisher.FluxOnAssembly" method-name="&lt;init&gt;" capture-key-expression="this" insert-class-name="reactor.core.publisher.FluxOnAssembly" insert-method-name="subscribe" insert-key-expression="param_1" />
</capture-points>
```

