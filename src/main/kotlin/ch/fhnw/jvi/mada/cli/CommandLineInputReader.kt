package ui

import ch.fhnw.jvi.mada.cli.UserAction
import ui.input.YesNoType
import java.util.Scanner

class CommandLineInputReader(private val scanner: Scanner = Scanner(System.`in`)) {

    fun readYesNo(message: String): Boolean {
        val answer = readValidatedInput(message) { a -> YesNoType.valueOf(a.toUpperCase()) }
        return answer.isYes
    }

    fun readAction(message: String): UserAction {
        return readValidatedInput(message) { a -> UserAction.valueOf(a.toUpperCase()) }
    }

    fun <T> readValidatedInput(request: String, extractor: (String) -> T): T {
        try {
            val input = readInputString(request)
            return extractor(input)
        } catch (e: Exception) {
            println("Invalid Input: ")
            return readValidatedInput(request, extractor)
        }
    }

    private fun readInputString(request: String): String {
        println(request)
        return scanner.next()
    }
}
