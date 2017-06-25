package reductions

import java.util.concurrent._
import scala.collection._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common._
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory

@RunWith(classOf[JUnitRunner]) 
class LineOfSightSuite extends FunSuite {
  import LineOfSight._
  test("lineOfSight should correctly handle an array of size 4") {
    val output = new Array[Float](4)
    lineOfSight(Array[Float](0f, 1f, 8f, 9f), output)
    assert(output.toList == List(0f, 1f, 4f, 4f))
  }


  test("upsweepSequential should correctly handle the chunk 1 until 4 of an array of 4 elements") {
    val res = upsweepSequential(Array[Float](0f, 1f, 8f, 9f), 1, 4)
    assert(res == 4f)
  }


  test("downsweepSequential should correctly handle a 4 element array when the starting angle is zero") {
    val output = new Array[Float](4)
    downsweepSequential(Array[Float](0f, 1f, 8f, 9f), output, 0f, 1, 4)
    assert(output.toList == List(0f, 1f, 4f, 4f))
  }

  test("downsweep should correctly compute the output for a non-zero starting angle") {
    val input = Array[Float](0.0f, 8.0f, 14.0f, 33.0f, 48.0f)
    val output = new Array[Float](5)
    val tree = Node(Node(Leaf(1, 2, 8.0f),Leaf(2,3,7.0f)), Node(Leaf(3, 4, 11.0f),Leaf(4,5,12.0f)))
    downsweep(input, output, 0, tree)
    assert(output.toList === List(0.0f, 8.0f, 8.0f, 11.0f, 12.0f))
  }

}

