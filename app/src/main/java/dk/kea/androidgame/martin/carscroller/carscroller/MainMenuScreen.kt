package dk.kea.androidgame.martin.carscroller.carscroller

import android.graphics.Bitmap
import dk.kea.androidgame.martin.carscroller.engine.core.GameEngine
import dk.kea.androidgame.martin.carscroller.engine.core.Screen


class MainMenuScreen(gameEngine: GameEngine) : Screen(gameEngine) {
    var background: Bitmap = gameEngine.loadBitmap("carscroller/xcarbackground.png")
    var startGame: Bitmap = gameEngine.loadBitmap("carscroller/xstartgame.png")
    internal var passedTime = 0f
    internal var startTime: Long = 0

    init {
        startTime = System.nanoTime()
    }


    override fun update(deltaTime: Float) {
        if (gameEngine.isTouchDown(0) && passedTime > 0.5f) {
            gameEngine.setScreen(GameScreen(gameEngine))
            return
        }
        gameEngine.drawBitmap(background, 0f, 0f)
        passedTime += deltaTime
        if (passedTime - passedTime.toInt() > 0.5f) {
            gameEngine.drawBitmap(startGame, (240 - startGame.width / 2).toFloat(), 160f)
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }
}