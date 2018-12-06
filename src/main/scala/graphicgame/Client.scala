package graphicgame

import java.rmi.server.UnicastRemoteObject
import scalafx.application.JFXApp
import scalafx.scene.canvas.Canvas
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import scalafx.Includes._
import scalafx.application.Platform
import java.rmi.Naming

//list of remote client in main
//death=> respawn in new location or be a spectator(not on entity list) but they're still connected

@remote trait RemoteClient {
  def drawLevel(passableLevel: PassableLevel): Unit

}

object Client extends UnicastRemoteObject with JFXApp with RemoteClient {
  val canvas = new Canvas(1000, 800)
  val renderer = new Renderer2D(canvas.graphicsContext2D, 30)
  val server = Naming.lookup("rmi://localhost/GraphicGameServer") match {
    case s: RemoteServer => s
  }
  val player: RemotePlayer = server.connect(this)
  def drawLevel(passableLevel: PassableLevel): Unit = {
    Platform.runLater(renderer.render(passableLevel, player.x, player.y, player.score))
  }
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

  }
}