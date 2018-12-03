package graphicgame

class Enemy2(
  private var _x: Double,
  private var _y: Double,
  val level: Level) extends Entity {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1
  var dead = false
  def isDead: Boolean = dead
  var r = scala.util.Random.nextInt(5)
  //Default speed for an enemy2
  val speed = 5

  //If enemy's location intersects with a projectile, it is dead.
  def killEnemy = {
    for (projectile <- level.projectiles) {
      if (intersects(projectile)) {
        projectile.hitEnemy()
        dead = true
      }
    }
  }

  //Moves an enemy (if it is not dead) based on the shortest path to the player.
  def update(delay: Double): Unit = {
    killEnemy
    if (!dead) {
      val players = level.players
      if (players.nonEmpty) {
        //Based on randomization, move in that a direction (up, right, down, or left).
        if (r == 0) {
          move(0, -speed * delay)
        }
        if (r == 1) {
          move(0, -speed * delay)
        }
        if (r == 2) {
          move(0, speed * delay)
        }
        if (r == 3) {
          move(-speed * delay, 0)
        }
        r = scala.util.Random.nextInt(5)
      }
    }
  }

  //Checks to see if enemy intersects with projectile.
  def intersects(projectile: Projectile): Boolean = {
    val overlapX = (_x - projectile.x).abs < 0.5 * (width + projectile.width)
    val overlapY = (_y - projectile.y).abs < 0.5 * (height + projectile.height)
    overlapX && overlapY
  }

  //Checks if a location is clear. If yes, changes the current position to the new position
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }

  def buildPassableEntity = PassableEntity(EntityType.Enemy2, x, y, width, height)
}
