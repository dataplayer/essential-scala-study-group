object Chapter6 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  // 6.1.9.2
  val animals = List("cat","dog","penguin")       //> animals  : List[String] = List(cat, dog, penguin)
  "mouse" +: animals :+ "tyrannosaurus"           //> res0: List[String] = List(mouse, cat, dog, penguin, tyrannosaurus)
  2 +: animals                                    //> res1: List[Any] = List(2, cat, dog, penguin)
  
  
  // 6.1.9.3
  case class Film(name: String, yearOfRelease: Int, imdbRating: Double)
  case class Director( firstName: String, lastName: String, yearOfBirth: Int, films: Seq[Film])
  val memento = new Film("Memento", 2000, 8.5)    //> memento  : Chapter6.Film = Film(Memento,2000,8.5)
  val darkKnight = new Film("Dark Knight", 2008, 9.0)
                                                  //> darkKnight  : Chapter6.Film = Film(Dark Knight,2008,9.0)
  val inception = new Film("Inception", 2010, 8.8)//> inception  : Chapter6.Film = Film(Inception,2010,8.8)
  val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
                                                  //> highPlainsDrifter  : Chapter6.Film = Film(High Plains Drifter,1973,7.7)
  val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9)
                                                  //> outlawJoseyWales  : Chapter6.Film = Film(The Outlaw Josey Wales,1976,7.9)
  val unforgiven = new Film("Unforgiven", 1992, 8.3)
                                                  //> unforgiven  : Chapter6.Film = Film(Unforgiven,1992,8.3)
  val granTorino = new Film("Gran Torino", 2008, 8.2)
                                                  //> granTorino  : Chapter6.Film = Film(Gran Torino,2008,8.2)
  val invictus = new Film("Invictus", 2009, 7.4)  //> invictus  : Chapter6.Film = Film(Invictus,2009,7.4)
  val predator = new Film("Predator", 1987, 7.9)  //> predator  : Chapter6.Film = Film(Predator,1987,7.9)
  val dieHard = new Film("Die Hard", 1988, 8.3)   //> dieHard  : Chapter6.Film = Film(Die Hard,1988,8.3)
  val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
                                                  //> huntForRedOctober  : Chapter6.Film = Film(The Hunt for Red October,1990,7.6)
                                                  //| 
  val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)
                                                  //> thomasCrownAffair  : Chapter6.Film = Film(The Thomas Crown Affair,1999,6.8)
                                                  //| 
  val eastwood = new Director("Clint", "Eastwood", 1930, Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))
                                                  //> eastwood  : Chapter6.Director = Director(Clint,Eastwood,1930,List(Film(High
                                                  //|  Plains Drifter,1973,7.7), Film(The Outlaw Josey Wales,1976,7.9), Film(Unfo
                                                  //| rgiven,1992,8.3), Film(Gran Torino,2008,8.2), Film(Invictus,2009,7.4)))
  val mcTiernan = new Director("John", "McTiernan", 1951, Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))
                                                  //> mcTiernan  : Chapter6.Director = Director(John,McTiernan,1951,List(Film(Pre
                                                  //| dator,1987,7.9), Film(Die Hard,1988,8.3), Film(The Hunt for Red October,199
                                                  //| 0,7.6), Film(The Thomas Crown Affair,1999,6.8)))
  val nolan = new Director("Christopher", "Nolan", 1970, Seq(memento, darkKnight, inception))
                                                  //> nolan  : Chapter6.Director = Director(Christopher,Nolan,1970,List(Film(Meme
                                                  //| nto,2000,8.5), Film(Dark Knight,2008,9.0), Film(Inception,2010,8.8)))
  val someGuy = new Director("Just", "Some Guy", 1990, Seq())
                                                  //> someGuy  : Chapter6.Director = Director(Just,Some Guy,1990,List())
  val directors = Seq(eastwood, mcTiernan, nolan, someGuy)
                                                  //> directors  : Seq[Chapter6.Director] = List(Director(Clint,Eastwood,1930,Lis
                                                  //| t(Film(High Plains Drifter,1973,7.7), Film(The Outlaw Josey Wales,1976,7.9)
                                                  //| , Film(Unforgiven,1992,8.3), Film(Gran Torino,2008,8.2), Film(Invictus,2009
                                                  //| ,7.4))), Director(John,McTiernan,1951,List(Film(Predator,1987,7.9), Film(Di
                                                  //| e Hard,1988,8.3), Film(The Hunt for Red October,1990,7.6), Film(The Thomas 
                                                  //| Crown Affair,1999,6.8))), Director(Christopher,Nolan,1970,List(Film(Memento
                                                  //| ,2000,8.5), Film(Dark Knight,2008,9.0), Film(Inception,2010,8.8))), Directo
                                                  //| r(Just,Some Guy,1990,List()))
  
  def moreThanOneFilm(numberOfFilms: Int): Seq[Director] = {
    directors.filter(director => director.films.length > numberOfFilms)
  }                                               //> moreThanOneFilm: (numberOfFilms: Int)Seq[Chapter6.Director]
  moreThanOneFilm(4)                              //> res2: Seq[Chapter6.Director] = List(Director(Clint,Eastwood,1930,List(Film(
                                                  //| High Plains Drifter,1973,7.7), Film(The Outlaw Josey Wales,1976,7.9), Film(
                                                  //| Unforgiven,1992,8.3), Film(Gran Torino,2008,8.2), Film(Invictus,2009,7.4)))
                                                  //| )
  moreThanOneFilm(5)                              //> res3: Seq[Chapter6.Director] = List()
  
  def directorYOB(yob: Int): Option[Director] = {
    directors.find(director => director.yearOfBirth == yob)
  }                                               //> directorYOB: (yob: Int)Option[Chapter6.Director]
  directorYOB(1990)                               //> res4: Option[Chapter6.Director] = Some(Director(Just,Some Guy,1990,List()))
                                                  //| 
  def direcorYOBandNumFilms(yob: Int, numberOfFilms: Int): Seq[Director] = {
    directors.filter(director =>  director.yearOfBirth < yob && director.films.length > numberOfFilms)
  }                                               //> direcorYOBandNumFilms: (yob: Int, numberOfFilms: Int)Seq[Chapter6.Director]
                                                  //| 
  direcorYOBandNumFilms(1971,2)                   //> res5: Seq[Chapter6.Director] = List(Director(Clint,Eastwood,1930,List(Film(
                                                  //| High Plains Drifter,1973,7.7), Film(The Outlaw Josey Wales,1976,7.9), Film(
                                                  //| Unforgiven,1992,8.3), Film(Gran Torino,2008,8.2), Film(Invictus,2009,7.4)))
                                                  //| , Director(John,McTiernan,1951,List(Film(Predator,1987,7.9), Film(Die Hard,
                                                  //| 1988,8.3), Film(The Hunt for Red October,1990,7.6), Film(The Thomas Crown A
                                                  //| ffair,1999,6.8))), Director(Christopher,Nolan,1970,List(Film(Memento,2000,8
                                                  //| .5), Film(Dark Knight,2008,9.0), Film(Inception,2010,8.8))))
  def sortDirectors(ascending: Boolean) = {
    directors.sortWith((x,y) => x.yearOfBirth < y.yearOfBirth)
  }                                               //> sortDirectors: (ascending: Boolean)Seq[Chapter6.Director]
  sortDirectors(true)                             //> res6: Seq[Chapter6.Director] = List(Director(Clint,Eastwood,1930,List(Film(
                                                  //| High Plains Drifter,1973,7.7), Film(The Outlaw Josey Wales,1976,7.9), Film(
                                                  //| Unforgiven,1992,8.3), Film(Gran Torino,2008,8.2), Film(Invictus,2009,7.4)))
                                                  //| , Director(John,McTiernan,1951,List(Film(Predator,1987,7.9), Film(Die Hard,
                                                  //| 1988,8.3), Film(The Hunt for Red October,1990,7.6), Film(The Thomas Crown A
                                                  //| ffair,1999,6.8))), Director(Christopher,Nolan,1970,List(Film(Memento,2000,8
                                                  //| .5), Film(Dark Knight,2008,9.0), Film(Inception,2010,8.8))), Director(Just,
                                                  //| Some Guy,1990,List()))
  // Ex. 6.2.7.1 Heros of the Silver Screen
  nolan.films.map(x => x.name)                    //> res7: Seq[String] = List(Memento, Dark Knight, Inception)
  directors.flatMap(director => director.films.map(film => film.name))
                                                  //> res8: Seq[String] = List(High Plains Drifter, The Outlaw Josey Wales, Unfor
                                                  //| given, Gran Torino, Invictus, Predator, Die Hard, The Hunt for Red October,
                                                  //|  The Thomas Crown Affair, Memento, Dark Knight, Inception)
  //6.6.2.1
  import scala.util.Try
  val opt1 = Some(1)                              //> opt1  : Some[Int] = Some(1)
  val opt2 = Some(2)                              //> opt2  : Some[Int] = Some(2)
  val opt3 = Some(3)                              //> opt3  : Some[Int] = Some(3)
  val seq1 = Seq(1)                               //> seq1  : Seq[Int] = List(1)
  val seq2 = Seq(2)                               //> seq2  : Seq[Int] = List(2)
  val seq3 = Seq(3)                               //> seq3  : Seq[Int] = List(3)
  val try1 = Try(1)                               //> try1  : scala.util.Try[Int] = Success(1)
  val try2 = Try(2)                               //> try2  : scala.util.Try[Int] = Success(2)
  val try3 = Try(3)                               //> try3  : scala.util.Try[Int] = Success(3)
  
  for {
    a <- opt1
    b <- opt2
    c <- opt3
   } yield a + b + c                              //> res9: Option[Int] = Some(6)
  
  for {
    a <- seq1
    b <- seq2
    c <- seq3
   } yield a + b + c                              //> res10: Seq[Int] = List(6)
  
  for {
    a <- try1
    b <- try2
    c <- try3
   } yield a + b + c                              //> res11: scala.util.Try[Int] = Success(6)
  
  

}