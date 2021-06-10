package rangequery

interface RangeFunction {
    val nullValue : Double
    fun execute(value1 : Double, value2 : Double) :  Double
}

class SumFunction : RangeFunction {
    override val nullValue: Double
        get() = 0.0

    override fun execute(value1: Double, value2: Double): Double {
        return value1 + value2
    }
}

data class RangeQuery(val startIndex : Int, val endIndex : Int )

interface RangeQueryable {
    fun query(rangeQuery : RangeQuery) : Double
}