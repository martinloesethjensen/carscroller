package dk.kea.androidgame.martin.carscroller.carscroller.screen

import android.util.Log
import dk.kea.androidgame.martin.carscroller.carscroller.CollisionListener
import dk.kea.androidgame.martin.carscroller.carscroller.world.World
import dk.kea.androidgame.martin.carscroller.carscroller.world.WorldRenderer

import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.Screen
import dk.kea.androidgame.martin.myfirstgameengine.engine.touch.TouchEvent


class GameScreen(gameEngine: GameEngine) : Screen(gameEngine) {
    enum class State {
        PAUSED,
        RUNNING,
        GAME_OVER
    }


    private var backgroundSpeed = 100
    private var background = gameEngine.loadBitmap("carscroller/xcarbackground.png")
    var resume = gameEngine.loadBitmap("carscroller/resume.png")
    private var pause = gameEngine.loadBitmap("carscroller/pause-button.png")
    var gameOver = gameEngine.loadBitmap("carscroller/gameover.png")

    private var state = State.RUNNING

    var bounce = gameEngine.loadSound("carscroller/bounce.wav")
    var crash = gameEngine.loadSound("carscroller/blocksplosion.wav")
    var gameOverSound = gameEngine.loadSound("carscroller/gameover.wav")

    private var world = World(gameEngine, object : CollisionListener {
        override fun collisionRoadSide() {
            bounce.play(1f)
        }

        override fun collisionMonster() {
            crash.play(1f)
        }

        override fun gameOver() {
            gameOverSound.play(1f)
        }
    }, backgroundSpeed = backgroundSpeed)

    private var worldRenderer = WorldRenderer(gameEngine, world)

    private var backgroundX: Float = 0f

    override fun update(deltaTime: Float) {
        if (world.gameOver) {
            state = State.GAME_OVER
        }

        Log.d("GameScreen", "Size: ${gameEngine.getTouchEvents().size}")

        if (state === State.PAUSED && gameEngine.getTouchEvents().isNotEmpty()) {
            Log.d("GameScreen", "Starting the game again.")
            state = State.RUNNING
            resume()
            gameEngine.getTouchEvents()
        }

        if (state === State.GAME_OVER) {
            Log.d("GameScreen", "Game is over.")
            val touchEvents = gameEngine.getTouchEvents()

            Log.d("GameScreen", "Event list size: ${touchEvents.size}")
            for (event in touchEvents) {
                Log.d("GameScreen", "$event")
                if (event.type === TouchEvent.TouchEventType.UP) {
                    gameEngine.setScreen(MainMenuScreen(gameEngine))
                    return
                }
            }
        }

        if (state === State.RUNNING && gameEngine.getTouchY(0) < 40
                && gameEngine.getTouchX(0) > (480 - 40)) {
            Log.d("GameScreen", "Pausing the game.")
            state = State.PAUSED
            pause()

        }

        if (state === State.RUNNING) {
            backgroundX += backgroundSpeed * deltaTime
            if (backgroundX > 2700 - 480) {
                backgroundX = 0f
            }
            // update the game objects
            world.update(deltaTime = deltaTime, accelerometerY = gameEngine.accelerometer[1])
        }

        // draw the background no matter what
        gameEngine.drawBitmap(background, 0, 0, backgroundX.toInt(), 0, 480, 320)
        gameEngine.drawBitmap(pause, (475 - pause.width).toFloat(), 5f)
        // draw the game object no matter what state
        worldRenderer.render()

        if (state === State.PAUSED) {
            gameEngine.drawBitmap(resume, (240 - resume.width / 2).toFloat(), (160 - resume.height / 2).toFloat())
        }

        if (state === State.GAME_OVER) {
            gameEngine.drawBitmap(gameOver, (240 - gameOver.width / 2).toFloat(), (160 - gameOver.height / 2).toFloat())
        }
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