package reductions

import java.util.concurrent._
import scala.collection._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common._

import ParallelCountChange._

@RunWith(classOf[JUnitRunner])
class ParallelCountChangeSuite extends FunSuite {

  test("countChange should return 0 for money < 0") {
    def check(money: Int, coins: List[Int]) = 
      assert(countChange(money, coins) == 0,
        s"countChang($money, _) should be 0")

    check(-1, List())
    check(-1, List(1, 2, 3))
    check(-Int.MinValue, List())
    check(-Int.MinValue, List(1, 2, 3))
  }

  test("countChange should return 1 when money == 0") {
    def check(coins: List[Int]) = 
      assert(countChange(0, coins) == 1,
        s"countChang(0, _) should be 1")

    check(List())
    check(List(1, 2, 3))
    check(List.range(1, 100))
  }

  test("countChange should return 0 for money > 0 and coins = List()") {
    def check(money: Int) = 
      assert(countChange(money, List()) == 0,
        s"countChang($money, List()) should be 0")

    check(1)
    check(Int.MaxValue)
  }

  test("countChange should work when there is only one coin") {
    def check(money: Int, coins: List[Int], expected: Int) =
      assert(countChange(money, coins) == expected,
        s"countChange($money, $coins) should be $expected")

    check(1, List(1), 1)
    check(2, List(1), 1)
    check(1, List(2), 0)
    check(Int.MaxValue, List(Int.MaxValue), 1)
    check(Int.MaxValue - 1, List(Int.MaxValue), 0)
  }

  test("countChange should work for multi-coins") {
    def check(money: Int, coins: List[Int], expected: Int) =
      assert(countChange(money, coins) == expected,
        s"countChange($money, $coins) should be $expected")

    check(50, List(1, 2, 5, 10), 341)
    check(250, List(1, 2, 5, 10, 20, 50), 177863)
  }

  test("moneyThreshold tests") {
    def check(money: Int, coins: List[Int], startingMoney: Int, expected: Boolean) =
      assert(moneyThreshold(startingMoney)(money, coins) == expected,
        s"moneyThreshold($startingMoney) with($money, $coins) should be $expected")

    check(50, List(1, 2, 5, 10), 50, false)
    check(50*2/3+1, List(1, 2, 5, 10), 50, false)
    check(50*2/3, List(1, 2, 5, 10), 50, true)
    check(50*2/3-1, List(1, 2, 5, 10), 50, true)
    check(1, List(1, 2, 5, 10), 50, true)
  }

  test("totalCoinsThreshold tests") {
    def check(money: Int, coins: List[Int], totalCoins: Int, expected: Boolean) =
      assert(totalCoinsThreshold(totalCoins)(money, coins) == expected,
        s"totalCoinsThreshold($totalCoins) with($money, $coins) should be $expected")

    check(50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), 9, false)
    check(50, List(1, 2, 5, 10, 20, 50, 100), 9, false)
    check(50, List(1, 2, 5, 10, 20, 50), 9, true)
    check(50, List(1, 2, 5, 10, 20), 9, true)
    check(50, List(1, 2, 5, 10), 9, true)
  }

  test("combinedThreshold tests") {
    def check(money: Int, coins: List[Int], startingMoney: Int, allCoins: List[Int], expected: Boolean) =
      assert(combinedThreshold(startingMoney,allCoins)(money, coins) == expected,
        s"combinedThreshold($startingMoney,$allCoins) with($money, $coins) should be $expected")

    check(50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), 50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), false)
    check(113, List(1, 100), 50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), false)
    check(45, List(5, 10, 20, 50, 100), 50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), true)
    check(28, List(1, 5, 10, 50, 100, 200, 500, 1000), 50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), true)
    check(5, List(1, 2, 10, 50, 100, 200, 500), 50, List(1, 2, 5, 10, 20, 50, 100, 200, 500), true)
  }


}
