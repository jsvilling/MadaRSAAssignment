package rsa.alg

import java.math.BigInteger
import java.math.BigInteger.*

class FastExponentiation(val x: BigInteger, val e: BigInteger, val m: BigInteger) {

    val result: BigInteger by lazy { generate() }

    private fun generate(): BigInteger {
        var h = ONE
        var k = x

        for (i in 0..e.bitCount()) {
            if(e.testBit(i)) {
                h = h.multiply(k).mod(m)
            }
            k = k.modPow(TWO, m)
        }
        return h
    }
}