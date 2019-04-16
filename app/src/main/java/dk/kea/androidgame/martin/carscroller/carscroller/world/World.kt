package dk.kea.androidgame.martin.carscroller.carscroller.world

import android.util.Log
import dk.kea.androidgame.martin.carscroller.carscroller.CollisionListener
import dk.kea.androidgame.martin.carscroller.carscroller.model.Car
import dk.kea.androidgame.martin.carscroller.carscroller.model.Monster
import dk.kea.androidgame.martin.myfirstgameengine.engine.core.GameEngine
import kotlin.random.Random

class World(var gameEngine: GameEngine,
            var collisionListener: CollisionListener,
            var backgroundSpeed: Int = 100) {
    companion object {
        const val MIN_X = 0
        const val MAX_X = 479
        const val MIN_Y = 30
        const val MAX_Y = 285
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

        for (monster in monsters) {
            monster.x -= (backgroundSpeed * deltaTime).toInt()
            if (monster.x < 0 - Monster.WIDTH) {
                // respawn the monster.
                val random = Random
                monster.x = 500 + random.nextInt(300)
                monster.y = 30 + random.nextInt(230)
                Log.d("World", "Just recycled a monster.")
            }
        }
        // check if the car collides with a monster.
        collideCarMonster()
    }

    private fun collideCarMonster() {
        for (monster in monsters) {
            if (collideMonsters(car.x, car.y, Car.WIDTH, Car.HEIGHT,
                            monster.x, monster.y, Monster.WIDTH, Monster.HEIGHT)) {
                gameOver = true
                Log.d("World", "Car collided with a monster. Game over is true.")
            }
        }
    }

    private fun collideMonsters(x: Int, y: Int, width: Int, height: Int, x2: Int, y2: Int, width2: Int, height2: Int): Boolean {
        if (x < (x2 + width2) && (x + width) > x2 && y < (y2 + height2) && (y + height) > y2) {
            return true
        }
        return false
    }

    private fun initializeMonsters() {
        val random = Random
        for (i in 0 until maxMonsters) {
            val tempRandomX = random.nextInt(50)
            val tempRandomY = random.nextInt(255)
            val monster = Monster((500 + tempRandomX) + (i * 50), 30 + tempRandomY)
            monsters.add(monster)
        }
    }
}