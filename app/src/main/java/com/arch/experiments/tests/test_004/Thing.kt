package com.arch.experiments.tests.test_004

import android.content.Context
import com.arch.experiments.common.extensions.showToast
import io.reactivex.subjects.BehaviorSubject

// Partial states? For example, transforming isLoading to actual View visibility

// Things like EditText can also be set from view

// Some people say that you could directly pass editText data to login, but then you wouldn't be able to prefil that data

// Leaks? Too early to worry about them

// Each action could be saved with a timestamp (maybe, if there are no performance issues)

// If I will ever do a presentation, specify explicitly why view should have less state

// Why all of this hassle? To have predictable view state and perfect synchronizatino between "presenter" and view.

// TODO handle state resetting / initializing

// TODO argument passing through Fragments/Activities

// TODO make a presentation singularly on why doubling state in view is bad, provide concrete examples.

// TODO think of concrete reason why you should udpate state with each editText edit, and not just receive it when you need it.

// TODO specify Anticipation Driven UX (basically, every-ish next screen or at least data is preloaded)

// TODO synchronization handling

// TODO for debug you could store much more data, such as time between or somethign

// TODO every action should be handled


// TODO figure out how to actually specify, basically the whole navigation system needs to be rethought with these in mind
//  PTR: documentation for this should be like RX, with images, screens and arrows!
data class ScreenState(val activity: ActivityState) // TODO Shouldn't store actual activity or fragment, bad for memory

// TODO if you specify fragment and activities, it will be coupled with Android, is that OK?

// TODO specify how to integrate legacy code with this structure

// TODO Think about the situation where you would want to put separate things into separate modules

sealed class ActivityState { // TODO Proper name
    // TODO how to add fragments here?
    object Login : ActivityState() // TODO figure out the proper way to assign these

    object Greeting : ActivityState()
}

// TODO somehow force to handle certain states or errors

// TODO State accessing could be done with Dagger

// PTR: this is basically an object that just reduces states, that are related to some screen
class LoginReducer {
    val stateUpdates = BehaviorSubject.create<State>() // TODO is it properly synchronized?
    val state
        get() = stateUpdates.value ?: State.DEFAULT

//    fun initState(): State {
//
//    }


    fun reduceUi(state: State, action: Action) { // TODO does it need return type?
        when (action) {
            is Action.LoginClicked -> state.copy(isLoading = true)
            is Action.UsernameEntered -> state.copy(username = action.username)
            is Action.PasswordEntered -> state.copy(password = action.password)
        }.also(stateUpdates::onNext) // TODO These could be in return type, onNext could be called internally
    }

    fun reduceEffects(state: State, effect: Effect) {
        when (effect) {
            is Effect.LoginError -> state.copy(isShowingError = true, error = effect.cause, isLoading = false)
            is Effect.LoginSuccess -> state.copy(isLoading = false)
        }.also(stateUpdates::onNext)
    }

    fun reduceScreenState(state: ScreenState, effect: Effect) {
        when (effect) {
            is Effect.LoginError -> state
            is Effect.LoginSuccess -> state.copy(activity = ActivityState.Greeting)
        }.also { screenState ->
            //            screenStateUpdates.onNext(screenState)
        }
    }

    data class State(
        // TODO Most people would consider this News, but maybe this is a more valid approach?
        // TODO If it's set to true, how to set it back to false?
        // This is something like a Toast or Snackbar, so we only show it once, but the thing is,
        // If we only show it once, that means that the view has state which we cannot control anymore,
        // that means that view should only react to state changes, not Actions or News.
        val isShowingError: Boolean,
        val error: Throwable?,
        // TODO maybe this should be= val errorState: ErrorState;; data class ErrorState(isShowing: Boolean, error: Throwable?)

        val isLoading: Boolean,
        val username: String,
        val password: String
    ) {
        companion object {
            val DEFAULT = State(false, null, false, "", "")
        }
    }

    sealed class Action {
        object LoginClicked : Action()
        data class UsernameEntered(val username: String) : Action()
        data class PasswordEntered(val password: String) : Action()
    }

    // Action happens before state is changed
    // Effect is called after state is changed (Wait but that's not entirely true..)
    // How to handle News? Maybe they should also be part of the state, just one that returns to it's former self somehow?
    // Should an error be a special kind of Effect?
    // What is Navigation even? Effect maybe?
    // There are things that affect the UI and things that affect the System, how (if) to differentiate?
    // Maybe things like Navigation should also be saved in some global-ish state?
    // Maybe there could be an object graph of states, one where a state can access a parent but not a child?


    // PTR: maybe there are things that could be saved among multiple states?
    // ptr Probably not a good idea, since it would require syncing across all those states

    sealed class Effect { // TODO Maybe these effects should be part of the LoginUseCase?
        data class LoginError(val cause: Throwable) : Effect()
        object LoginSuccess : Effect()
        // TODO add effect which isn't just part of login, since these both could go to LoginUseCase.
    }
}

// PTR is the Model really needed? Maybe "presenter" should directly handle all data by directly accessing repositories and things

class RefreshThing {
    val stateUpdates = BehaviorSubject.create<State>()
    val state
        get() = stateUpdates.value ?: State.DEFAULT

    data class State(val isLoggingIn: Boolean) {
        companion object {
            val DEFAULT = State(false)
        }
    }
}

class LoginBinder(
    private val loginReducer: LoginReducer,
    private val refreshThing: RefreshThing,
    private val loginUseCase: LoginUseCase
) {
    // TODO This is maybe a side effect
    fun getValues() {
//        val a = Observable.merge(loginReducer.stateUpdates
//                .filter { it.isLoading }, refreshThing.stateUpdates
//                .filter { it.isLoggingIn })
        val a = loginReducer.stateUpdates
            .filter { it.isLoading }
            .flatMapCompletable { loginUseCase.login(it.username, it.password) }
        // TODO should we continue here or emit an action?
//            .flatMapCompletable { user -> databaseRepository.putUser(user) }

        // Now this goes nowhere
    }
}

// TODO think about things that are in state, but in things like preferences. What to do with them?
// todo I mean they obviously have their own state, but at the same time are not accessible at State objects.
// todo what if each state change/get was async? Then you could treat database changes as simple state changes, just as equal as switch changes
// todo then you could treat

// TODO how to handle async/rx events

// TODO implement access token / refresh token with this structure

// PTR: system events could also be received, but unaccessible to other components
// ptr For example it could be that event is sent after data is updated from login, but is this necessary?

// PTR: maybe one point to strive for could be to have short classes

// TODO make a mechanism to measure time between actions, to prevent lag.

class Toaster(val context: Context) {
    private var cause: Throwable? = null

    fun setError(cause: Throwable?) {
        this.cause = cause
    }

    fun show() {
        val message = cause?.message
        message?.let(context::showToast)
    }

    fun hide() {
        // TODO implement
    }
}