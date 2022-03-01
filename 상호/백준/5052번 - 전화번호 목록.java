/* https://www.acmicpc.net/problem/5052 */
/* 정렬, 위 아래 문자열 확인  */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            String[] numbers = new String[n];

            for (int i = 0; i < n; i++) {
                numbers[i] = br.readLine();
            }

            Arrays.sort(numbers);

            if (isValid(numbers)) sb.append("YES\n");
            else sb.append("NO\n");
        }

        System.out.print(sb);
    }

    /**
     * 정렬된 문자열에서 
     * 위에 번호가 아래 번호의 접두사인지 확인
     */
    private static Boolean isValid(String[] numbers) {
        for(int i = 0; i < numbers.length-1; i++) {
            String num1 = numbers[i];
            String num2 = numbers[i+1];

            if (num1.length() > num2.length()) continue;
            if (num1.equals(num2.substring(0, num1.length()))) {
                return false;
            }
        }

        return true;
    }
}