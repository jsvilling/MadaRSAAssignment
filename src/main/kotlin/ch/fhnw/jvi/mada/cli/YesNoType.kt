package ui.input

enum class YesNoType {
    Y, YES, N, NO;

    val isYes: Boolean
        get() = this == Y || this == YES

    val isNo: Boolean
        get() = this == N || this == NO
}
