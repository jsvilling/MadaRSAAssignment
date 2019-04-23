package ch.fhnw.jvi.mada.rsa.service

import ch.fhnw.jvi.mada.rsa.dto.RSAKey
import ch.fhnw.jvi.mada.util.FileUtils
import ch.fhnw.jvi.mada.util.StringConstants.DEFAULT_DELIMITER
import ch.fhnw.jvi.mada.util.StringConstants.PRIVATE_KEY_NAME
import ch.fhnw.jvi.mada.util.StringConstants.PUBLIC_KEY_NAME
import ch.fhnw.jvi.mada.util.StringConstants.RSA_KEY_END_TOKEN
import ch.fhnw.jvi.mada.util.StringConstants.RSA_KEY_START_TOKEN
import java.io.File
import java.math.BigInteger

class RSAKeyFileService {

    fun readKeyFromFile(keyFileName: String): RSAKey {
        val file = File(keyFileName)
        val keyString = file.readText().removeSurrounding(RSA_KEY_START_TOKEN, RSA_KEY_END_TOKEN)
        val keyValues = keyString.split(DEFAULT_DELIMITER)
        return RSAKey(BigInteger(keyValues[0]), BigInteger(keyValues[1]))
    }

    fun persistKeyPair(keyPair: Pair<RSAKey, RSAKey>) {
        persistKey(PUBLIC_KEY_NAME, keyPair.first)
        persistKey(PRIVATE_KEY_NAME, keyPair.second)
    }

    private fun persistKey(keyFileName: String, key: RSAKey) {
        val file = FileUtils.createFile(keyFileName)
        FileUtils.clearFileContens(file)
        FileUtils.writeToFile(key.toString(), file)
    }
}