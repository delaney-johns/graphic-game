package graphicgame

import java.rmi.server.UnicastRemoteObject

@remote trait RemotePlayer {
  def leftPressed()
  def leftReleased()
  def rightPressed()
  def rightReleased()
  def upPressed()
  def upReleased()
  def downPressed()
  def downReleased()
  def wPressed()
  def wReleased()
  def aPressed()
  def aReleased()
  def sPressed()
  def sReleased()
  def dPressed()
  def dReleased()
  def x: Double
  def y: Double
  def score: Int
}

class Player(
  private var _x: Double,
  private var _y: Double,
  val level: Level) extends UnicastRemoteObject with Entity with RemotePlayer {
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1
  var dead = false
  def isDead: Boolean = dead
  private var coolDown = 0.0
  private var _score = 0

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
  
  def score = _score

  //If directional key is pressed, player moves in appropriate x, y direction.
  //If WASD keys are pressed, projectiles shoot in appropriate direction.
  def update(delay: Double): Unit = {
    coolDown -= delay
    if (up) move(0, -speed * delay)
    if (down) move(0, speed * delay)
    if (left) move(-speed * delay, 0)
    if (right) move(speed * delay, 0)
    if (w && coolDown <= 0) {
      new Projectile(x, y - 1, level, 0, -1, this)
      coolDown = .5
    }
    if (a && coolDown <= 0) {
      new Projectile(x - 1, y, level, -1, 0, this)
      coolDown = .5
    }
    if (s && coolDown <= 0) {
      new Projectile(x, y + 1, level, 0, 1, this)
      coolDown = .5
    }
    if (d && coolDown <= 0) {
      new Projectile(x + 1, y, level, 1, 0, this)
      coolDown = .5
    }
    killPlayer
  }

  //If the place a player wants to go is clear (does not have walls), then they can move there.
  def move(changeX: Double, changeY: Double): Unit = {
    if (level.maze.isClear(_x + changeX, _y + changeY, width, height)) {
      _x += changeX
      _y += changeY
    }
  }

  def killPlayer = {
    for (enemy <- level.enemies) {
      if (intersects(enemy)) {
        dead = true
      }
      
      for (projectiles <- level.projectiles) {
        if (intersects(projectiles)) {
          dead = true
        }
      }
    }
  }

  //Checks to see if player intersects with enemy.
  def intersects(enemy: Entity): Boolean = {
    val overlapX = (_x - enemy.x).abs < 0.5 * (width + enemy.width)
    val overlapY = (_y - enemy.y).abs < 0.5 * (height + enemy.height)
    overlapX && overlapY
  }
  
  def incrementScore() = {
    _score += 10
  }

  def buildPassableEntity = PassableEntity(EntityType.Player, x, y, width, height)
}