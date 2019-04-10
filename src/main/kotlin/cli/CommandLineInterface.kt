package cli

import rsa.RSAKeyGenerator
import ui.CommandLineInputReader

class CommandLineInterface(
    val reader: CommandLineInputReader = CommandLineInputReader(),
    val generator: RSAKeyGenerator = RSAKeyGenerator()
) {

    private val nextAction: UserAction
        get() = reader.readValidatedInput("What do you want to do?") { a -> UserAction.valueOf(a.toUpperCase()) }

    private val isContinue: Boolean
        get() = reader.readYesNo("Do you want do do another thing?")

    fun run() {
        println("Welcome to the MADA RSA Tool")
        do {
            when (nextAction) {
                UserAction.GENERATE -> generate()
                UserAction.ENCRYPT -> encrypt()
                UserAction.DECRYPT -> decrypt()
            }
        } while (isContinue)
    }

    private fun generate() {
        generator.generateAndPersistKeyPair()
        print("A RSA keypair was generated and saved in the files pk.txt resp. sk.txt")
    }

    private fun encrypt() {

    }

    private fun decrypt() {}

}