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

  //Projectiles are not present at the start of level.
  private var w = false
  private var a = false
  private var s = false
  private var d = false

  //When key is pressed, direction is set to true. This moves player using update method.
  def upPressed = { up = true }
  def downPressed = { down = true }
  def leftPressed = { left = true }
  def rightPressed = { right = true }

  //When key is pressed, key is set to true. Used to make projectiles.
  def wPressed = { w = true }
  def aPressed = { a = true }
  def sPressed = { s = true }
  def dPressed = { d = true }

  //When key is released, player cannot move.
  def upReleased = { up = false }
  def downReleased = { down = false }
  def leftReleased = { left = false }
  def rightReleased = { right = false }

  //When key is released, player stops shooting projectiles.
  def wReleased = { w = false }
  def aReleased = { a = false }
  def sReleased = { s = false }
  def dReleased = { d = false }

  //If directional key is pressed, player moves in appropriate x, y direction.
  //If WASD keys are pressed, projectiles shoot in appropriate direction.
  def update(delay: Double): Unit = {
    if (up) move(0, -speed * delay)
    if (down) move(0, speed * delay)
    if (left) move(-speed * delay, 0)
    if (right) move(speed * delay, 0)
    if (w) new Projectile(x, y, level, 0, -speed * delay)
    if (a) new Projectile(x, y, level, -speed * delay, 0)
    if (s) new Projectile(x, y, level, 0, speed * delay)
    if (d) new Projectile(x, y, level, speed * delay, 0)
  }

  //If the place a player wants to go is clear (does not have walls), then they can move there.
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }
}