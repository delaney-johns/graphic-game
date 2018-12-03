package graphicgame

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import java.rmi.registry.LocateRegistry
import java.util.concurrent.LinkedBlockingQueue

@remote trait RemoteServer {
  def connect(client: RemoteClient): RemotePlayer
}

object Server extends UnicastRemoteObject with App with RemoteServer {
  LocateRegistry.createRegistry(1099)
  Naming.rebind("GraphicGameServer", this)
  val level = new Level
  //TODO: make more than one enemy appear in random places each time
  for(i <- 1 to 20) new Enemy1(1.5+ util.Random.nextInt(20)*3, 1.5+ util.Random.nextInt(20)*3, level)
  for(i <- 1 to 10) new Enemy2(1.5+ util.Random.nextInt(20)*3, 1.5+ util.Random.nextInt(20)*3, level)
  var clientList = List[RemoteClient]()
  val connectedPlayerQueue = new LinkedBlockingQueue[Player]()
  def connect(client: RemoteClient): RemotePlayer = {
    println("connecting player")
    clientList = client :: clientList
    val player = new Player(1.5+ util.Random.nextInt(20)*3, 1.5+ util.Random.nextInt(20)*3, level)
    connectedPlayerQueue.put(player)
    player
  }

  //Timer is implemented because the characteristics of entities change depending on time.
  var lastTime = -1L
  val drawInterval = 0.01
  var drawDelay = 0.0
  while (true) {
    while (!connectedPlayerQueue.isEmpty()) level += connectedPlayerQueue.take()
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