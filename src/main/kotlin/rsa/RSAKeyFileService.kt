package rsa

import rsa.dto.RSAKey
import java.io.File
import java.math.BigInteger

class RSAKeyFileService {

    fun persistKeyPair(keyPair: Pair<RSAKey, RSAKey>) {
        persistKey("sk.txt", keyPair.first)
        persistKey("pk.txt", keyPair.second)
    }

    fun readKeyFromFile(keyFileName: String): RSAKey {
        val file = File(keyFileName)
        val keyString = file.readText()
        val keyValues = keyString.split(",")
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