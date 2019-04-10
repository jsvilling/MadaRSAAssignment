package rsa

import java.io.File

class RSAEncryptionService(val fileService: RSAKeyFileService = RSAKeyFileService()) {


    fun encrypt() {
        // Convert each char to car (0-127)
        // Encrypt easch char
        // write chars to cipher.txt, comma separated

        // Read PK from pk.txt
        fileService.readKeyFromFile("pk.txt")

        // read text.txt
        val file = File("text.txt")
        val fileText = file.readText()

        for (c in fileText) {
            c
        }

    }

}