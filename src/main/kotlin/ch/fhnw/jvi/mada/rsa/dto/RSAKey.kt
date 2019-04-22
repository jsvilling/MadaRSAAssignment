package ch.fhnw.jvi.mada.rsa.dto

import java.math.BigInteger

data class RSAKey(val n: BigInteger, val ed: BigInteger)