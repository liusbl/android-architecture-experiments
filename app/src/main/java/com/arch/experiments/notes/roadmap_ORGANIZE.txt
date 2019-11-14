What should be considered.

Stage 1:
- Creation

- Initialization

- Logging

- Consider that there are things that you only set once

- Check for leaks, proper object disposing

- Proper naming

- Make lots of possible lifecycle scenarios, since this is a difficult problem

- No memory leaks

- Documentation

- Code samples

- Actually publish the library

- (internal) Cover with CrossLinked and MultiLinked with Unit Tests

- Solution for data that is stored in controller in one way and
    displayed a little bit differently (such as dates, currency, ask Martynas for example)

- Medium or similar posts

- Core state machine

- Generic lifecycle handling

- Android lifecycle handling

- Integration with Android ViewModel

- Middlewares for logging or storage

- Unit tests

- State Machine scoping

- State Machine sharing

- Parent-child relations
    * Parent to child state sharing
    * Child to parent state sharing
    * Child to Child state sharing
    * Child to Brother state sharing
    * etc...

- Lists

- Concurrency

- Android-specific state machines (support for unsafe)

- (internal) machine separation for cross linked and multi linked


--------
Stage 1.5 (a.k.a if there is enough time)

- RxJava / Flow integration

- Testing

- Go through lint/ detekt, etc

--------
Stage 2:

Android Navigation stored as state in some state machine