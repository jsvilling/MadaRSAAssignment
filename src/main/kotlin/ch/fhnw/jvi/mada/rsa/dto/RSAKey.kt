package ch.fhnw.jvi.mada.rsa.dto

import ch.fhnw.jvi.mada.util.StringConstants
import java.math.BigInteger

data class RSAKey(val n: BigInteger, val ed: BigInteger) {

    override fun toString(): String {
        return StringConstants.RSA_KEY_START_TOKEN
                .plus(n).plus(StringConstants.DEFAULT_DELIMITER)
                .plus(ed).plus(StringConstants.RSA_KEY_END_TOKEN)
    }
}