package ch.fhnw.jvi.mada.util

import java.io.File
import java.math.BigInteger

/**
 * Helper class to interact with files.
 */
object FileUtils {

    fun createFile(fileName: String): File {
        val cipherFile = File(fileName)
        cipherFile.createNewFile()
        return cipherFile
    }

    fun writeToFile(c: BigInteger, file: File) {
        writeToFile(
            c.toString().plus(StringConstants.DEFAULT_DELIMITER),
            file
        )
    }

    fun writeToFile(c: String, file: File) {
        file.appendText(c)
    }

    fun clearFileContens(fileName: File) {
        fileName.writeText("")
    }
}
