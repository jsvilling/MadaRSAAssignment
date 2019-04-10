package rsa.alg

import java.math.BigInteger
import java.math.BigInteger.*

class FastExponentiation(val x: BigInteger, val e: BigInteger, val m: BigInteger) {

    val result: BigInteger by lazy { generate() }

    private fun generate(): BigInteger {
        var i = e.bitCount() - 1
        var h = ONE
        var k = x

        while (i >= 0) {
            if(x.testBit(i)) {
                h = h.multiply(k).mod(m)
            }
            k = k.modPow(TWO, m)
            i--
        }
        return h
    }
}