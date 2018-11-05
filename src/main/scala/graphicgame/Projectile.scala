package graphicgame

class Projectile(
  private var _x: Double,
  private var _y: Double,
  val level: Level,
  val directionX: Double,
  val directionY: Double
) extends Entity {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1

  //Default speed for a projectile
  val speed = 3

  //Moves a projectile up, if walls are not in the path
  def update(delay: Double): Unit = {
    move(directionX, directionY)
  }

  //Checks if a spot is clear. If yes, changes the current position to the new position. Projectile disappears from level when it hits wall.
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
    else
     level.entities -= this
  }
  
    def buildPassableEntity = PassableEntity(EntityType.Enemy, x, y, width, height)
}
