package rangequery

import java.lang.IllegalArgumentException

class SegmentTree(val treeAsArray : Array<Double>, val size : Int, val rangeFunction: RangeFunction) : RangeQueryable {
    override fun query(rangeQuery: RangeQuery): Double {
        if (rangeQuery.startIndex < 0 || rangeQuery.endIndex > size - 1) {
            throw IllegalArgumentException("$rangeQuery is invalid as its not within bounds 0 and ${size - 1}")
        }
        println(size)
        return doQuery(0, 0, size - 1, rangeQuery)
    }

    private fun doQuery(treePos : Int, nodeStart : Int, nodeEnd : Int, rangeQuery: RangeQuery ) : Double {
        if (rangeQuery.startIndex <= nodeStart && rangeQuery.endIndex >= nodeEnd) {
            // Full overlap
            return treeAsArray[treePos]
        } else if (nodeEnd < rangeQuery.startIndex || nodeStart > rangeQuery.endIndex) {
            // No overlap
            return rangeFunction.nullValue
        } else {
            // partial overlap
            val middle = (nodeStart + nodeEnd) / 2
            val leftValue = doQuery((2 * treePos) + 1, nodeStart, middle, rangeQuery)
            val rightValue = doQuery((2 * treePos) + 2, middle + 1, nodeEnd, rangeQuery)
            return rangeFunction.execute(leftValue, rightValue)
        }
    }
}

fun makeSegmentTree(values : Array<Double>, rangeFunction : RangeFunction) : SegmentTree {
    //println(utils.log2(values.size.toDouble()))
    val treeHeight = Math.ceil(utils.log2(values.size.toDouble()) + 1).toInt()
    //println(treeHeight)
    val treeSize = Math.pow(2.0, treeHeight.toDouble()).toInt()
    //println(treeSize)
    val tree = Array<Double>(treeSize) { rangeFunction.nullValue }
    doMakeSegmentTree(tree, 0, values, 0, values.size - 1, rangeFunction)
    return SegmentTree(tree, values.size, rangeFunction)
}

private fun doMakeSegmentTree(
    tree : Array<Double>,
    tpos : Int,
    values : Array<Double>,
    left : Int,
    right : Int,
    rangeFunction: RangeFunction) : Double {

    if (left == right) {
        tree[tpos] = values[left]
        return tree[tpos]
    }

    val mid = (left + right) / 2
    val leftValue = doMakeSegmentTree(tree, (2 * tpos) + 1, values, left, mid, rangeFunction)
    val rightValue = doMakeSegmentTree(tree, (2 * tpos) + 2, values, mid + 1, right, rangeFunction)
    tree[tpos] = rangeFunction.execute(leftValue, rightValue)
    return tree[tpos]
}