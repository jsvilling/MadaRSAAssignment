package ch.fhnw.jvi.mada.rsa.alg

import java.math.BigInteger
import java.math.BigInteger.*

class FastExponentiation(val x: BigInteger, val e: BigInteger, val m: BigInteger) {

    val result: BigInteger by lazy { calculate() }

    private var h = ONE
    private var k = x

    private fun calculate(): BigInteger {
        for (i in 0..e.bitLength()) {
            if(e.testBit(i)) {
                h = h.multiply(k).mod(m)
            }
            k = k.multiply(k).mod(m)
        }
        return h
    }
}