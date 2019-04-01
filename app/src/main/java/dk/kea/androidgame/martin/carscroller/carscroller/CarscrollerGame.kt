package dk.kea.androidgame.martin.carscroller.carscroller

import dk.kea.androidgame.martin.carscroller.engine.core.GameEngine
import dk.kea.androidgame.martin.carscroller.engine.core.Screen

class CarscrollerGame : GameEngine() {

    override fun createStartScreen(): Screen {
        music = this.loadMusic("engine/music.ogg")
        return MainMenuScreen(this)
    }

    public override fun onResume() {
        super.onResume()
        music.play()
    }

    public override fun onPause() {
        super.onPause()
        music.pause()
    }
}