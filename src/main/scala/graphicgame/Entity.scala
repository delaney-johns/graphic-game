package graphicgame

//Each entity has a position, size, and updates based on a time.

trait Entity {
  def x: Double
  def y: Double
  def width: Double
  def height: Double
  def update(delay: Double): Unit
}

