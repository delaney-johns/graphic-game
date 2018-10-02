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

object Main extends JFXApp {
  val canvas = new Canvas(1000, 800)
  val renderer = new Renderer2D(canvas.graphicsContext2D, 30)
  val level = new Level
  val player = new Player(1.5, 1.5, level)
  val enemy = new Enemy(5, 5, level)
  stage = new JFXApp.PrimaryStage {
    title = "Easter Eggs" 
    scene = new Scene(1000, 800) {
      content = List(canvas)
    }

    //If the user presses a key, the player moves in the appropriate direction.
    canvas.onKeyReleased = (keyEvent: KeyEvent) => {
      keyEvent.code match {
        case KeyCode.Up => player.upReleased
        case KeyCode.Down => player.downReleased
        case KeyCode.Left => player.leftReleased
        case KeyCode.Right => player.rightReleased
        case KeyCode.W => player.wReleased
        case KeyCode.A => player.aReleased
        case KeyCode.S => player.sReleased
        case KeyCode.D => player.dReleased 
        case _ =>
      }
    }
    
    //If a user releases a key, the player stops moving.
    canvas.onKeyPressed = (keyEvent: KeyEvent) => {
      keyEvent.code match {
        case KeyCode.Up => player.upPressed
        case KeyCode.Down => player.downPressed
        case KeyCode.Left => player.leftPressed
        case KeyCode.Right => player.rightPressed
        case KeyCode.W => player.wPressed
        case KeyCode.A => player.aPressed
        case KeyCode.S => player.sPressed
        case KeyCode.D => player.dPressed
        case _ =>
      }
    }
   
    canvas.requestFocus()
    
    //Timer is implemented because the characteristics of entities change depending on time.
    var lastTime = -1L
    val timer = AnimationTimer { time =>
      if (lastTime >= 0) {
        val delay = (time - lastTime) * 1e-9
        level.updateAll(delay)
        renderer.render(level, player.x, player.y)
      }
      lastTime = time
    }
    timer.start()
  }
}
