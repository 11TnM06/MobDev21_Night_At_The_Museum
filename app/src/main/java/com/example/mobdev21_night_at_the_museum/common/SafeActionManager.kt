package com.example.mobdev21_night_at_the_museum.common

typealias WAITING_ACTION = () -> Unit

class SafeActionManager {

    private var isSafeAction = false
    private val waitingActionList = mutableListOf<WAITING_ACTION>()

    fun doSafeAction(action: WAITING_ACTION) {
        if (isSafeAction) {
            action.invoke()
        } else {
            addWaitingAction(action)
        }
    }

    fun doWaitingAction() {
        val actionList = mutableListOf<WAITING_ACTION>()
        actionList.addAll(this.waitingActionList)

        if (actionList.isNotEmpty()) {
            actionList.forEach { waitingAction ->
                waitingAction.invoke()
                removeWaitingAction(waitingAction)
            }
        }
    }

    fun setSafeActionState(isSafeAction: Boolean) {
        this.isSafeAction = isSafeAction
    }

    fun resetWaitingActionList() {
        this.waitingActionList.clear()
    }

    private fun removeWaitingAction(action: WAITING_ACTION) {
        this.waitingActionList.remove(action)
    }

    private fun addWaitingAction(action: WAITING_ACTION) {
        this.waitingActionList.add(action)
    }
}
