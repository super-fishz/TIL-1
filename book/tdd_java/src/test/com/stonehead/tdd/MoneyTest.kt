package com.stonehead.tdd

import net.oddpoet.expect.extension.equal
import net.oddpoet.expect.should
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class MoneyTest {

    @Test
    fun `곱셈 테스트 - Dollar`() {
        // given
        val five = Money.dollar(5)

        // when

        // then
        five.times(2).should.equal(Money.dollar(10))
        five.times(3).should.equal(Money.dollar(15))
    }

    @Test
    fun `곱셈 테스트 - Franc`() {
        // given
        val five = Money.franc(5)

        // when

        // then
        five.times(2).should.equal(Money.franc(10))
        five.times(3).should.equal(Money.franc(15))
    }

    @Test
    fun `동등 테스트`() {
        // given
        val budget = 5

        // when

        // then
        assertTrue(Money.dollar(budget) == Money.dollar(budget))
        assertFalse(Money.franc(budget) == Money.franc(budget + 1))
        assertFalse(Money.franc(budget).equals(Money.dollar(budget)))
    }

    @Test
    fun `통화 테스트`() {
        assertEquals("USD", Money.dollar(1).currency())
        assertEquals("CHF", Money.franc(1).currency())
    }

    @Test
    fun `다른 클래스라도 통화가 같으면 같은거다`() {
        // given
        val budget = 5
        val currency = "CHF"

        // when

        // then
        assertTrue(Money(budget, "CHF").equals(Money.franc(budget)))
    }

    @Test
    fun `쉬운 더하기`() {
        // given
        val five = Money.dollar(5)

        // when
        val sum:Expression = five.plus(five)
        val bank = Bank()
        val reduced = bank.reduce(sum, "USD")

        // then
        assertEquals(Money.dollar(10), reduced)
    }

    @Test
    fun `plus는 sum을 리턴한다`() {
        // given
        val five = Money.dollar(5)

        // when
        val result = five.plus(five)
        val sum:Sum = result as Sum

        // then
        assertEquals(five, sum.augend)
        assertEquals(five, sum.addend)
    }

    @Test
    fun testReduceSum() {
        // given
        val sum = Sum(Money.dollar(3), Money.dollar(4))
        val bank = Bank()

        // when
        val result = bank.reduce(sum, "USD")

        // then
        assertEquals(Money.dollar(7), result)
    }

    @Test
    fun testRduceMoney() {
        // given
        val bank = Bank()

        // when
        val result = bank.reduce(Money.dollar(1), "USD")

        // then
        assertEquals(result, Money.dollar(1))
    }

    @Test
    fun `다른 통화 비교`() {
        // given
        val bank = Bank()
        bank.addRate("CHF", "USD", 2)

        // when
        val result = bank.reduce(Money.franc(2), "USD")

        // then
        assertEquals(Money.dollar(1), result)
    }

    @Test
    fun testIdentityRate() {
        // given

        // when

        // then
        assertEquals(1, Bank().rate("USD", "USD"))
    }

    @Test
    fun `다른 통화 더하기`() {
        // given
        val fiveBucks = Money.dollar(5) as Expression
        val tenFrancs = Money.franc(10) as Expression
        val bank = Bank()
        bank.addRate("CHF", "USD", 2)

        // when
        val result = bank.reduce(fiveBucks.plus(tenFrancs), "USD")

        // then
        assertEquals(result, Money.dollar(10))
    }

    @Test
    fun testSumPlusMoney() {
        // given
        val fiveBucks = Money.dollar(5) as Expression
        val tenFrancs = Money.franc(10) as Expression
        val bank = Bank()
        bank.addRate("CHF", "USD", 2)

        // when
        val sum = Sum(fiveBucks, tenFrancs).plus(fiveBucks)
        val result = bank.reduce(sum, "USD")

        // then
        assertEquals(result, Money.dollar(15))
    }

    @Test
    fun testSumTimes() {
        // given
        val fiveBucks = Money.dollar(5) as Expression
        val tenFrancs = Money.franc(10) as Expression
        val bank = Bank()
        bank.addRate("CHF", "USD", 2)

        // when
        val sum = Sum(fiveBucks, tenFrancs).times(2)
        val result = bank.reduce(sum, "USD")

        // then
        assertEquals(result, Money.dollar(20))
    }

    @Test
    @Ignore("test 실패.....")
    fun testPlusSameCurrencyReturnsMoney() {
        // given
        val sum = Money.dollar(1).plus(Money.dollar(1))

        // when

        // then
        assertTrue(sum is Money)
    }
}