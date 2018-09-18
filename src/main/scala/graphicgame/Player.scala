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

  val speed = 3.0

  private var up = false
  private var down = false
  private var left = false
  private var right = false

  def upPressed = { up = true }
  def downPressed = { down = true }
  def leftPressed = { left = true }
  def rightPressed = { right = true }

  def upReleased = { up = false }
  def downReleased = { down = false }
  def leftReleased = { left = false }
  def rightReleased = { right = false }

  def update(delay: Double): Unit = {
    if (up) move(0, -speed * delay)
    if (down) move(0, speed * delay)
    if (left) move(-speed * delay, 0)
    if (right) move(speed * delay, 0)
  }

  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }
}