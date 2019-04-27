package ch.fhnw.jvi.mada.crypto.dh.dto

import java.math.BigInteger

data class ElgamalKey(val group: CyclicGroup, val generator: BigInteger, val gb: BigInteger)