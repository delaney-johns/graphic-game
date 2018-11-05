package graphicgame

import scala.collection.mutable.Buffer
import com.sun.jmx.remote.internal.ArrayQueue

class Level {
  //Creates maze for the level and a list of all entities.
  val maze = Maze(3, false, 20, 20, 0.6)
  private var _entities = Buffer[Entity]()
  def entities: Buffer[Entity] = _entities
  def -=(e: Entity): Unit = {
    entities -= e
  }
  def +=(e: Entity): Unit = {
    _entities += e
  }

  //Builds collection of projectiles.
  def projectiles = _entities.collect {
    case p: Projectile => p
  }

  //Build collection of players.
  def players = _entities.collect {
    case p: Player => p
  }

  //Entities update based on a time.
  def updateAll(delay: Double): Unit = {
    for (entity <- entities) {
      entity.update(delay)
    }
  }
  def buildPassableLevel: PassableLevel = {
  PassableLevel(maze, entities.map(_.buildPassableEntity))  
  }
  
  
  //Takes location of enemy and location of player.
  //Determines the amount of steps in a direction from enemy's location to player's location.
  val offsets = Array((-1, 0), (1, 0), (0, 1), (0, -1))
  def shortestPath(sx: Int, sy: Int, ex: Int, ey: Int): Int = {
    val queue = new collection.mutable.Queue[(Int, Int, Int)]()
    var visited = Set(sx -> sy)
    queue.enqueue((sx, sy, 0))
    while (!queue.isEmpty) {
      val (x, y, steps) = queue.dequeue()
      if (x == ex && y == ey) return steps
      for ((dx, dy) <- offsets) {
        val nx = x + dx
        val ny = y + dy
        if (ny >= 0 && ny < maze.height && nx >= 0 && nx < maze.width &&
          maze.open(ny, nx) && !visited(nx -> ny)) {
          queue.enqueue((nx, ny, steps + 1))
          visited += (nx -> ny)
        }
      }
    }
    1000000000
  }

}