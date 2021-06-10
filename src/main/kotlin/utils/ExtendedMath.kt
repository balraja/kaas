package utils

object ExtendedMathConstants {
    val LOG_10_2 = Math.log10(2.0)
}

fun log2(value : Double) : Double {
    // loga(b) = log10(b)/log10(a)
    return Math.log10(value) / ExtendedMathConstants.LOG_10_2
}