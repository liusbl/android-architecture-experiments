- IDEA: generate some debug screen with all the information of
 all state machines where you can change all the states

- Common name for (Publish and Subscribe) ValueProvider, Observer, Pusher and Machine:
    - StateInteractor?

- Maybe store state in internal storage

- Consider LiveData transformations
    https://proandroiddev.com/clean-easy-new-how-to-architect-your-app-part-4-livedata-transformations-f0fd9f313ec6

- I feel like formatting and storing state should be all part of the responsibility of the edit text.

- With this architecture, whether observe methods are called
      is decided outside the scope of presenter. Is this okay?

- Consider all the things you can do when you have access to all the state from a single location 

- Create possibility for using this library when you don't actually need that many states, 
    as is usual with many android apps. But it should also provide a way to handle those states if need be.
    
- Check code smell patterns

- Consider what is the separation between ui and presenter state?

- Create the perfect example which encompasses all these cases

- Consider most common use cases.

- Answer the question: WHY?

- What if settings self state is too long and something happens in that time?

- What if state handler get's notified before updating components?

- OTHER: What does saying "UseCase" provide

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

- During presentation do live coding / refactoring from mvp/mvvm

- Make "decortator" for config where you could the reverse config, for timetravel purposes

- Consider state resetting

- CHOOSE: single state field VS separate fields for each widget state.
    * IF using single state field, for state description CHOOSE: data class VS sealed class

- Why send widget state updates to state handler if you can pass arguments only when needed?
    * IF sending updates, CHOOSE: for EditText, handle each letter update VS handle done listener

- Don't notify "programmer" updates to widgets when state is changed from user side.
    This could result in useless callbacks or infinite loops.
    Example:
        1. TextWatcher for EditText is attached
        a. StateHandler state changes are observed by EditText
        2. User inputs text
        3. TextWatcher for EditText is notified by the framework
        4. TextWatcher dispatches Action to StateHandler
        5. Action updates the state in StateHandler
        6. EditText observes change from StateHandler
        7. EditText changes text, even though it's the same.

- Consider a way to "force", or make it easier, for the user to handle all the state changes.
    One option would be to use sealed classes.

- View can only:
    * Dispatch an Action
    * Observe a State change (this State should ONLY contain items that are necessary for that view)

- Handle lifecycle in a generic way

- Add possibility for middleware, logging
    * Also, could log last states in case of crash

- Should navigation be part of StateHandler or should it just be called in view?

- Consider schedulers and threads

- Consider unhandled errors

- PTR: People have hard time distinguishing between Event and State
    Cia svarbi problema. Kaip programuotojui nesuklysti ir lengvai ir teisingai naudoti Event/State data, ar MutableLiveData/SingleLiveData
    Not having to provide initial state for EventConfig would be one way to minimize confusion
    one way to do that would be to remove stateProvider from Pusher and Observer

- Consider scoping

- Consider errors

- Consider synchronization, concurrency, and all those things

- Consider when you push identical states, how should it be reacted to

- Consider how to pass state machines from one place to another

- Consider concurrency issues
    Analyze both conceptual and implementation concurrency issues.

- Describe all the possible UI components and their interactions with state

- Consider when the lifecycle doesn't match with the storage of the machine
    * For example when storing changes of list items. When they get unbinded they should retain their state.

- Why is it bad to call view.getSomething from presenter?
    * It wouldn't work in MVVM

- Logging

- Debugging options

- Consider hyperion

- Middlewares for logging or storage

- Maybe make some structure when latest state is stored in background to prefs or something

- Consider what if every component had it's own state machine

- TellDontAsk

- Provide a way to just set the initial state

- Handle state changes from wider scopes.
    Example:
        Fragment's StateHandler could have access to Activity's or Application's StateHandlers.

- Consider Menus

- Consider onActivityResult

- Consider usage outside of UI-Controller communcation

- CHOOSE: Asynchronous state updates: should be part of stream VS just update BehaviourSubject themselves

- Provide scope in which to create things that will have the same lifecycle

- Check for leaks, proper object disposing

- For lists there needs to be a way to create machine before the ui component is available

- Possibility for improving things  like changing the language

- Generate state machine from ui xml or something

- Consider how not to bloat up state with large object

- Consider for time travelling things:
    * Could only write down concrete state field changes instead of full state change

- Consider providing solution without sealed classes for people fearing large switch cases

- Consider making a "where to start" guide and lots of other tutorials, examples, samples

- PTR: Graphs are things that make things clear, so use more of them

- Analyze redux lib, react-native

- Consider thread safety (Stage 1), but integration with other libs can come at Stage 2

-Decide what to do with passing data in actions and effects.
    It seems to me that it would be best if no data was pass anywhere,
        it would only change through state.
    But then there would need to be very strict ordering.

- It might be hard to test when everything is an observable, make it easier

- Ensure that the latest state is always returned

- Consider what if you only want to emit effect and not change state?

- Consider usage in services, broadcast receivers

- Consider that there are more lifecycle events then just start/stop.
    Possible events:
    1. Where state handling is initialized, but view is not being notified
    2. View starts being notified
    3. View stops being notified
    4. State handling is stopped

- Consider what if you don't want effects?

- Consider simplicity / ease of understanding

- Consider that StateMachineFactory could be used similarly for presenter?

- Consider communication between fragments and activities

- Create plan of what features to implement and priorities, aka roadmap

- Considers views with complex states

- STREAMS: What if there is need for disposing the stream early?

- Consider how to integrate logging or other middleware

- Consider passing extras between activities and bundles in fragments

- Consider complex view, animations with states, etc. etc.
    Maybe if you are creating a complex custom view,
    then you should use state machines internally?

- Consider Android-specific race conditions

- Consider more unusual lifeccle events, such as createOptionsMenu

- Consider navigation cases like onBackPressed

- PTR: Using lambdas instead of Pusher or Observer is not valid, since you couldn't get "value"

- Consider integration with other state machines

- Consider global handler of view models

- Consider performance.
    Make benchmarks

- Consider what if you need to get previous state for some reason

- Passing huge list through activities would crash app. So the question is how to handle shared state?

- Consider complex custom views.

- Consider minimizing mistakes

- Generate state machines from ui xml or something

- Consider things that partially belong to view and partially to presenter, such as @ColorRes changes.
    Maybe some transformation classes could be used?

- Consider Don't keep activities

- Consider Activity, Fragment and DialogFragment lifecycles

- Consider possibility of replacing Actions with quick State changes.

- Check MVICore for ideas

- Consider testing

- Consider usage outside of "presenter" type use cases

- Check old test project for good comments

- How to update list items

- Consider the "overhead" of coupling with Rx or Flow

- Consider situations when you need things to happen in some order.
    Example: You set a toast message and only after that you set toast show = true.
    If they were switched, you wouldn't get the latest message

- PTR: There are some actions that just change state, others call some apis

- Consider how to stop running streams

- Create sample app that uses everything

- Consider caching

- Consider animations

- Consider data passing from repositories

- Consider other architecture libraries

- Automatically get free RxClicks and similar things

- Create easy way to make wrappers around Configs
    (like UnsafeAndroidConfig or RecyclerViewItemConfig or smth)

- Consider proper naming

- Create custom state saver on configuration change

- Find cases where some state history is necessary

- Consider databinding and how my lib could overlap those benefits

- Consider other library integration

- Consider coroutines

- Create structure for automatically tracking UserActions.
    This could allow for a sort of "integration test", where you would replay those actions.

- Track time of actions dispatching

- Consider other state machine integration.

- Consider side effects

- Consider data sharing between components

- Consider that with switch cases methods might get bloated.
    Possible solution:
    Strictly enforcing method extraction when unless one-liner

- Consider lifecycle

- Consider why effect should be in single stream

- Consider support for firebase

- Consider examples for lists

- Consider data transfer and business logic decoupling

- Consider onSavedInstanceState (or what will be received after activity kill)

- Consider that it's almost impossible to have generic BaseViewModel

- Consider Android's current ViewModel architecture

- Consider Flowable vs. Observable

- Consider different build types and flavours

- Consider naming

- Shared scope in-memory storage instead of extras or arguments

- Consider what to do about focusing views, should they be part of explicit state?
    My thinking is that they should, but only if something needs to be changed,
    then state should handle focuses of all views

- Emphasize differences from other architectures, analyze pros and cons.

- Consider integration with default Android widgets and other structures
    Examples:
    EditText
    TextView
    Spinner
    Toolbar
    Popup menu
    Drawer
    SearchView
    RecyclerView
    ProgressBar
    Toast
    Snackbar
    ImageView
    AutoCompleteTextView
    Button
    CheckBox (group)
    CalendarView?
    Chronometer
    Switch
    RadioButton (group)
    NumberPicker
    RatingBar
    TimePicker (dialog)


- CHOOSE: state in View VS state in Presenter

- Consider how to best provide those dependencies

- Consider error handling

- Consider long running tasks, like WorkManager, broadcasts, receivers

- What if the view events are emitting too fast and system can't keep up,
    make some buffering mechanism. Example:
        Typing too fast, system can't keep up, what do?

- Consider distinctUntilChanged, should it be used?
    It might even cause harm, I think the best way would be to somehow track duplicates,
    but still let them pass and analyze them later

- Maybe convert Pusher and Observer to lambdas?

- Consider what if you want to not send every action to state machine? Such as navigation on button click

- Consider if there is a way to text if you implemented UI state machine correctly

-    // TODO need to somehow handle the creation of new state machines coming from UI

- Consider extras / bundles

- Consider onSavedInstanceState

- Consider when and how to dispose

- What if Observer, Pusher, Machines for one screen one packed into some single class?

- Analyze problem when you need to have enum state for view

- Consider readability

- PTR: When demoing show how to convert our architecture to this one

- Consider consistent scalability (both small and large)

- For lists, maybe should have a single state machine container for all items?

- Consider RxJava and Coroutines

- Consider cases when you need to dispose some stream before end of lifecycle

- Consider decoupling as much as possible
    * Library shouldn't provide BaseActivity or BaseFragment,
     rather it should provide interface that has storage methods

- Consider effects that only need to happen once

- From a conceptual standpoint, there is no difference between cached state,
    persistent storage, network requests,
    to me they all like state updates

- Maybe everything can be a state machine

- Consider using some structure like CRUD?

- Talk to marketing about writing an article

- Explain why each state change should have it's own state machine

- Convert news app to that architecture

- Analyze dependency overhead

- Ensure that the state received is always the latest

- Explore state sharing between screens

- Try to integrate with RecyclerView

- Look up state machine terminology

- Create benchmarks

- Consider side effects.
    One option is that side effects are also just state changes of internal state machines?

- Look up related functional programming things

- Consider which places you should be able to attach middleware

- Consider time travel

- Append timestamp to each state change

- Consider creating some separate super efficient state handling,
    which could allow some boilerplate, but would be used in
    situations where performance is key (Stage 2)

- Investigate possible variations for forms and provide some solutions

- Somehow avoid state change infinite loops (maybe there is a way to detect them, to avoid outofmemory)?

- Aren't actions really just things that trigger some view component state machine?
    Yes, but it's quite impractical to implement it like that

- Consider a wrapper that would wrap all state machines into one for easier middleware

- Consider what if you need previous state

- Consider filters

- Consider how immutability is ensured

- Create good testing model

- Good integration with both rx and flow and any other stream library (Stage 2)

- Consider having the option to use the trigger mechanism if wanted

- Consider not only view state, but internal business logic state

- Consider how to improve

- What if the lifecycle requires to continue calculations but you can't show in view, see example in ooono

- PTR: The state machine should be thought of as a small presenter made only for that single view

- Integration with ViewModel

- Look up JS

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





