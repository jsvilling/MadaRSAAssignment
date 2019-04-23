package ch.fhnw.jvi.mada.cli

import ch.fhnw.jvi.mada.rsa.service.RSACryptoService
import ch.fhnw.jvi.mada.rsa.service.RSAKeyGenerationService
import ui.CommandLineInputReader

/**
 * CLI class for the MadaRSAAssignment.
 *
 * This class provides a small command line user interface to use the MadRSAAssignment tool. The class reads and
 * validates user input and calls the corresponding service classes.
 *
 * All corresponding service classes can be passed as constructor parameter and have a default implementation specified.
 *
 * @param reader        - Service to read and validate user input
 * @param generator     - Service to generate RSAKey pairs
 * @param cryptoService - Service to encrypt and decrypt a payload
 */
class CommandLineInterface(
    private val reader: CommandLineInputReader = CommandLineInputReader(),
    private val generator: RSAKeyGenerationService = RSAKeyGenerationService(),
    private val cryptoService: RSACryptoService = RSACryptoService()
) {

    private val nextAction: UserAction
        get() = reader.readAction("Choose a command to run. [encrypt | decrypt | generate | help]")

    private val isContinue: Boolean
        get() = reader.readYesNo("Do you want to run another command? [y/n]")

    fun run() {
        println("Welcome to the MADA RSA Tool")
        do {
            when (nextAction) {
                UserAction.GENERATE -> generate()
                UserAction.ENCRYPT -> encrypt()
                UserAction.DECRYPT -> decrypt()
                UserAction.HELP -> help()
            }
        } while (isContinue)
    }

    private fun generate() {
        generator.generateAndPersistKeyPair()
        println("A RSA keypair was generated and saved in the files pk.txt resp. sk.txt")
    }

    private fun encrypt() {
        cryptoService.encrypt()
        println("The contents of the file text.txt were encrypted and saved to the file cipher.txt")
    }

    private fun decrypt() {
        cryptoService.decrypt()
        println("The contents of the file cipher.txt were encrypted and saved to the file text.txt")
    }

    private fun help() {
        println("encrypt: \tEncrypts the contents of the file text.txt using the RSA key in the file pk.txt")
        println("decrypt: \tDecrypts the contents of the file cipher.txt using the RSA key in the file sk.txt")
        println("generate: \tGenerates a RSA key pair which will be saved in the files sk.txt / pk.txt")
    }

}