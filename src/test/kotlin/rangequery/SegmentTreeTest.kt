package rangequery

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class SegmentTreeTest {

    @Test
    fun testSegmentTree() {
        val stree = makeSegmentTree(arrayOf(1.0, 2.0, 5.0, 6.0, 7.0, 9.0), SumFunction())
        val expectedTreeArray : Array<Double> = listOf(30, 8, 22, 3, 5, 13, 9, 1, 2, 0, 0, 6, 7, 0, 0, 0).map { it.toDouble() }.toTypedArray()
        Assertions.assertArrayEquals(stree.treeAsArray, expectedTreeArray)
        assertThrows<IllegalArgumentException> {  stree.query(RangeQuery(-1, stree.size - 1))}
        assertThrows<IllegalArgumentException> {  stree.query(RangeQuery(0, stree.size))}
        Assertions.assertEquals(stree.query(RangeQuery(2,4)), 18.0)
    }

}