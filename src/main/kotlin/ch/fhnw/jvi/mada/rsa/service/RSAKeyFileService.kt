package ch.fhnw.jvi.mada.rsa.service

import ch.fhnw.jvi.mada.rsa.dto.RSAKey
import ch.fhnw.jvi.mada.util.StringConstants.DEFAULT_DELIMITER
import ch.fhnw.jvi.mada.util.StringConstants.PRIVATE_KEY_NAME
import ch.fhnw.jvi.mada.util.StringConstants.PUBLIC_KEY_NAME
import java.io.File
import java.math.BigInteger

class RSAKeyFileService {

    fun persistKeyPair(keyPair: Pair<RSAKey, RSAKey>) {
        persistKey(PUBLIC_KEY_NAME, keyPair.first)
        persistKey(PRIVATE_KEY_NAME, keyPair.second)
    }

    fun readKeyFromFile(keyFileName: String): RSAKey {
        val file = File(keyFileName)
        val keyString = file.readText()
        val keyValues = keyString.split(DEFAULT_DELIMITER)
        return RSAKey(BigInteger(keyValues[0]), BigInteger(keyValues[1]))
    }

    private fun persistKey(keyFileName: String, key: RSAKey) {
        val file = File(keyFileName)
        file.createNewFile()
        file.writeText(key.toString())
    }
}