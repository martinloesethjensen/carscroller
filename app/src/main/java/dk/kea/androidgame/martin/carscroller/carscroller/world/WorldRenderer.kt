package dk.kea.androidgame.martin.carscroller.carscroller.world

import dk.kea.androidgame.martin.carscroller.`my-first-game-engine`.engine.core.GameEngine

class WorldRenderer(var gameEngine: GameEngine, var world: World) {
    var carImage = gameEngine.loadBitmap("carscroller/xbluecar2.png")
    var monsterImage = gameEngine.loadBitmap("carscroller/xyellowmonster2.png")

    init {
        render()
    }

    fun render() {
        gameEngine.drawBitmap(carImage, world.car.x.toFloat(), world.car.y.toFloat())

        for (monster in world.monsters) {
            gameEngine.drawBitmap(monsterImage, monster.x.toFloat(), monster.y.toFloat())
        }
    }
}