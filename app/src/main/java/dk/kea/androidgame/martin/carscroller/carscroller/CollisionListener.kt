package dk.kea.androidgame.martin.carscroller.carscroller

interface CollisionListener {
    fun collisionRoadSide()
    fun collisionMonster()
    fun gameOver()
}