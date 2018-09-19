package graphicgame

class Level {
  //Creates maze for the level and a list of all entities.
  val maze = Maze(3, false, 20, 20, 0.6)
  private var _entities = List[Entity]()
  def entities: List[Entity] = _entities
  def +=(e: Entity): Unit = {
    _entities ::= e
  }
  
  //Entities update based on a time.
  def updateAll(delay: Double): Unit = {
    for (entity <- entities) {
      entity.update(delay)

    }
  }
}