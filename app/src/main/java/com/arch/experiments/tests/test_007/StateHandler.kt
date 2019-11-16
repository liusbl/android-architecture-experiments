package com.arch.experiments.tests.test_007

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

// PTR: stateHandler could request conflict resolution strategy from Components?

// PTR: as it turns out, there is much more state that you can see.

// TBD: So maybe not all state settings should be async?
//  But then again, if you wanted async, there would still be these problems.

// TBD: Should state conflict resolution be async?
//  If that's the case, conflicts could be conflicted even again.
//  So probably NO, they should be sync.

class StateHandler<State> {
    private val buffer = ConcurrentLinkedQueue<BaseAction<State>>()
    private val conflictBuffer = ConcurrentLinkedQueue<BaseAction<State>>()
    private val actionBeingProcessed = AtomicReference<BaseAction<State>>()
    private val isConflicting = AtomicBoolean(false)

    fun update(action: BaseAction<State>) {
        processUpdate(action)
    }

    private fun processUpdate(action: BaseAction<State>) {
        if (!actionBeingProcessed.compareAndSet(null, action)) { // TODO check if this works
            // Update state asyncly
            actionBeingProcessed.compareAndSet(action, null)
        } else {
            isConflicting.set(true)
//            val conflictResolution = actionBeingProcessed.get().resolveConflict(action, )
        }
    }
}