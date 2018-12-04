package graphicgame

//Each enemy has a position, size, and is part of only currently implemented level.
class Enemy1(
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
  //Default speed for an enemy1
  val speed = 3

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
        //Based on shortest path, creates list that is used to determine which direction enemy should move in.
        //Order is up, right, down, left
        val distanceList = List(
          level.shortestPath(x, y - 1, players(0).x, players(0).y),
          level.shortestPath(x + 1, y, players(0).x, players(0).y),
          level.shortestPath(x, y + 1, players(0).x, players(0).y),
          level.shortestPath(x - 1, y, players(0).x, players(0).y))


        //Determines index of minimum steps
        val distance = distanceList.indexOf(distanceList.min)

        //Based on index of list, move in that a direction (up, right, down, or left)
        if (distance == 0) {
          move(0, -speed * delay)
        }
        if (distance == 1) {
          move(speed * delay, 0)
        }
        if (distance == 2) {
          move(0, speed * delay)
        }
        if (distance == 3) {
          move(-speed * delay, 0)
        }
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

  def buildPassableEntity = PassableEntity(EntityType.Enemy1, x, y, width, height)
}