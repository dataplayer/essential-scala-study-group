object Chapter2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  // 2.1.4
  //  type: Int
  // value: 3
  1 + 2                                           //> res0: Int(3) = 3
  
  //  type: Int
  // value: 3
  "3".toInt                                       //> res1: Int = 3
  
  //  type: Int
  // value Error
  //"foo".toInt
  
  // 2.2.5 Rewrite in Op-style
  // "foo".take(1)
  "foo" take 1                                    //> res2: String = f
  // 1 + 2 + 3
  (1).+(2).+(3)                                   //> res3: Int = 6
  // Diff and Similarities between?
  // Both are of type Int and of value 6.
  // But first is an expression and second is a literal.
  1 + 2 + 3                                       //> res4: Int = 6
  6                                               //> res5: Int(6) = 6
  
  // 2.3.8
  // Vals and types of the following
  //  type: Int
  // value: 42
  42                                              //> res6: Int(42) = 42
  //  type: Boolean
  // value: true
  true                                            //> res7: Boolean(true) = true
  //  type: Long
  // value: 123
  123L                                            //> res8: Long(123L) = 123
  //  type: Double
  // value: 42.0
  42.0                                            //> res9: Double(42.0) = 42.0
  
  // 2.4.5
  // Define objects for cats
  object Oswald {
    val colour: String = "Black"
    val food: String = "Milk"
  }
  Oswald.colour                                   //> res10: String = Black
  Oswald.food                                     //> res11: String = Milk
  
  object Henderson {
    val colour: String = "Ginger"
    val food: String = "Chips"
  }
  Henderson.colour                                //> res12: String = Ginger
  Henderson.food                                  //> res13: String = Chips
  
  object Quentin {
    val colour: String = "Tabby and White"
    val food: String = "Curry"
  }
  Quentin.colour                                  //> res14: String = Tabby and White
  Quentin.food                                    //> res15: String = Curry
  
  // Square Dance
  object calc {
    def square(arg: Double) = arg * arg
    def cube(arg: Double) = square(arg) * arg
  }
  calc.square(2)                                  //> res16: Double = 4.0
  calc.cube(2)                                    //> res17: Double = 8.0
  
  // Precise Squre Dance
  object calc2 {
    def square(arg: Int) = arg * arg
    def cube(arg: Int) = square(arg) * arg
    def square(arg: Double) = arg * arg
    def cube(arg: Double) = square(arg) * arg
  }
  calc2.square(2)                                 //> res18: Int = 4
  calc2.square(2.0)                               //> res19: Double = 4.0
  calc2.cube(2)                                   //> res20: Int = 8
  calc2.cube(2.0)                                 //> res21: Double = 8.0
  
  // Order of evaluation
  "a"                                             //> res22: String("a") = a
  "b"                                             //> res23: String("b") = b
  "c"                                             //> res24: String("c") = c
  "a"                                             //> res25: String("a") = a
  "b"                                             //> res26: String("b") = b
  //prints 3c
  // ? not too sure about order of ops here
  
  // 2.6.4
  //  type: String
  // value: predator
  if (1 > 2) "alien" else "predator"              //> res27: String = predator

  //  type: Any
  // value: 2001
  if (1 > 2) "alien" else 2001                    //> res28: Any = 2001
  
  //  type: ?
  // value: ?
  if (false) "hello"                              //> res29: Any = ()
}