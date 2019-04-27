package ch.fhnw.jvi.mada.crypto.dh.dto

import java.math.BigInteger
import java.util.function.UnaryOperator

data class CyclicGroup(val floor: BigInteger, val ceeling: BigInteger, val operator: UnaryOperator<BigInteger>) {
    fun pow(p: BigInteger): BigInteger {
        var result = floor
        var counter = BigInteger.ONE
        while (counter < p) {
            result = operator.apply(result)
        }
        return result
    }
}