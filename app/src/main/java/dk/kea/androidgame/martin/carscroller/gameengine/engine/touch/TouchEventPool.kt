package dk.kea.androidgame.martin.carscroller.gameengine.engine.touch

import dk.kea.androidgame.martin.carscroller.gameengine.engine.core.Pool

class TouchEventPool : Pool<TouchEvent>() {
    override fun newItem(): TouchEvent {
        return TouchEvent()
    }
}
