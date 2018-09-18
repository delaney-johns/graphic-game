package graphicgame

trait Entity {
  def x: Double
  def y: Double
  def width: Double
  def height: Double

  def update(delay: Double): Unit
}