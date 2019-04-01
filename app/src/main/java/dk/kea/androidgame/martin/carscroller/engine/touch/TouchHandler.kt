package dk.kea.androidgame.martin.carscroller.engine.touch

interface TouchHandler {
    fun isTouchDown(pointer: Int): Boolean
    fun getTouchX(pointer: Int): Int
    fun getTouchY(pointer: Int): Int
}