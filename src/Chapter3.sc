object Chapter3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  // 3.1.6
  // Cats, Again
  class Cat(val color: String, val food: String) {}
  val Oswald = new Cat("Black","Milk")            //> Oswald  : Chapter3.Cat = Chapter3$$anonfun$main$1$Cat$1@511baa65
  val Henderson = new Cat("Ginger","Chips")       //> Henderson  : Chapter3.Cat = Chapter3$$anonfun$main$1$Cat$1@340f438e
  val Quentin = new Cat("Tabby and White", "Curry")
                                                  //> Quentin  : Chapter3.Cat = Chapter3$$anonfun$main$1$Cat$1@30c7da1e
  // Cats on the Prowl
  object ChipShop {
    def willServe(c: Cat) = {
      if ("chips".equals(c.food.toLowerCase)) true else false
    }
  }
  ChipShop.willServe(Oswald)                      //> res0: Boolean = false
  ChipShop.willServe(Henderson)                   //> res1: Boolean = true
  
  // Directorial Debut
  class Director(val firstName: String, val lastName: String, val yearOfBirth: Int) {
    def name = firstName + " " + lastName
  }
  
  class Film(val name: String, val yearOfRelease: Int, val imdbRating: Double, val director: Director) {
    def directorsAge = { yearOfRelease - director.yearOfBirth }
    def isDirectedBy(d: Director) = director.equals(d)
    def copy(name: String = "Momento", yearOfRelease: Int = 2000, imdbRating: Double = 8.5, director: Director = new Director("Christopher","Nolan",1970)): Film = {
      new Film(name,yearOfRelease,imdbRating,director)
    }
  }
  val eastwood = new Director("Clint", "Eastwood", 1930)
                                                  //> eastwood  : Chapter3.Director = Chapter3$$anonfun$main$1$Director$1@5b464ce
                                                  //| 8
  val mcTiernan = new Director("John", "McTiernan", 1951)
                                                  //> mcTiernan  : Chapter3.Director = Chapter3$$anonfun$main$1$Director$1@57829d
                                                  //| 67
  val nolan = new Director("Christopher", "Nolan", 1970)
                                                  //> nolan  : Chapter3.Director = Chapter3$$anonfun$main$1$Director$1@19dfb72a
  val someBody = new Director("Just", "Some Body", 1990)
                                                  //> someBody  : Chapter3.Director = Chapter3$$anonfun$main$1$Director$1@17c6892
                                                  //| 5
  
  val memento = new Film("Memento", 2000, 8.5, nolan)
                                                  //> memento  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@7e0ea639
  val darkKnight = new Film("Dark Knight", 2008, 9.0, nolan)
                                                  //> darkKnight  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@3d24753a
  val inception = new Film("Inception", 2010, 8.8, nolan)
                                                  //> inception  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@59a6e353
  
  val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7, eastwood)
                                                  //> highPlainsDrifter  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@7a0ac6
                                                  //| e3
  val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9, eastwood)
                                                  //> outlawJoseyWales  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@71be98f
                                                  //| 5
  val unforgiven = new Film("Unforgiven", 1992, 8.3, eastwood)
                                                  //> unforgiven  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@6fadae5d
  val granTorino = new Film("Gran Torino", 2008, 8.2, eastwood)
                                                  //> granTorino  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@17f6480
  val invictus = new Film("Invictus", 2009, 7.4, eastwood)
                                                  //> invictus  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@2d6e8792
  
  val predator = new Film("Predator", 1987, 7.9, mcTiernan)
                                                  //> predator  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@2812cbfa
  val dieHard = new Film("Die Hard", 1988, 8.3, mcTiernan)
                                                  //> dieHard  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@2acf57e3
  val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6, mcTiernan)
                                                  //> huntForRedOctober  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@506e6d
                                                  //| 5e
  val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8, mcTiernan)
                                                  //> thomasCrownAffair  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@96532d
                                                  //| 6
  
  eastwood.yearOfBirth                            //> res2: Int = 1930
  dieHard.director.name                           //> res3: String = John McTiernan
  invictus.isDirectedBy(nolan)                    //> res4: Boolean = false
  huntForRedOctober.directorsAge                  //> res5: Int = 39
  
  // find youngest director
  val movies = Seq(memento,darkKnight,inception,highPlainsDrifter,outlawJoseyWales,unforgiven,granTorino,invictus,predator,dieHard,huntForRedOctober,thomasCrownAffair)
                                                  //> movies  : Seq[Chapter3.Film] = List(Chapter3$$anonfun$main$1$Film$1@7e0ea63
                                                  //| 9, Chapter3$$anonfun$main$1$Film$1@3d24753a, Chapter3$$anonfun$main$1$Film$
                                                  //| 1@59a6e353, Chapter3$$anonfun$main$1$Film$1@7a0ac6e3, Chapter3$$anonfun$mai
                                                  //| n$1$Film$1@71be98f5, Chapter3$$anonfun$main$1$Film$1@6fadae5d, Chapter3$$an
                                                  //| onfun$main$1$Film$1@17f6480, Chapter3$$anonfun$main$1$Film$1@2d6e8792, Chap
                                                  //| ter3$$anonfun$main$1$Film$1@2812cbfa, Chapter3$$anonfun$main$1$Film$1@2acf5
                                                  //| 7e3, Chapter3$$anonfun$main$1$Film$1@506e6d5e, Chapter3$$anonfun$main$1$Fil
                                                  //| m$1@96532d6)
  def filmDirectorMaxAge(f1: Film, f2: Film): Film = if (f1.directorsAge > f2.directorsAge) f1 else f2
                                                  //> filmDirectorMaxAge: (f1: Chapter3.Film, f2: Chapter3.Film)Chapter3.Film
  def filmDirectorMinAge(f1: Film, f2: Film): Film = if (f1.directorsAge < f2.directorsAge) f1 else f2
                                                  //> filmDirectorMinAge: (f1: Chapter3.Film, f2: Chapter3.Film)Chapter3.Film
  def filmDirectorMaxRating(f1: Film, f2: Film): Film = if (f1.imdbRating > f2.imdbRating) f1 else f2
                                                  //> filmDirectorMaxRating: (f1: Chapter3.Film, f2: Chapter3.Film)Chapter3.Film
  
  val oldestDirector = movies.reduceLeft(filmDirectorMaxAge)
                                                  //> oldestDirector  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@2d6e8792
  println(oldestDirector.director.name + " age:" + oldestDirector.directorsAge)
                                                  //> Clint Eastwood age:79
  
  val youngestDirector = movies.reduceLeft(filmDirectorMinAge)
                                                  //> youngestDirector  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@7e0ea63
                                                  //| 9
  println(youngestDirector.director.name + " age:" + youngestDirector.directorsAge)
                                                  //> Christopher Nolan age:30
  val bestFilm = movies.reduceLeft(filmDirectorMaxRating)
                                                  //> bestFilm  : Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@3d24753a
  println(bestFilm.name + " rating: " + bestFilm.imdbRating)
                                                  //> Dark Knight rating: 9.0
  highPlainsDrifter.copy(name = "L'homme des hautes plaines")
                                                  //> res6: Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@2ef9b8bc
  thomasCrownAffair.copy(yearOfRelease = 1968, director = new Director("Norman", "Jewison", 1926))
                                                  //> res7: Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@5d624da6
  inception.copy().copy().copy()                  //> res8: Chapter3.Film = Chapter3$$anonfun$main$1$Film$1@1e67b872
  
  // an adder
  class Adder(amount: Int) {
    def add(in: Int) = in + amount
  }
  
  // implement a Counter class
  class Counter(i: Int = 1) {
    def inc: Counter = new Counter(i + 1)
    def dec: Counter = new Counter(i - 1)
    def adjust(a: Adder): Counter = new Counter(a.add(i))
    def count: Int = i
  }
  
  val c = new Counter(10)                         //> c  : Chapter3.Counter = Chapter3$$anonfun$main$1$Counter$2@60addb54
  c.inc.inc.inc.count                             //> res9: Int = 13
  val c2 = new Counter                            //> c2  : Chapter3.Counter = Chapter3$$anonfun$main$1$Counter$2@3f2a3a5
  c2.dec.dec.count                                //> res10: Int = -1
  
  // 3.2.3 Not sure what we are missing.
  
  
  
  
  
  
}