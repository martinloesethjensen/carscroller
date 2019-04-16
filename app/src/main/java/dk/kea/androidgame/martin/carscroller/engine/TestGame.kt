package dk.kea.androidgame.martin.carscroller.engine

import dk.kea.androidgame.martin.carscroller.engine.core.GameEngine
import dk.kea.androidgame.martin.carscroller.engine.core.Screen

class TestGame : GameEngine() {
    override fun createStartScreen(): Screen {
        return TestScreen(this)
    }
}
