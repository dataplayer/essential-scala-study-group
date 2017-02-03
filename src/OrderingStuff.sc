object OrderingStuff {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  /*
  trait Number
  trait Variable extends Ordered[Variable] {
    def compare(that: Variable): Int = (this,that) match {
      case (Integer(l1), Integer(l2)) => l1 compare l2
    }
  }

  trait Scalar extends Variable
  
  case class Integer(i: Int) extends Scalar with Number
  
  //object Integer {
  //  implicit def IntegerOrdering: Ordering[Integer] = Ordering.by(_.i)
  //}
  
  val x: Seq[Variable] = Seq(Integer(2),Integer(1), Integer(-2))
  
  x.sorted
  */
  
  trait Number
  trait Variable[A <: Variable[A]] extends Ordered[A] {
    override def compare(that: A): Int = this compare that
  }
  sealed trait Scalar
  case class Integer(i: Int) extends Scalar with Number with Variable[Integer] {
    override def compare (that: Integer): Int = this.i compare that.i
  }
  case class Real(i: Int) extends Scalar with Number with Variable[Real] {
    override def compare (that: Real): Int = this.i compare that.i
  }
  val x = Seq(Integer(2),Integer(1), Integer(-2)) //> x  : Seq[OrderingStuff.Integer] = List(Integer(2), Integer(1), Integer(-2))
                                                  //| 
  x.sorted                                        //> res0: Seq[OrderingStuff.Integer] = List(Integer(-2), Integer(1), Integer(2)
                                                  //| )
  val mixed = Seq(Integer(2),Real(-10), Real(100))//> mixed  : Seq[OrderingStuff.Variable[_ >: OrderingStuff.Real with OrderingSt
                                                  //| uff.Integer <: OrderingStuff.Variable[_ >: OrderingStuff.Real with Ordering
                                                  //| Stuff.Integer <: Product with Serializable with OrderingStuff.Number with O
                                                  //| rderingStuff.Scalar] with Product with Serializable with OrderingStuff.Numb
                                                  //| er with OrderingStuff.Scalar] with Product with Serializable with OrderingS
                                                  //| tuff.Number with OrderingStuff.Scalar] = List(Integer(2), Real(-10), Real(1
                                                  //| 00))
  
  // Doesn't work
  // mixed.sorted
                                         
}