/* https://programmers.co.kr/learn/courses/30/lessons/42579 */
/* 맵 자료구조를 적절하게 활용할 수 있는가 */

class Solution {
    data class Music(val idx: Int, val playCount: Int)

    fun solution(genres: Array<String>, plays: IntArray): IntArray {
        val answer = mutableListOf<Int>()
        val playPerGenre: MutableMap<String, Int> = HashMap() // 장르별 재생 횟수
        val musicsOfGenre: MutableMap<String, MutableList<Music>> = HashMap() // 장르별 Music 리스트

        for (i in genres.indices) {
            val genre = genres[i]
            val playCount = plays[i]
            
            // 장르 재생 횟수 증가
            playPerGenre[genre] = playPerGenre.getOrDefault(genre, 0) + playCount
            
            // 해당 장르의 Music 리스트가 없으면 생성
            if (musicsOfGenre.contains(genre).not()) {
                musicsOfGenre[genre] = mutableListOf<Music>()
            }
            musicsOfGenre[genre]!!.add(Music(i, playCount)) // Music 추가
        }

        playPerGenre
            .toList() // Map to List
            .sortedWith(compareByDescending<Pair<String, Int>>{it.second}) // 재생횟수 내림차순 정렬
            .forEach { (genre, _) ->
                val musics = musicsOfGenre[genre]!! // 해당 장르의 노래 리스트
                // 노래 재생 횟수 내림차순, 같으면 고유번호 오름차순으로 정렬
                musics.sortWith(compareByDescending<Music>{it.playCount}.thenBy{it.idx})
                
                // 가장 많이 재생된 노래 최대 2개씩 추가
                for (i in musics.indices) {
                    if (i == 2) break
                    answer.add(musics[i].idx)
                }
            }

        return answer.toIntArray()
    }
}