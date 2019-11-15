Initializtion/Creation:

Problems:
1. How / where to initialize machines?

2. How / where to initialize the state?

------
State can be initialized:
  * in XML
  * by passing data to presenter (let's say extras or arguments)
  * by passing data to view (let's say some initial value from preferences)
------
If presenter takes in an Observer,
then you don't need to initialize the state from the presenter;
that responsibility goes to the view.
Therefore you don't care about initial state in Observer.

If presenter takes in a Pusher,
then you should provide initial state from presenter.
Consider if that should be forced or optional.

What about Machine? Seems like it should be similar to Pusher

What if Observer, Pusher, Machines for one screen one packed into some single class?
------
Useful classes:

StateInteractor - general name

StateProvider
Observer
Pusher
Machine

For uninitialized machines:
StateProvider.Factory
Observer.Factory
Pusher.Factory
Machine.Factory