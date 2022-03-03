/* https://programmers.co.kr/learn/courses/30/lessons/42884 */

/* 각 경로의 공통 범위에만 설치한다 
   1. 진입 지점 기준으로 오름차순 정렬
   2-1. 공통 범위가 있으면 카메라 개수 유지
   2-2. 없으면 카메라 추가
*/

class Solution {
    public int solution(int[][] routes) {
        // 진입 지점 기준 오름차순으로 정렬
        Arrays.sort(routes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return -1;
            } else {
                return o1[0] - o2[0];
            }
        });

        int count = 1;

        // 공통 범위
        int start = routes[0][0]; // 개념상 적어놓음, 실제로 쓰이지 않음
        int end = routes[0][1];

        // 이전 경로와 공통 범위가 있으면 카메라 개수 유지,
        // 없으면 카메라 추가
        for (int i = 1; i < routes.length; i++) {
            int currentStart = routes[i][0];
            int currentEnd = routes[i][1];

            if (currentStart <= end) {
                start = currentStart;
                end = Math.min(end, currentEnd);
            } else {
                count++;
                start = currentStart;
                end = currentEnd;
            }
        }

        return count;
    }
}