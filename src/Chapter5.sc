object Chapter5 {
  println("Sequencing Computations")              //> Sequencing Computations
  
  // 5.1.3.1 Generic List
  
  sealed trait LinkedList[A] {
    def length: Int = this match {
      case End() => 0
      case Pair(h,t) => 1 + t.length
    }
    def contains(i: Int): Boolean = this match {
      case End() => false
      case Pair(h,t) => if (h == i) true else t.contains(i)
    }
    def apply(i: Int): Result[A] = this match {
      case End() => Failure("Index out of bounds!")
      case Pair(h,t) => if (i == 0) Success(h) else t(i - 1)
    }
  }
  final case class End[A]() extends LinkedList[A]
  final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
  /*
  // 5.1.3.2 Working With Generic Types
  // Implement length() above
  val example = Pair(1, Pair(2, Pair(3, End())))
  assert(example.length == 3)
  assert(example.tail.length == 2)
  assert(End().length == 0)
  
  // Implement contains() above
  assert(example.contains(3) == true)
  assert(example.contains(4) == false)
  assert(End().contains(0) == false)
  // Implement apply() above
  // Change apply to return a Result with a failure case
  // after adding the type below and editing apply() we test for result types
  assert(example(0) == Success(1))
  assert(example(1) == Success(2))
  assert(example(2) == Success(3))
  assert(example(3) == Failure("Index out of bounds!"))
  // use the following type to return failure or success
  */
  sealed trait Result[A]
  case class Success[A](result: A) extends Result[A]
  case class Failure[A](reason: String) extends Result[A]
 
  // 5.2.3.1 A Better Abstraction
  sealed trait IntList {
    def fold[A](end: A, f: (Int,A) => A): A = this match {
      case IEnd => end
      case IPair(h,t) => f(h,t.fold(end,f))
    }
    def sum: Int        = fold[Int](0,(x,y) => x + y)
    def product: Int    = fold[Int](1,(x,y) => x * y)
    def length: Int     = fold[Int](0,(_,y) => 1 + y)
    def double: IntList = fold[IntList](IEnd,(x,y) => IPair(x * 2,y))
    
  }
  final case object IEnd extends IntList
  final case class IPair(head: Int, tail: IntList) extends IntList
  
  
  // 5.3.4.1 Tree
  sealed trait Tree[A] {
    def fold[B](leaf: A => B,node: (B,B) => B): B
    override def toString: String = this.fold[String](A => A.toString, (a,b) => a + " " + b)
  }
  final case class Leaf[A](elem: A) extends Tree[A] {
    def fold[B](leaf: A => B,node: (B,B) => B): B = leaf(elem)
  }
  final case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
    def fold[B](leaf: A => B,node: (B,B) => B): B = node(left.fold(leaf,node),right.fold(leaf,node))
  }
  val tree: Tree[String] = Node(Node(Leaf("To"), Leaf("iterate")),
      Node(Node(Leaf("is"), Leaf("human,")),
      Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))
                                                  //> tree  : Chapter5.Tree[String] = To iterate is human, to recurse divine
      
  tree.fold[String](str => str,(a, b) => a + " " + b)
                                                  //> res0: String = To iterate is human, to recurse divine

  // 5.4.1.1 Pairs (Generic Product Types)
  //case class Pair[A,B](one: A, two: B)
  //val p = Pair[Int,String](1,"one")
  
  // 5.4.3.1 Generic Sum Types
  sealed trait Sum[A,B] {
    def fold[C](left: A => C, right: B => C): C = this match {
      case Left(a) => left(a)
      case Right(b) => right(b)
    }
  }
  final case class Left[A,B](value: A) extends Sum[A,B]
  final case class Right[A,B](value: B) extends Sum[A,B]
  
  Left[Int,String](1).value                       //> res1: Int = 1
  Right[Int,String]("one").value                  //> res2: String = one
  val sum: Sum[Int, String] = Right("foo")        //> sum  : Chapter5.Sum[Int,String] = Right(foo)
  sum match {
    case Left(x) => x.toString
    case Right(x) => x
  }                                               //> res3: String = foo
  
  // 5.4.4.1 Mayve that was a mistake
  sealed trait Maybe[A] {
    def fold[B](empty: B, full: A => B): B = this match {
      case Empty() => empty
      case Full(a) => full(a)
    }
    def map[B](f: A => B): Maybe[B] = this match {
      case Empty() => Empty[B]()
      case Full(a) => Full(f(a))
    }
    def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match {
      case Empty() => Empty[B]()
      case Full(a) => f(a)
    }
    def map2[B](f: A => B): Maybe[B] = flatMap[B](v => Full(f(v)))
  }
  final case class Empty[A]() extends Maybe[A]
  final case class Full[A](a: A) extends Maybe[A]
  
  //val perhaps: Maybe[Int] = Empty[Int]
  //val perhaps2: Maybe[Int] = Full(1)
  
  
  // 5.4.6.1 Generics versus Traits
  // 5.4.6.2 Folding Maybe (see above)
  // 5.4.6.3 Folding Sum (see above)
  // 5.5.4.1 Mapping Lists (something is off about the Empty at the end
  //val list: LinkedList[Maybe[Int]] = Pair(Full(1), Pair(Full(2), Pair(Full(3), Empty)))
  // 5.5.4.2 Mapping Maybe: Implement map for Maybe
  // 5.5.4.3 Sequencing Computations
  val list = List(1,2,3)                          //> list  : List[Int] = List(1, 2, 3)
  list.flatMap(x => List(x,-x))                   //> res4: List[Int] = List(1, -1, 2, -2, 3, -3)
  //val list22 = List(Full(3), Full(2), Full(1))
  // 5.5.4.4 Sum
  sealed trait Sum2[A,B] {
    def fold[C](left: A => C, right: B => C): C = this match {
      case Failure2(a) => left(a)
      case Success2(b) => right(b)
    }
    def map[C](f: B => C): Sum2[A,C] = this match {
      case Failure2(a) => Failure2(a)
      case Success2(b) => Success2(f(b))
    }
    def flatMap[C](f: B => Sum2[A,C]): Sum2[A,C] = this match {
      case Failure2(a) => Failure2(a)
      case Success2(b) => f(b)
    }
  }
  final case class Failure2[A,B](value: A) extends Sum2[A,B]
  final case class Success2[A,B](value: B) extends Sum2[A,B]
  




}