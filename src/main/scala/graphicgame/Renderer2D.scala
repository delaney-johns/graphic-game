package graphicgame

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

//TODO draw string on for scoreboard
/**
 * This is a 2D renderer that with draw your game elements to a Canvas. You should change the
 * images to fit the style of your game. Also, alter the entities to match what you have in
 * your game.
 *
 * Unlike the Maze class, you will do quite a bit of editing this file to change images and alter
 * other details. You will have to uncomment the render method to use this.
 */
class Renderer2D(gc: GraphicsContext, blockSize: Double) {
  private var lastCenterX = 0.0
  private var lastCenterY = 0.0

  // Put variables for images here. Put your images in the src/main/resources directory.
  private val floorImage = Renderer2D.loadImage("/images/grass.jpg")
  private val wallImage = Renderer2D.loadImage("/images/wall.jpg")
  private val playerImage = Renderer2D.loadImage("/images/player.jpg")
  private val enemy1Image = Renderer2D.loadImage("/images/enemy.jpg")
  private val enemy2Image = Renderer2D.loadImage("/images/enemy2image.jpg")
  private val generatorImage = Renderer2D.loadImage("/images/generator.png")
  private val projectileImage = Renderer2D.loadImage("/images/projectile.jpg")

  /**
   * These two methods are used to figure out where to draw things. They are used by the render.
   */
  def blocksToPixelsX(bx: Double): Double = gc.canvas.getWidth / 2 + (bx - lastCenterX) * blockSize
  def blocksToPixelsY(by: Double): Double = gc.canvas.getHeight / 2 + (by - lastCenterY) * blockSize

  /**
   * These two methods are used to go from coordinates to blocks. You need them if you have mouse interactions.
   */
  def pixelsToBlocksX(px: Double): Double = (px - gc.canvas.getWidth / 2) / blockSize + lastCenterX
  def pixelsToBlocksY(py: Double): Double = (py - gc.canvas.getHeight / 2) / blockSize + lastCenterY

  /**
   * This method is called to render things to the screen.
   */
  def render(passableLevel: PassableLevel, cx: Double, cy: Double, score: Int): Unit = {
    lastCenterX = cx
    lastCenterY = cy
    
    val drawWidth = (gc.canvas.getWidth / blockSize).toInt + 1
    val drawHeight = (gc.canvas.getWidth / blockSize).toInt + 1

    // Draw walls and floors
    for {
      x <- cx.toInt - drawWidth / 2 - 1 to cx.toInt + drawWidth / 2 + 1
      y <- cy.toInt - drawHeight / 2 - 1 to cy.toInt + drawHeight / 2 + 1
    } {
      val img = if (passableLevel.maze(x, y)) {
        wallImage
      } else {
        floorImage
      }
      gc.drawImage(img, blocksToPixelsX(x), blocksToPixelsY(y), blockSize, blockSize)
    }

    // Draw entities
    for (e <- passableLevel.entities) {
      val img = e.typeOfEntity match {
        case EntityType.Player => playerImage
        case EntityType.Enemy1 => enemy1Image
        case EntityType.Enemy2 => enemy2Image
        case EntityType.Projectile => projectileImage
      }
      if (passableLevel.maze.wrap) {
        for (rx <- -1 to 1; ry <- -1 to 1)
          gc.drawImage(img, blocksToPixelsX(e.x - e.width / 2 + rx * passableLevel.maze.width), blocksToPixelsY(e.y - e.height / 2 + ry * passableLevel.maze.height), e.width * blockSize, e.height * blockSize)
      } else {
        gc.drawImage(img, blocksToPixelsX(e.x - e.width / 2), blocksToPixelsY(e.y - e.height / 2), e.width * blockSize, e.height * blockSize)
      }
    }
    gc.fill = Color.White
    gc.fillText("Score: " + score, 20, 20)
    
  }
 
}
object Renderer2D {
  /**
   * This method assumes that you are putting your images in src/main/resources. This directory is
   * packaged into the JAR file. Eclipse doesn't use the JAR file, so this will go to the file in
   * the directory structure if it can't find the resource in the classpath. The argument should be the
   * path inside of the resources directory.
   */
  def loadImage(path: String): Image = {
    val res = getClass.getResource(path)
    if (res == null) {
      new Image("file:src/main/resources" + path)
    } else {
      new Image(res.toExternalForm())
    }
  }
}