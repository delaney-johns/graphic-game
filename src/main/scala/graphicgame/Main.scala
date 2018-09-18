package graphicgame

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.text
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.paint.Color
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
  val canvas = new Canvas(1000, 800)
  val renderer = new Renderer2D(canvas.graphicsContext2D, 30)
  val level = new Level
  val player = new Player(1.5, 1.5, level)
  val enemy = new Enemy(5, 5, level)
  stage = new JFXApp.PrimaryStage {
    title = "My Game" // Change this to match the theme of your game.
    scene = new Scene(1000, 800) {
      content = List(canvas)

    }

    canvas.onKeyReleased = (keyEvent: KeyEvent) => {
      keyEvent.code match {
        case KeyCode.Up => player.upReleased
        case KeyCode.Down => player.downReleased
        case KeyCode.Left => player.leftReleased
        case KeyCode.Right => player.rightReleased
        case _ =>
      }
    }
    canvas.onKeyPressed = (keyEvent: KeyEvent) => {
      keyEvent.code match {
        case KeyCode.Up => player.upPressed
        case KeyCode.Down => player.downPressed
        case KeyCode.Left => player.leftPressed
        case KeyCode.Right => player.rightPressed
        case _ =>
      }
    }

    canvas.requestFocus()

    var lastTime = -1L
    val timer = AnimationTimer { time =>
      if (lastTime >= 0) {
        val delay = (time - lastTime) * 1e-9
        level.updateAll(delay)
        renderer.render(level, 0, 0)
      }
      lastTime = time
    }
    timer.start()
  }
}
