/* https://www.acmicpc.net/problem/8980 */

/* 실패한 코드 */

import java.io.*
import java.util.*
import kotlin.math.min

data class Delivery(val to: Int, var box: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var answer = 0
    val (n, truckCapacity) = readLine().split(" ").map(String::toInt)
    val deliverySize = readLine().toInt()
    val truck = PriorityQueue(compareBy<Delivery>{it.to})
    var truckAvailable = truckCapacity

    // 각 마을의 배송 정보를 입력한 배열
    val townInfo = Array<MutableList<Delivery>>(n + 1) { mutableListOf() }
    repeat(deliverySize) {
        val st = StringTokenizer(readLine())
        val from = st.nextToken().toInt()
        townInfo[from].add(Delivery(st.nextToken().toInt(), st.nextToken().toInt()))
    }

    sortEachTownInfo(townInfo) //배송 정보를 보내는 마을 기준으로 오름차순 정렬

    for (currentTown in 1..n) {
        // 택배 배송(하차)
        while (truck.isNotEmpty() && truck.peek().to == currentTown) {
            val delivery = truck.poll()
            answer += delivery.box
            truckAvailable += delivery.box
        }

        // 택배 상차
        for(delivery in townInfo[currentTown]) {
            if (truckAvailable == 0) break

            delivery.box = min(truckAvailable, delivery.box)
            truckAvailable -= delivery.box
            truck.offer(delivery)
        }
    }

    print(answer)
}

/**
 * 각 마을에서 보내는 배송 정보를 보내는 마을(to) 기준 오름차순 정렬
 */
fun sortEachTownInfo(townInfo: Array<MutableList<Delivery>>) {
    townInfo.forEach { deliveryList ->
        deliveryList.sortWith(compareBy{it.to})
    }
}

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/* 통과한 코드 */

import java.io.*
import java.util.*
import kotlin.math.min

data class Delivery(val from: Int, val to: Int, var box: Int)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var answer = 0
    val st = StringTokenizer(readLine())

    val n = st.nextToken().toInt()
    val truckCapacity = st.nextToken().toInt()
    val capacityArray = IntArray(n+1){ truckCapacity }

    val deliveryList = mutableListOf<Delivery>()
    val deliverySize = readLine().toInt()
    repeat(deliverySize) {
        val st2 = StringTokenizer(readLine())
        deliveryList.add(Delivery(st2.nextToken().toInt(), st2.nextToken().toInt(), st2.nextToken().toInt()))
    }
    deliveryList.sortWith(compareBy<Delivery>{it.to}.thenBy{it.from})

    deliveryList.forEach { delivery ->
        val minCapacity = (delivery.from until delivery.to).minOfOrNull { capacityArray[it] }
        val deliveredBox = min(minCapacity!!, delivery.box)
        answer += deliveredBox

        for (town in delivery.from until delivery.to) {
            capacityArray[town] -= deliveredBox
        }
    }

    print(answer)
}