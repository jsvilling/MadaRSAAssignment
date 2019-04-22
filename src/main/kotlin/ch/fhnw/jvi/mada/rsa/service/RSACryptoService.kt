package ch.fhnw.jvi.mada.rsa.service

import ch.fhnw.jvi.mada.util.FileUtils.clearFileContens
import ch.fhnw.jvi.mada.util.FileUtils.createFile
import ch.fhnw.jvi.mada.util.FileUtils.writeToFile
import ch.fhnw.jvi.mada.rsa.alg.FastExponentiation
import ch.fhnw.jvi.mada.rsa.dto.RSAKey
import java.math.BigInteger

import ch.fhnw.jvi.mada.util.StringConstants.CIPHER_FILE_NAME
import ch.fhnw.jvi.mada.util.StringConstants.DEFAULT_DELIMITER
import ch.fhnw.jvi.mada.util.StringConstants.PRIVATE_KEY_NAME
import ch.fhnw.jvi.mada.util.StringConstants.PUBLIC_KEY_NAME
import ch.fhnw.jvi.mada.util.StringConstants.TEXT_FILE_NAME

class RSACryptoService(val fileService: RSAKeyFileService = RSAKeyFileService()) {

    val textFile = createFile(TEXT_FILE_NAME)
    val cipherFile = createFile(CIPHER_FILE_NAME)

    fun encrypt() {
        val pk = fileService.readKeyFromFile(PUBLIC_KEY_NAME)
        val text = textFile.readText()
        clearFileContens(cipherFile)

        text.chars()
            .mapToObj { c -> encryptFromChar(c, pk) }
            .forEach { c -> writeToFile(c, cipherFile) }
    }

    fun decrypt() {
        val sk = fileService.readKeyFromFile(PRIVATE_KEY_NAME)
        val cipher = cipherFile.readText()
        clearFileContens(textFile)

        cipher.split(DEFAULT_DELIMITER).stream()
            .filter(String::isNotBlank)
            .map { s -> decryptToChar(s, sk) }
            .forEach { c -> writeToFile(c, textFile) }
    }

    private fun encryptFromChar(c: Int, pk: RSAKey): BigInteger {
        val x = c.toBigInteger()
        return FastExponentiation(x, pk.ed, pk.n).result
    }

    private fun decryptToChar(s: String, sk: RSAKey): String {
        val x = BigInteger(s)
        val result = FastExponentiation(x, sk.ed, sk.n).result
        return result.toInt().toChar().toString()
    }
}