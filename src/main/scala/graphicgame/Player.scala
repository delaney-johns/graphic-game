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
}

class Player(
  private var _x: Double,
  private var _y: Double,
  val level: Level) extends UnicastRemoteObject with Entity with RemotePlayer {
  level += this
  def x: Double = _x
  def y: Double = _y
  def width: Double = 1
  def height: Double = 1
  var dead = false
  def isDead: Boolean = dead
  private var coolDown = 0

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
    println("updating")
    coolDown -= delay.toInt
    println("coolDown is:" + coolDown)
    if (up) move(0, -speed * delay)
    if (down) move(0, speed * delay)
    if (left) move(-speed * delay, 0)
    if (right) move(speed * delay, 0)
    //TODO: make projectile release slower 
    if (w && coolDown <= 0) {
      new Projectile(x, y, level, 0, -speed * delay)
      //makes the bullet
      coolDown = 3
    }
    if (a && coolDown <= 0) {
      new Projectile(x, y, level, -speed * delay, 0)
      coolDown = 3
    }
    if (s && coolDown <= 0) {
      new Projectile(x, y, level, 0, speed * delay)
      coolDown = 3
    }
    if (d && coolDown <= 0) {
      new Projectile(x, y, level, speed * delay, 0)
      coolDown = 3
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
        println(enemy)
      }
    }
  }

  //Checks to see if player intersects with enemy.
  def intersects(enemy: Enemy1): Boolean = {
    val overlapX = (_x - enemy.x).abs < 0.5 * (width + enemy.width)
    val overlapY = (_y - enemy.y).abs < 0.5 * (height + enemy.height)
    overlapX && overlapY
  }

  def buildPassableEntity = PassableEntity(EntityType.Player, x, y, width, height)
}