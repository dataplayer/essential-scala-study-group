
import scala.util.parsing.combinator._

/**
 * The world's simplest possible implementation of the IO monad.
 * A pure way to represent effectful operations as a thunk.
 */
case class IO[A](unsafePerformIO: () => A) {
  def map[B](ab: A => B): IO[B] = IO(() => ab(unsafePerformIO()))
 
  def flatMap[B](afb: A => IO[B]): IO[B] = IO(() => afb(unsafePerformIO()).unsafePerformIO())
 
  def tryIO(ta: Throwable => A): IO[A] = 
    IO(() => IO.tryIO(unsafePerformIO()).unsafePerformIO() match {
      case Left(t) => ta(t)
      case Right(a) => a
    })

}

object IO {
  def point[A](a: => A): IO[A] = IO(() => a)

  def tryIO[A](a: => A): IO[Either[Throwable, A]] =
    IO(() => try { Right(a) } catch { case t : Throwable => Left(t) })
}

object Console {
  /**
   * Writes a line of text to the console.
   */
  def putStrLn(line: String): IO[Unit] = IO(unsafePerformIO = () => println(line))

  /**
   * Reads a line of text from the console.
   */
  def getStrLn: IO[String] = IO(unsafePerformIO = () => readLine())
}

trait Describable {
  def describe: String
}

sealed trait Item extends Describable

case class Sword() extends Item {
  def describe = "A metallic sword."
}

case class Key(num: Int) extends Item {
  def describe = "A key labelled with a " + number + "."
  def number = num
}

case class Door(num: Int, isOpen: Boolean) extends Item {
  val status = if (isOpen) "open" else "closed"
  def describe = "A thick wooden, " + status + ", door labelled with a: " + number
  def number = num
}

case class Location(x: Int, y: Int)
case class Cell(inv: List[Item], baseDesc: String) extends Describable {
  def describe = baseDesc
}

case class WorldMap(private val value: Vector[Vector[Cell]]) {
   // returns the cell at a certain location
  def cellAt(loc: Location): Option[Cell] = value(loc.x)(loc.y) match {
    case c: Cell => Some(c)
    case _ => None
  }
  // updates WorldMap with edits to a certain cell
  def updateAt(loc: Location, f: Cell => Cell): Option[WorldMap] = {
    cellAt(loc) match {
      case Some(cell) => {
        val x = value(loc.x).updated(loc.y, f(cell))
        val y = value.updated(loc.x, x)
        Some(WorldMap(y))        
      }
      case None => Some(WorldMap(value))
    }
  }
}

case class PlayerState(loc: Location, inv: List[Item])
case class GameState(player: PlayerState, map: WorldMap)

sealed trait Command

object Command extends {
  
  private object parser extends RegexParsers {
    lazy val quit:      Parser[Command] = "quit"      ^^^ Quit
    lazy val look:      Parser[Command] = "look"      ^^^ LookAround
    lazy val pickupkey: Parser[Command] = "pickupkey" ^^^ PickUpKey
    lazy val opendoor:  Parser[Command] = "opendoor"  ^^^ OpenDoor
    lazy val baglist:   Parser[Command] = "baglist"   ^^^ BagList
    lazy val north:     Parser[Command] = "north"     ^^^ North
    lazy val south:     Parser[Command] = "south"     ^^^ South
    lazy val east:      Parser[Command] = "east"      ^^^ East
    lazy val west:      Parser[Command] = "west"      ^^^ West

    def grammer: Parser[Command] = quit | look | north | south | east | west | pickupkey | opendoor | baglist
  }
  
  import parser._
  
  def parse(line: String): Command = parseAll(grammer, line) match {
    case Success(x,_)       => x
    case failure: NoSuccess => Unknown
  }
  
}

case object LookAround extends Command
case object PickUpKey  extends Command
case object OpenDoor   extends Command
case object BagList    extends Command
case object Quit       extends Command
case object Unknown    extends Command
case object North      extends Command
case object South      extends Command
case object East       extends Command
case object West       extends Command


object Application {
  def parse(text: String): Either[String, Command] = {
    Command.parse(text) match {
      case Unknown    => Left("Sorry, that command was not recognized: " + text)
      case Quit       => Right(Quit)
      case LookAround => Right(LookAround)
      case PickUpKey  => Right(PickUpKey)
      case OpenDoor   => Right(OpenDoor)
      case BagList    => Right(BagList)
      case North      => Right(North)
      case South      => Right(South)
      case East       => Right(East)
      case West       => Right(West)
    }
  }

  final case class ActResult(text: List[String], state: Option[GameState])

  def act(command: Command, oldState: GameState): ActResult = command match {
    case Quit       => ActResult(List("Good Bye!"), state = None)
    case LookAround => {
      val loc = oldState.player.loc
      val room = oldState.map.cellAt(loc)
      room match {
        case Some(r) => ActResult(r.describe +: r.inv.map(x => x.describe), Some(oldState))
        case None    => ActResult(List("Nothing to see around here..."), Some(oldState))
      }
    }
    case PickUpKey => {
      // find key in room inv
      // add it to player bag
      // remove key from room inventory
      val loc = oldState.player.loc
      val playerBag = oldState.player.inv
      val room = oldState.map.cellAt(loc)
      val newRoomInv = room match {
        case Some(r) => Some(r.inv.filter( p => !p.isInstanceOf[Key]))
        case None => None
      }
      val newPlayerBag = room match {
        case Some(r) => r.inv.flatMap { 
          case k: Key => k +: playerBag
          case _ => playerBag
        }
        case _ => playerBag
      }
      val newmap = oldState.map.updateAt(loc, c => Cell(newRoomInv.get,c.baseDesc))
      val player = PlayerState(loc, newPlayerBag)
      val newGameState = GameState(player, newmap.get)
      ActResult(List("You've picked up a key!"),Some(newGameState))
    }
    case BagList => {
      ActResult(oldState.player.inv.map( x => x.describe ),Some(oldState))
    }
    case OpenDoor => {
      // Does player have a key?
      // If no say "sorry you don't have a key"
      // If so does the label on the key and the label on the door match?
      // If not say "sorry you don't have the correct key"
      // If so then remove key from player inventory and label door open.
      val loc = oldState.player.loc
      val room = oldState.map.cellAt(loc)
      val doorLabel = room.get.inv.filter(x => x.isInstanceOf[Door]).head.asInstanceOf[Door].num
      val key = oldState.player.inv.filter(x => x.isInstanceOf[Key]).asInstanceOf[List[Key]].filter(k => k.num == doorLabel)
      key match {
        case Nil => ActResult(List("Sorry, you don't have the key to opens this door."), Some(oldState))
        case key :: tail => {
          // we have the correct key; need to setup up world
          val newPlayerBag = oldState.player.inv.filter( x => x != key)
          val player = PlayerState(loc, newPlayerBag)
          val closedDoor = Door(doorLabel,false)
          val newRoomInv = room.get.inv.filter(x => x != closedDoor)
          val openedDoor = Door(doorLabel,true)
          val newmap = oldState.map.updateAt(loc, c => Cell(openedDoor +: newRoomInv,c.baseDesc))
          val newGameState = GameState(player, newmap.get)
          ActResult(List("You've opened the door with key!"), Some(newGameState))
        }
      }
    }
    case _    => {
      val loc = oldState.player.loc
      val playerBag = oldState.player.inv
      val room = oldState.map.cellAt(loc)
      ActResult(List(room.get.baseDesc), Some(oldState))
    }
  }

  def loop(oldState: GameState): IO[GameState] =
    for {
      line <- Console.getStrLn
      
      ActResult(text, newStOpt) = parse(line) match {
          case Left(error)    => ActResult("Sorry, that command was not recognized:" :: error :: Nil, Some(oldState))
          case Right(command) => act(command, oldState)
        }
      
      _ <- text.foldLeft[IO[Unit]](IO.point(())) {
              case (io, line) =>
                for {
                  _ <- io
                  _ <- Console.putStrLn(line)
                } yield ()
            }
      
      finalState <- newStOpt match {
                      case None => IO.point(oldState)
                      case Some(newState) => loop(newState)
                    }
      
    } yield finalState

  /**
   * A pure IO value representing the application.
   */
  def start: IO[Unit] = {
      /* game initialization stuff */
      for {
        _ <- Console.putStrLn("Enter 'Start' to begin a new game or 'Quit' to quite the game.")
        input <- Console.getStrLn
        _ <- if (input == "Start") for {
               _ <- Console.putStrLn("Welcome to Nick's World!")
               _ <- loop(init)
             } yield Unit
             else for {
               _ <- Console.putStrLn("Looks like you don't want to paly, Bye!.")
             } yield Unit
      } yield Unit
    }
    
    /* initiate game */
    def init: GameState = {
      val akey = Key(1)
      val asword = Sword()
      val door1 = Door(1,false)
      val inventory = List[Item](akey, asword, door1)
      val location = Location(0, 0)
      val cell = Cell(inventory, "You are in a large room with some things around the room.")
      val mapvalue = Vector(Vector(cell))
      val map = WorldMap(mapvalue)
      val playerinventory = List[Item]()
      val player = PlayerState(location, playerinventory)
      GameState(player, map)
    }
}

object Main {
  /**
   * The main function of the application, which performs the effects
   * described by the application's IO monad.
   */
  def main(args: Array[String]): Unit = {
    
    val sword1 = Sword()
    val key1 = Key(1)
    val cell1 = Cell(List(sword1),"a room")
    val testWorldMap = WorldMap(Vector(Vector(cell1)))
    
    // make sure world map returns a cell with cellAt
    val gotcell = testWorldMap.cellAt(Location(0,0))
    assert(gotcell.get.baseDesc == "a room")
    
    // add an item to cell
    val testWorldMap2 = testWorldMap.updateAt(Location(0,0), c => Cell(c.inv ++ List(key1), c.baseDesc))
    val x = testWorldMap2 match {
      case Some(wmap) => wmap.cellAt(Location(0,0)) match {
        case Some(cell) => cell.inv.length
        case None => 0
      }
      case None => 0
    }
    assert(x == 2)
    
    Application.start.unsafePerformIO()
  }
}