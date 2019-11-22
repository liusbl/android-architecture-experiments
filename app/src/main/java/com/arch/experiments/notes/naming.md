# Naming conventions
- **State**: TODO
- **Event**: TODO
- **StateInteractor**: handles state in an undefined way
- **StateProvider**: `StateInteractor` provides current state 
- **Observer**: `StateProvider` that can observe state
- **Pusher**: `StateObserver` that can update state
- **Machine**: both `StateProvider` and `StateObserver`
- **Config**: TODO

