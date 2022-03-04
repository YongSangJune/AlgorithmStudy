/* https://www.hackerrank.com/challenges/pairs/problem?isFullScreen=true */

/* 해시 사용, (자신 - target)이 해시에 있는지 확인 */

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

/*
 * Complete the 'pairs' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER k
 *  2. INTEGER_ARRAY arr
 */

fun pairs(target: Int, arr: Array<Int>): Int {
    val hash: MutableMap<Int, Boolean> = HashMap()
    
    arr.forEach { num -> 
        hash[num] = true
    }
    
    return arr.filter {hash.contains(it - target)}.size
}

fun main(args: Array<String>) {
    val first_multiple_input = readLine()!!.trimEnd().split(" ")

    val n = first_multiple_input[0].toInt()

    val k = first_multiple_input[1].toInt()

    val arr = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()

    val result = pairs(k, arr)

    println(result)
}