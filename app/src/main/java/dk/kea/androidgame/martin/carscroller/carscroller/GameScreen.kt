package dk.kea.androidgame.martin.carscroller.carscroller

import dk.kea.androidgame.martin.carscroller.carscroller.world.World
import dk.kea.androidgame.martin.carscroller.carscroller.world.WorldRenderer
import dk.kea.androidgame.martin.carscroller.engine.core.GameEngine
import dk.kea.androidgame.martin.carscroller.engine.core.Screen


class GameScreen(gameEngine: GameEngine) : Screen(gameEngine) {
    enum class State {
        PAUSED,
        RUNNING,
        GAME_OVER
    }

    var background = gameEngine.loadBitmap("carscroller/xcarbackground.png")
    var resume = gameEngine.loadBitmap("carscroller/resume.png")
    var gameOver = gameEngine.loadBitmap("carscroller/gameover.png")

    var state = State.RUNNING

    var bounce = gameEngine.loadSound("carscroller/bounce.wav")
    var crash = gameEngine.loadSound("carscroller/blocksplosion.wav")
    var gameOverSound = gameEngine.loadSound("carscroller/gameover.wav")

    var world = World(gameEngine, object : CollisionListener {
        override fun collisionRoadSide() {
            bounce.play(1f)
        }

        override fun collisionMonster() {
            crash.play(1f)
        }

        override fun gameOver() {
            gameOverSound.play(1f)
        }
    })

    var worldRenderer = WorldRenderer(gameEngine, world)

    var backgroundX: Float = 0f

    override fun update(deltaTime: Float) {
        if (state === State.RUNNING) {
            backgroundX += 100 * deltaTime
            if (backgroundX > 2700 - 480) {
                backgroundX = 0f
            }
        }

        gameEngine.drawBitmap(background, 0, 0, backgroundX.toInt(), 0, 480, 320)
        world.update(deltaTime, gameEngine.accelerometer[0])
        worldRenderer.render()
    }

    override fun pause() {
        if (state === State.RUNNING) state = State.PAUSED
        gameEngine.music.pause()
    }

    override fun resume() {
        gameEngine.music.play()
    }

    override fun dispose() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}