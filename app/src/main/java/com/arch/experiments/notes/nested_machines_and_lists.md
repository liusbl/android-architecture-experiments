There is clearly a need for some kind of state machine container
from which you could access child's state machines.

Perhaps the Presenter should also be this container.

This issues comes up when you try to create a list.
How then you treat each item's UI component?
If you treat them all separately,
then you don't have the corresponding fields:

    val progressStateMachines = mutableListOf<Machine<Boolean>>()
    val editTextStateMachines = mutableListOf<Machine<String>>()

Otherwise, you could do something like this:

    data class ViewData(isInProgress: Boolean, text: String)

    val stateMachines = mutableListOf<Machine<ViewData>>()

But then you break the convention that each UI component is
responsible for it's own state and has it's own state machine.

Therefore, there needs to be some kind of Container,
which would allow developer to :
    - access each ui component separate in a list item;
    - get other state machines which come from the same list item;

Idea: Perhaps Machine<Machine<?>>, something like that? ``