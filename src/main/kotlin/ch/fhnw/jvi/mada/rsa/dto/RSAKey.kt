package ch.fhnw.jvi.mada.rsa.dto

import ch.fhnw.jvi.mada.util.StringConstants
import java.math.BigInteger

/**
 * Data class for an RSA Key. As far as this class is concerned it is not differentiated between public and private keys.
 * Both keys are just stored as a combination of two numbers n and e.
 *
 * The class contains two immutable properties representing the numbers of an RSA Key. Neither of the properties are
 * validated by this class! It is the responsibility of the caller to assure the Key is created with valid values.
 *
 * To create a valid RSA Key the values have to meet the following criteria:
 *
 * @param n: Has to be the product of two distinct primes
 * @param ed: Has to be a number which is element of Z*n.
 */
data class RSAKey(val n: BigInteger, val ed: BigInteger) {

    /**
     * This String representation is less pretty than the default created by the data class method. However we need
     * to create the String like this to adhere to the specification.
     */
    override fun toString(): String {
        return StringConstants.RSA_KEY_START_TOKEN
            .plus(n).plus(StringConstants.DEFAULT_DELIMITER)
            .plus(ed).plus(StringConstants.RSA_KEY_END_TOKEN)
    }
}