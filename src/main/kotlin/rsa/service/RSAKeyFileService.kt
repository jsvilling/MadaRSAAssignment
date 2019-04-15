package rsa.service

import rsa.dto.RSAKey
import util.StringConstants.DEFAULT_DELIMITER
import util.StringConstants.PRIVATE_KEY_NAME
import util.StringConstants.PUBLIC_KEY_NAME
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
        val keyString = generateKeyString(key)
        file.createNewFile()
        file.writeText(keyString)
    }

    private fun generateKeyString(key: RSAKey): String {
        return "".plus(key.n).plus(",").plus(key.ed)
    }
}