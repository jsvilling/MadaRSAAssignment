package ch.fhnw.jvi.mada.rsa.alg

import java.math.BigInteger
import java.math.BigInteger.ONE

/**
 * Implementation of the fast exponentiation algorithm.
 *
 * To calculate the results of an algorithm create a new instance of this class using the BigInteger values x, e and m,
 * This algorithm will calcualte the result of x^e mod m.
 *
 * The Algorithm is initialized with the numbers passed to the constructor. However the result will not be calculated
 * immediately. Instead it will be lazily calculated the first time one of the result properties is accesed.
 */
class FastExponentiation(val x: BigInteger, val e: BigInteger, val m: BigInteger) {

    val result: BigInteger by lazy { calculate() }

    private var h = ONE
    private var k = x

    /**
     * This function is used to calculate the result of the algorithm.
     */
    private fun calculate(): BigInteger {
        // Iterate over all bits of e. Exluding the sign bit
        for (i in 0..e.bitLength()) {
            // If the current bit is 1 recalculate h
            if (e.testBit(i)) {
                h = h.multiply(k).mod(m)
            }
            // In any case recalculate k
            k = k.multiply(k).mod(m)
        }
        return h
    }
}