package ui

import ch.fhnw.jvi.mada.cli.UserAction
import ui.input.YesNoType
import java.util.Scanner

/**
 * Helper class to read validated user input from a scanner source.
 *
 * The scanner to be used can be passed as a constructor argument. If no scanner is provided a default instance will be
 * used. The default instance is created as a new Scanner reading from System.in
 */
class CommandLineInputReader(private val scanner: Scanner = Scanner(System.`in`)) {

    /**
     * Read user input and validate against the YesNoType enum. If an invalid input is provided, the user will be
     * informed and asked to provide a valid value. This is repeated until a valid value has been provided.
     *
     * @return Boolean value indicating whether the user has confirmed or declined.
     */
    fun readYesNo(message: String): Boolean {
        val answer = readValidatedInput(message) { a -> YesNoType.valueOf(a.toUpperCase()) }
        return answer.isYes
    }

    /**
     * Read user input and validate against the UserAction enum. If an invalid input is provided, the user will be
     * informed and asked to provide a valid value. This is repeated until a valid value has been provided.
     *
     * @return The action the user wants to be performed.
     */
    fun readAction(message: String): UserAction {
        return readValidatedInput(message) { a -> UserAction.valueOf(a.toUpperCase()) }
    }

    /**
     * Generic method to read a validated String input. If an invalid input is provided, the user will be informed and
     * asked to provide a valid value. This is repeated until a valid value has been provided.
     *
     * @param request The message that will be displayed to the user.
     * @param extractor A function that validated the user input and converts it to the expected data type. The function
     * is expected to throw an exception if the input value is invalid.
     */
    fun <T> readValidatedInput(request: String, extractor: (String) -> T): T {
        try {
            val input = readInputString(request)
            return extractor(input)
        } catch (e: Exception) {
            println("Invalid Input: ")
            return readValidatedInput(request, extractor)
        }
    }

    /**
     * Prints the provided message and reads the next value from the scanner.
     */
    private fun readInputString(request: String): String {
        println(request)
        return scanner.next()
    }
}
