package ui.input

/**
 * Wrapper enum for user feedback. This can be used to simplify reading a confirm/decline response.
 */
enum class YesNoType {
    Y, YES, N, NO;

    val isYes: Boolean
        get() = this == Y || this == YES

    val isNo: Boolean
        get() = this == N || this == NO
}
