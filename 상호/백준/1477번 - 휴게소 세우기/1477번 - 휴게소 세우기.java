import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int[] stops = new int[n + 1];
        stops[n] = l; // 고속도로 마지막 점, l-1로 하면 오답

        if (n > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                stops[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(stops);
        }

        int start = 1;
        int end = l; // l/2로 하면 오답

        // 구간의 길이를 기준으로 이분 탐색
        while (start <= end) {
            int mid = (start + end) / 2;

            // 적게 지었으면 휴게소 간격을 더 줄일 수 있다
            if (getHowManyBuild(stops, mid) > m) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        System.out.print(start);
    }

    private static int getHowManyBuild(int[] stops, int standard) {
        int prev = 0;
        int count = 0;

        for (int stop : stops) {
            // 휴게소 세우는 부분
            while (stop - prev > standard) {
                prev += standard;
                count++;
            }

            prev = stop;
        }

        return count;
    }
}