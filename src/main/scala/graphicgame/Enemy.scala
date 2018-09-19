package graphicgame

//Each enemy has a position, size, and is part of only currently implemented level.
class Enemy(
  private var _x: Double,
  private var _y: Double,
  val level: Level) extends Entity {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1

  //Default speed for an enemy
  val speed = 3

  //Moves an enemy up, if walls are not in the path
  def update(delay: Double): Unit = {
    move(0, -speed * delay)
  }

  //Checks if a spot is clear. If yes, changes the current position to the new position
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }
}