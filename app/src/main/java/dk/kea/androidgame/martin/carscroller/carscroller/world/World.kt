package dk.kea.androidgame.martin.carscroller.carscroller.world

import dk.kea.androidgame.martin.carscroller.carscroller.CollisionListener
import dk.kea.androidgame.martin.carscroller.carscroller.model.Car
import dk.kea.androidgame.martin.carscroller.carscroller.model.Monster
import dk.kea.androidgame.martin.carscroller.engine.core.GameEngine

class World(var gameEngine: GameEngine, var collisionListener: CollisionListener) {
    companion object {
        const val MIN_X = 0
        const val MAX_X = 479
        const val MIN_Y = 40
        const val MAX_Y = 280
    }

    var car = Car()
    var monsters = arrayListOf<Monster>()
    var maxMonsters = 3
    var gameOver = false
    var points = 0
    var lives = 3

    init {
        initializeMonsters()
    }

    fun update(deltaTime: Float, accelerometerY: Float) {
        // move the car based on the phone accelerometer. For finished game.
        car.y = (car.y + accelerometerY * deltaTime * 40).toInt()
        // move the car based on user screen touch. Only for testing. Remove before publishing.
        if (gameEngine.isTouchDown(0)) {
            car.y = gameEngine.getTouchY(0) - Car.HEIGHT
        }

        if (car.y < MIN_Y) car.y = MIN_Y

        if (car.y + Car.HEIGHT > MAX_Y) car.y = MAX_Y - Car.HEIGHT - 1
    }

    private fun initializeMonsters() {
    }
}