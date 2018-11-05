package graphicgame

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import java.rmi.registry.LocateRegistry

@remote trait RemoteServer {
  def connect(client: RemoteClient): RemotePlayer
}

object Server extends UnicastRemoteObject with App with RemoteServer {
  LocateRegistry.createRegistry(1099)
  Naming.rebind("GraphicGameServer", this)
  val level = new Level
  val enemy = new Enemy(5, 5, level)
  var clientList = List[RemoteClient]()
  def connect(client: RemoteClient): RemotePlayer = {
    clientList = client :: clientList
    new Player(1.5, 1.5, level)
  }

  //Timer is implemented because the characteristics of entities change depending on time.
  var lastTime = -1L
  val drawInterval = 0.01
  var drawDelay = 0.0
  while (true) {
    val time = System.nanoTime()
    if (lastTime >= 0) {
      val delay = (time - lastTime) * 1e-9
      level.updateAll(delay)
      drawDelay += delay
      if (drawDelay >= drawInterval) {
        val passableLevel = level.buildPassableLevel
        clientList.foreach(_.drawLevel(passableLevel))
        drawDelay = 0.0
      }
    }
    lastTime = time
  }
}