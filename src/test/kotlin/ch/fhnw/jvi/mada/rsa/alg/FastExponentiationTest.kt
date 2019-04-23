package ch.fhnw.jvi.mada.rsa.alg

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.*

class FastExponentiationTest {

    @Test
    fun testFastExponentiation_SmallNumbers() {
        // Given
        val x = BigInteger("5")
        val e = BigInteger("37")
        val m = BigInteger("77")

        // When
        val actual = FastExponentiation(x, e, m).result

        // Then
        val expected = BigInteger("47")
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testFastExponentiation_LargerNumbers() {
        // Given
        val x = BigInteger("546131564851321354686513213548465421351")
        val e = BigInteger("12345684512351546845132151564213515486")
        val m = BigInteger("3561568651313531215684653213154685513")

        // When
        val actual = FastExponentiation(x, e, m).result

        // Then
        val expected = BigInteger("2559091474779831762075635995726432871")
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testFastExponentiation_RandomLargerNumbers() {
        // Given
        val random = Random()
        val x = BigInteger(2048, random)
        val e = BigInteger(2048, random)
        val m = BigInteger(2047, random) // One bit smaller so it is guaranteed smaller than x and e

        // When
        val actual = FastExponentiation(x, e, m).result

        // Then
        val expected = x.modPow(e, m)
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}