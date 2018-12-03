package graphicgame

class Projectile(
  private var _x: Double,
  private var _y: Double,
  val level: Level,
  val directionX: Double,
  val directionY: Double) extends Entity {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1
  var dead = false
  def isDead: Boolean = dead

  def hitEnemy() = dead = true

  //Default speed for a projectile
  val speed = 3

  //Moves a projectile, if walls are not in the path
  def update(delay: Double): Unit = {
    move(directionX, directionY)
  }

  def intersects(enemy: Entity): Boolean = {
    val overlapX = (_x - enemy.x).abs < 0.5 * (width + enemy.width)
    val overlapY = (_y - enemy.y).abs < 0.5 * (height + enemy.height)
    overlapX && overlapY
  }

  //Checks if a spot is clear. If yes, changes the current position to the new position. Projectile disappears from level when it hits wall.
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    } else
      dead = true
  }

  def buildPassableEntity = PassableEntity(EntityType.Projectile, x, y, width, height)
}
