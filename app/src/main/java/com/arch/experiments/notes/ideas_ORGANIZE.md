




- What if machines were aware of their parents (such as MachineLinker or Config), 
    they possibly this could do something good, is this valid? 

- With this architecture, whether observe methods are called
      is decided outside the scope of presenter. Is this okay?

- Consider all the things you can do when you have access to all the state from a single location 

- Create possibility for using this library when you don't actually need that many states, 
    as is usual with many android apps. But it should also provide a way to handle those states if need be.

- Need a way to provide ways to combine state machine, in order to support list ViewData? 

- What if state handler get's notified before updating components?

- Consider how to handle cases where some edge case shouldn't happen
if programmer was good (such as arguments!! or context!!)

- Handle different types of widget. There are widget that need to:
    * Handle user and "programmer" updates
    * Handle user updates
    * Handle "programmer" updates
    * Need to set just initial state (like Flutter's StatelessWidget)
        Although this could be considered a "programmer" update

- Attaching other state machines

- Implement two types of state machines:
    * With Async operations
    * Without Sync operations

- Consider state initialization. There are two types:
    * Initialization when class is created
    * Initialization when lifecycle is started




- CHOOSE: single state field VS separate fields for each widget state.
    * IF using single state field, for state description CHOOSE: data class VS sealed class

- Why send widget state updates to state handler if you can pass arguments only when needed?
    * IF sending updates, CHOOSE: for EditText, handle each letter update VS handle done listener



- Consider a way to "force", or make it easier, for the user to handle all the state changes.
    One option would be to use sealed classes.

- View can only:
    * Dispatch an Action
    * Observe a State change (this State should ONLY contain items that are necessary for that view)



- PTR: People have hard time distinguishing between Event and State
    Cia svarbi problema. Kaip programuotojui nesuklysti ir lengvai ir teisingai naudoti Event/State data, ar MutableLiveData/SingleLiveData
    Not having to provide initial state for EventConfig would be one way to minimize confusion
    one way to do that would be to remove stateProvider from Pusher and Observer

- Consider when the lifecycle doesn't match with the storage of the machine
    * For example when storing changes of list items. When they get unbinded they should retain their state.


- Consider usage outside of UI-Controller communcation

- CHOOSE: Asynchronous state updates: should be part of stream VS just update BehaviourSubject themselves

- Consider for time travelling things:
    * Could only write down concrete state field changes instead of full state change

- Consider making a "where to start" guide and lots of other tutorials, examples, samples

-Decide what to do with passing data in actions and effects.
    It seems to me that it would be best if no data was pass anywhere,
        it would only change through state.
    But then there would need to be very strict ordering.

- Consider that there are more lifecycle events then just start/stop.
    Possible events:
    1. Where state handling is initialized, but view is not being notified
    2. View starts being notified
    3. View stops being notified
    4. State handling is stopped


- Consider that StateMachineFactory could be used similarly for presenter?

- Consider communication between fragments and activities

- Consider passing extras between activities and bundles in fragments

- Consider complex view, animations with states, etc. etc.
    Maybe if you are creating a complex custom view,
    then you should use state machines internally?

- Consider integration with other state machines

- Consider situations when you need things to happen in some order.
    Example: You set a toast message and only after that you set toast show = true.
    If they were switched, you wouldn't get the latest message

- Create structure for automatically tracking UserActions.
    This could allow for a sort of "integration test", where you would replay those actions.

- Consider that with switch cases methods might get bloated.
    Possible solution:
    Strictly enforcing method extraction when unless one-liner

- Consider what to do about focusing views, should they be part of explicit state?
    My thinking is that they should, but only if something needs to be changed,
    then state should handle focuses of all views

- What if the view events are emitting too fast and system can't keep up,
    make some buffering mechanism. Example:
        Typing too fast, system can't keep up, what do?

- Consider distinctUntilChanged, should it be used?
    It might even cause harm, I think the best way would be to somehow track duplicates,
    but still let them pass and analyze them later

- Consider if there is a way to text if you implemented UI state machine correctly

-    // TODO need to somehow handle the creation of new state machines coming from UI

- From a conceptual standpoint, there is no difference between cached state,
    persistent storage, network requests,
    to me they all like state updates



- Ensure that the state received is always the latest

- Explore state sharing between screens

- Consider which places you should be able to attach middleware


- Consider creating some separate super efficient state handling,
    which could allow some boilerplate, but would be used in
    situations where performance is key (Stage 2)

- Investigate possible variations for forms and provide some solutions

- Somehow avoid state change infinite loops (maybe there is a way to detect them, to avoid outofmemory)?

- Aren't actions really just things that trigger some view component state machine?
    Yes, but it's quite impractical to implement it like that

- Consider a wrapper that would wrap all state machines into one for easier middleware

- Consider having the option to use the trigger mechanism if wanted

- What if the lifecycle requires to continue calculations but you can't show in view, see example in ooono

- PTR: The state machine should be thought of as a small presenter made only for that single view

- Integration with ViewModel

- TBD: Maybe notifyObserverOnInit  should be an event????

 - Consider different types of state machines:
    State machine that only updates values internally
    State machine that updates values internally and exposes external mutation
    State machine that returns values immediately or async
    The only connectino between the components should be State Updates!

- Go through as many projects as I can and gather ideas, use cases

- startDateEditText.setOnClickListener { // shows date picker } (guest-list app)

- Consider passing state machines to parents and children, make it easy

- Think of proper name for "programmer" updates

- Consider that there are things that you only set once

- What about R.string resources for EditText?

- Cases which should never happen should be sent to crashlytics

- Consider don't keep activities integration (by default everything should be stored in state)

- Consider state machines for activities/fragments

- Lifecycle: each State Machine should have it's own lifecycle management. How to make it easy?

My main issue with MVVM is the total lack of event driven interaction. If your whole view is inside a model, where will you store animations if that kind of state really only exists in the view?
You even need that weird EventLiveData to handle simple dialog triggering and that kind of things. I think MVP is not yet outdated, as it is superior to MVVM in terms of events and providing a flow throughout your application.
- Joost Klitsie, kažkoks random bičiukas

- Try out architecture with setState method and compare it with my architecture
  fun setState(function: () -> Unit) {
  	function()
  	notifyStateChanged
  }
  setState{} is weird.

Points about "workflow" lib:
BAD:
Not entirely clear between the difference of State and Rendering,
    Seems to me like all of it should be State
Difficult to grasp initially (whatever though)
Worker class is tied to Flow (I guess you can convert)
Confusing types, for example:  private val child: Workflow<Unit, Nothing, Any>
Not particularly fond of Nothing types
Not particularly fond of return null everuwhere
Snapshot is tied to okio (maybe not that bad?)
GOOD:
Good documentation
Time travel is a good feature





