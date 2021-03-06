# NAME

- Store state in internal storage

- Generate some debug screen with all the information of
    all state machines where you can change all the states
    
- Handle received error in onObserve and onPush

- Logging

- Debugging options

- Possibility of navigation as state instead of event

- Maybe make some structure when latest state is stored in background to prefs or something

- Generate state machine from ui xml or something

- Save timestamp with each state update

- Consider things that partially belong to view and partially to presenter, such as @ColorRes changes.
    Maybe some transformation classes could be used?
    
- Consider possibility of replacing Events with quick State changes.

- Create custom state saver for configuration changes (done in some test)

- Analyze problem when you need to have enum state for view (Such as view visibility)

- Time travel

- Integration with other popular libraries:
    - Android ViewModels
    - Dagger
    - RxJava
        - Consider Flowable vs. Observable
    - Coroutines
    - Timber
    - Databinding
    - Firebase
    - Hyperion

- Check cases like in festool how-to app and implement those

- Leave only one of Config or Machine

- State changes could be sent 

- What if you treated things like database / storage as state machines? 

- State history caching / storage

- Libraries should be separated as much as possible

- Send state history to crashlytics in event of crash or throwable

- Saved state changes could be replayed for testing (not entirely sure about that)

- Provide simple way to get Observable from state updates

- Provide simple way to set Observable as state updates

- Integration with Android ViewModel

- What if settings self state is too long and something happens in that time?

- Track time of actions dispatching

- Passing huge list through activities would crash app. So the question is how to handle shared state?

- Consider not only view state, but internal business logic state

- Consider different build types and flavours

- Consider what if you need to get previous state for some reason

- Lifecycles that outlive the UI lifecycle

- Do operations in background thread

- A "decorator" for config where you could the reverse config, for timetravel purposes

- Provide way to combine state machines.

- Find cases where some state history is necessary

- Unit testing 

- Automatically make some kind of espresso / integration testing 

- Integration with default Android widgets and other structures
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