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

/**
 * Service to encrypt and decrypt Textfiles using RSA. The service uses the following files in the working directory:
 *
 * <ul>
 *     <li>sk.txt       - Textfile containing the secret / private key which is used or decryption</li>
 *     <li>pk.txt       - Textfile containing th public key which is used for encryption</li>
 *     <li>text.txt     - Textfile used as source for encryption and as target for decryption</li>
 *     <li>cipher.txt   - Textfile used as source for decryption and as target for encryption</li>
 * </ul>
 *
 * All files that are not present when used will be created by the service.
 *
 * @param fileSrvice: Service class used to read the raw key data from a file or to persist the raw key data back
 * to a file. There is a default implementation provided which can be overridden if the behaviour of the default
 * component does not meet the callers criteria. The default fileService will always be overwrite existing contents
 * in any of the files when the service writes to the file. When using the default fileService it is the responisbility
 * of the caller to assure the needed content has been consumed before the service is called again. This fileService
 * only supports UTF-8 encoding.
 */
class RSACryptoService(val fileService: RSAKeyFileService = RSAKeyFileService()) {

    val textFile = createFile(TEXT_FILE_NAME)
    val cipherFile = createFile(CIPHER_FILE_NAME)

    /**
     * Encrypts the contents of the file text.txt using the public key information from the file pk.txt
     */
    fun encrypt() {
        val pk = fileService.readKeyFromFile(PUBLIC_KEY_NAME)
        val text = textFile.readText()
        clearFileContens(cipherFile)

        text.chars()
            .mapToObj { c -> encryptFromChar(c, pk) }
            .forEach { c -> writeToFile(c, cipherFile) }
    }

    /**
     * Decrypts the contents of the file cipher.txt using the private key information from the file sk.txt
     */
    fun decrypt() {
        val sk = fileService.readKeyFromFile(PRIVATE_KEY_NAME)
        val cipher = cipherFile.readText()
        clearFileContens(textFile)

        cipher.split(DEFAULT_DELIMITER).stream()
            .filter(String::isNotBlank)
            .map { s -> decryptToChar(s, sk) }
            .forEach { c -> writeToFile(c, textFile) }
    }

    /**
     * Encrypts the given char to a BigInteger value using the given RSAKey. The encrypted value is calculated using
     * the FastExponentiation algorithm.
     */
    private fun encryptFromChar(c: Int, pk: RSAKey): BigInteger {
        val x = c.toBigInteger()
        return FastExponentiation(x, pk.ed, pk.n).result // x ^ ed mod n
    }

    /**
     * Decrypts the given String representation of a BigInteger number using the given RSAKey. The decrypted value is
     * calculated using the FastExponentiation algorithm.
     *
     * @return The String representation of the decrypted value (ACII Character)
     */
    private fun decryptToChar(s: String, sk: RSAKey): String {
        val x = BigInteger(s)
        val result = FastExponentiation(x, sk.ed, sk.n).result // x ^ ed mod n
        return result.toInt().toChar().toString() // not pretty but necessary as BigInteger does not implement toChar().
    }
}