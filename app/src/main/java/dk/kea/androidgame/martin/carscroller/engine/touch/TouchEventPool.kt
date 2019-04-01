package dk.kea.androidgame.martin.carscroller.engine.touch

import dk.kea.androidgame.martin.carscroller.engine.core.Pool

class TouchEventPool : Pool<TouchEvent>() {
    override fun newItem(): TouchEvent {
        return TouchEvent()
    }
}
