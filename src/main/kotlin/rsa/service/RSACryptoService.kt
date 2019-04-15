package rsa.service

import util.FileUtils.clearFileContens
import util.FileUtils.createFile
import util.FileUtils.writeToFile
import rsa.alg.FastExponentiation
import rsa.dto.RSAKey
import java.math.BigInteger

import util.StringConstants.CIPHER_FILE_NAME
import util.StringConstants.DEFAULT_DELIMITER
import util.StringConstants.PRIVATE_KEY_NAME
import util.StringConstants.PUBLIC_KEY_NAME
import util.StringConstants.TEXT_FILE_NAME

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
            .map { s -> decryptToChar(s, sk) }
            .filter(String::isNotBlank)
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