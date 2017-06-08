import common.task

val splittingPoints:List[Int]=(0 until 5 by 2).toList
var tuples:List[(Int,Int)] = splittingPoints.zip(splittingPoints.tail) ++ List((splittingPoints.last,5))
tuples++List((1,1))
val pepe = (0 until 4 by 2).toList
pepe.zip(pepe.tail)

val computation = task {
  val result = 1 + 1
  println("Done!")
  result
}
println("About to wait for some heavy calculation...")
computation.join()
