object ADataModel {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  case class Metadata(md: Map[String,String] = Map("" -> ""))
  
  sealed trait Variable
  
  case class Scalar(v: Variable, md: Metadata = Metadata()) extends Variable
  case class Tuple(t: List[Variable], md: Metadata = Metadata()) extends Variable
  case class Function(domain: Tuple, range: Tuple, md: Metadata = Metadata()) extends Variable
  
  case class Time(t: Int, md: Metadata = Metadata()) extends Variable
  case class Real(r: Double, md: Metadata = Metadata()) extends Variable
  case class Integer(i: Integer, md: Metadata = Metadata()) extends Variable
  case class Text(t: String, md: Metadata = Metadata()) extends Variable
  
  case class Dataset(variable: Variable, metadata: Metadata = Metadata()) extends Variable

  
  val x = Real(2)                                 //> x  : ADataModel.Real = Real(2.0,Metadata(Map( -> )))
  println(x)                                      //> Real(2.0,Metadata(Map( -> )))
  
  val f = Function(
        Tuple(List(Real(1),Real(2))),
        Tuple(List(Text("a"),Text("b"))))         //> f  : ADataModel.Function = Function(Tuple(List(Real(1.0,Metadata(Map( -> )))
                                                  //| , Real(2.0,Metadata(Map( -> )))),Metadata(Map( -> ))),Tuple(List(Text(a,Meta
                                                  //| data(Map( -> ))), Text(b,Metadata(Map( -> )))),Metadata(Map( -> ))),Metadata
                                                  //| (Map( -> )))
  val ds = Dataset(f)                             //> ds  : ADataModel.Dataset = Dataset(Function(Tuple(List(Real(1.0,Metadata(Map
                                                  //| ( -> ))), Real(2.0,Metadata(Map( -> )))),Metadata(Map( -> ))),Tuple(List(Tex
                                                  //| t(a,Metadata(Map( -> ))), Text(b,Metadata(Map( -> )))),Metadata(Map( -> ))),
                                                  //| Metadata(Map( -> ))),Metadata(Map( -> )))
  ds.variable.asInstanceOf[Function].range        //> res0: ADataModel.Tuple = Tuple(List(Text(a,Metadata(Map( -> ))), Text(b,Met
                                                  //| adata(Map( -> )))),Metadata(Map( -> )))
  
  
}