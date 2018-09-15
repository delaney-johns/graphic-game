package graphicgame


import scalafx.application.JFXApp
import scalafx.scene.text
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.paint.Color

/**
 * This is a stub for the graphical game.
 */
object Main extends JFXApp {
	stage = new JFXApp.PrimaryStage {
		title = "My Game" // Change this to match the theme of your game.
		scene = new Scene(1000, 800) {
		  fill = Color.Black
		 
		  val welcomeText = new Label("Welcome to The Haunted Rink, a game by Delaney Johns")
		  welcomeText.layoutX = 300
		  welcomeText.layoutY = 10
		  
		
		  val directions = new Label("Use the arrow keys to move your roller skater and use WASD to shoot spooky ghosts.")
		  directions.layoutX = 300
		  directions.layoutY = 50
		 
		  content = List(welcomeText, directions)
		
		}
		
	}
}
