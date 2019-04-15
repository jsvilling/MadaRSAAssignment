package cli

import rsa.service.RSACryptoService
import rsa.service.RSAKeyGenerationService
import ui.CommandLineInputReader

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
        print("A RSA keypair was generated and saved in the files pk.txt resp. sk.txt")
    }

    private fun encrypt() {
        cryptoService.encrypt()
        print("The contents of the file text.txt were encrypted and saved to the file cipher.txt")
    }

    private fun decrypt() {
        cryptoService.decrypt()
        print("The contents of the file cipher.txt were encrypted and saved to the file text.txt")
    }

    private fun help() {
        print("encrypt: \tEncrypts the contents of the file text.txt using the RSA key in the file pk.txt")
        print("decrypt: \tDecrypts the contents of the file cipher.txt using the RSA key in the file sk.txt")
        print("generate: \tGenerates a RSA key pair which will be saved in the files sk.txt / pk.txt")
    }

}