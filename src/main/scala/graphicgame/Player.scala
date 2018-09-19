package graphicgame

class Player(
  private var _x: Double,
  private var _y: Double,
  val level: Level) extends Entity {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1

  //Default speed for player.
  val speed = 3.0

  //Player is standing still at start of level.
  private var up = false
  private var down = false
  private var left = false
  private var right = false

  //When key is pressed, direction is set to true. This moves player using update method.
  def upPressed = { up = true }
  def downPressed = { down = true }
  def leftPressed = { left = true }
  def rightPressed = { right = true }

  //When key is released, player cannot move.
  def upReleased = { up = false }
  def downReleased = { down = false }
  def leftReleased = { left = false }
  def rightReleased = { right = false }

  //If key is pressed, player moves in appropriate x, y direction.
  def update(delay: Double): Unit = {
    if (up) move(0, -speed * delay)
    if (down) move(0, speed * delay)
    if (left) move(-speed * delay, 0)
    if (right) move(speed * delay, 0)
  }

  //If the place a player wants to go does not have walls, then they can move there.
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }
}