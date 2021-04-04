package com.softensity.prime

import com.softensity.prime.service.PrimeService
import org.scalatest.funsuite.AnyFunSuite

class PrimeServiceTest extends AnyFunSuite {
  val service: PrimeService = PrimeModule.service

  test("PrimeService.sieveNumbers and PrimeService.sieveNumbers should return an equal result") {
    val number = 11
    val sieveList = service.sieveNumbers(number)
    val primeList = service.primeNumbers(number)
    assert(primeList == sieveList && primeList == List(2, 3, 5, 7, 11))
  }
}
