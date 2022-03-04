/* https://www.hackerrank.com/challenges/gridland-metro/problem?isFullScreen=true */

/* 1. 정렬 후 각 트랙(행)의 범위가 겹치지 않도록 펼친다
   2. 각 행이 차지하는 칸 수를 구한다
   3. 전체에서 뺀다
 */

import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*
import kotlin.math.max

/*
 * Complete the 'gridlandMetro' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER n
 *  2. INTEGER m
 *  3. INTEGER k
 *  4. 2D_INTEGER_ARRAY track
 */

fun gridlandMetro(n: Int, m: Int, k: Int, track: Array<Array<Int>>): Long {
    if (track.isEmpty()) {
        return n.toLong() * m.toLong()
    }
    
    val flatTracks = getFlattenTracks(track) // 범위가 겹치지 않는 배열
    var trackCount = 0L

    flatTracks.forEach {
        trackCount += it[2] - it[1] + 1
    }
    
    return n.toLong() * m.toLong() - trackCount
}

private fun getFlattenTracks(track: Array<Array<Int>>): List<Array<Int>> {
    val flatTracks = mutableListOf<Array<Int>>()
    
    track.sortWith(compareBy<Array<Int>>{it[0]}.thenBy{it[1]})
    
    flatTracks.add(track[0])

    for (i in 1 until track.size) {
        val prev = flatTracks.last()
        val current = track[i]
        
        if (prev[0] != current[0]) flatTracks.add(current)
        else if(prev[2] >= current[1]) { // 범위가 겹치는 경우
            prev[2] = max(prev[2], current[2])
        } else {
            flatTracks.add(current)
        }
    }
    
    return flatTracks
}

fun main(args: Array<String>) {
    val first_multiple_input = readLine()!!.trimEnd().split(" ")

    val n = first_multiple_input[0].toInt()

    val m = first_multiple_input[1].toInt()

    val k = first_multiple_input[2].toInt()

    val track = Array<Array<Int>>(k, { Array<Int>(3, { 0 }) })

    for (i in 0 until k) {
        track[i] = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()
    }

    val result = gridlandMetro(n, m, k, track)

    println(result)
}