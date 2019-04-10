package rsa

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class ExtendenEuclideanAlgorithm(val a: BigInteger, val b: BigInteger) {

    val result by lazy { calculate() }
    val gcd by lazy { result[1] }
    val x0 by lazy { result[2] }
    val y0 by lazy { result[3] }
    val bezoutCoefficients by lazy { result.copyOfRange(2, 4) }

    private fun calculate(): Array<BigInteger> {
        var s = createInitialRow(a, b)
        while (!isDone(s)) {
            s = calculateNextRow(s)
        }
        return arrayOf(s[1], s[7], s[4], s[5])
    }

    private fun isDone(row: Array<BigInteger>): Boolean {
        return row[1].equals(ZERO) || row[7].equals(ZERO)
    }

    private fun createInitialRow(a: BigInteger, b: BigInteger): Array<BigInteger> {
        return arrayOf(a, b, ONE, ZERO, ZERO, ONE, a.div(b), a.mod(b))
    }

    private fun calculateNextRow(r: Array<BigInteger>): Array<BigInteger> {
        return arrayOf(r[1], r[7], r[4], r[5], nextX1(r), nextY1(r), r[1].div(r[7]), r[1].mod(r[7]))
    }

    private fun nextX1(r: Array<BigInteger>): BigInteger {
        return r[2].subtract(r[6].multiply(r[4]))
    }

    private fun nextY1(r: Array<BigInteger>): BigInteger {
        return r[3].subtract(r[6].multiply(r[5]))
    }
}