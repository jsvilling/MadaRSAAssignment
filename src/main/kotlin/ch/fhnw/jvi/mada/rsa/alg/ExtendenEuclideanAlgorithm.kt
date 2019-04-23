package ch.fhnw.jvi.mada.rsa.alg

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

/**
 * Implementation of the extenden eucledian algorithm.
 *
 * To calculate the results of an algorithm create a new instance of this class using the two integers a and b. The
 * algorithm will be initialized with those two numbers. However the result will not be calculated immediately.
 * Instead it will be lazily calculated the first time one of the result properties is accesed.
 */
class ExtendenEuclideanAlgorithm(val a: BigInteger, val b: BigInteger) {

    val result by lazy { calculate() }
    val gcd by lazy { result[1] }
    val x0 by lazy { result[2] }
    val y0 by lazy { result[3] }
    val bezoutCoefficients by lazy { result.copyOfRange(2, 4) }

    /**
     * This function is used to calculate the result of the algorithm. It uses the the values a and b with which the
     * Algorithm was initialized. Calculation is done using an array of BigInteger values. Each row is used to
     * calculate the next row untill calculation is done. Only the last row of the calculation is saved. All other rows
     * are discarded when the next row is calculated.
     */
    private fun calculate(): Array<BigInteger> {
        var s = createInitialRow(a, b)
        while (!isDone(s)) {
            s = calculateNextRow(s)
        }
        return arrayOf(s[1], s[7], s[4], s[5])
    }

    /**
     * Creates the first row. This will be the basis for calculating the result.
     */
    private fun createInitialRow(a: BigInteger, b: BigInteger): Array<BigInteger> {
        return arrayOf(a, b, ONE, ZERO, ZERO, ONE, a.div(b), a.mod(b))
    }

    /**
     * Checks whether the calculation is done. Calculation is done if b' is 0 r will be 0 in the next iteration.
     */
    private fun isDone(row: Array<BigInteger>): Boolean {
        return row[1].equals(ZERO) || row[7].equals(ZERO)
    }

    /**
     * Calculates the next row based on the current one. This method follows the rules of the extenden eucledian algorithm.
     * a' -> b' of current row
     * b' -> r of current row
     * x0 -> x1 of current  row
     * y0 -> y1 of current  row
     * x1 -> see this#nextX1
     * y1 -> see this#nextY1
     * q -> a' div b' (using valus of current row)
     * e -> a' mod b' (using valus of current  row)
     */
    private fun calculateNextRow(r: Array<BigInteger>): Array<BigInteger> {
        return arrayOf(r[1], r[7], r[4], r[5], nextX1(r), nextY1(r), r[1].div(r[7]), r[1].mod(r[7]))
    }

    /**
     * Calculates x1 for the next row based on the current one.
     * x1 -> x0 - x1 * q (using valus of current  row)
     */
    private fun nextX1(r: Array<BigInteger>): BigInteger {
        return r[2].subtract(r[6].multiply(r[4]))
    }

    /**
     * Calculates x1 for the next row based on the current one.
     * x1 -> x0 - x1 * q (using valus of current  row)
     */
    private fun nextY1(r: Array<BigInteger>): BigInteger {
        return r[3].subtract(r[6].multiply(r[5]))
    }
}