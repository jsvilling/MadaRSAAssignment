package ui

import ui.input.Message
import ui.input.YesNoType

import java.util.Scanner

class CommandLineInputReader(private val scanner: Scanner = Scanner(System.`in`)) {
    fun <T> readValidatedInput(message: Any, extractor: (String) -> T): T {
        return readValidatedInput(message.toString(), extractor)
    }

    fun <T> readValidatedInput(request: String, extractor: (String) -> T): T {
        try {
            val input = readInputString(request)
            return extractor(input)
        } catch (e: Exception) {
            System.out.println("Invalid Input: ")
            return readValidatedInput(request, extractor)
        }
    }

    fun readNonEmptyString(request: String): String {
        return readValidatedInput(request, this::validateNonEmpty)
    }

    fun readYesNo(message: Message): Boolean {
        val answer = readValidatedInput(message) { a -> YesNoType.valueOf(a.toUpperCase()) }
        return answer.isYes
    }

    private fun readInputString(request: String): String {
        System.out.println(request)
        return scanner.next()
    }

    private fun validateNonEmpty(value: String): String {
        if (value.isEmpty()) {
            throw RuntimeException()
        }
        return value
    }
}
