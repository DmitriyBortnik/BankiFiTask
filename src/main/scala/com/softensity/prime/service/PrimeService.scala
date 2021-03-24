package com.softensity.prime.service

import scala.annotation.tailrec

class PrimeService {

  def sieveNumbers(number: Int): List[Int] = {
    @tailrec
    def go(xs: List[Int], acc: List[Int]): List[Int] = xs match {
      case y :: ys =>
        if (y*y > number) acc.reverse ::: (y :: ys)
        else go(filterFactors(y, ys), y :: acc)
      case Nil => Nil
    }

    go((2 to number).toList, Nil)
  }

  def primeNumbers(number: Int): List[Int] = {
    (2 to number).filter(isPrime).toList
  }

  private def filterFactors(seed: Int, xs: List[Int]): List[Int] = {
    xs.filter(x => x % seed != 0)
  }

  private def isPrime(number: Int): Boolean = {
    if (number <= 1) false
    else if (number == 2) true
    else !(2 until number - 1).exists(x => number % x == 0)
  }

}
