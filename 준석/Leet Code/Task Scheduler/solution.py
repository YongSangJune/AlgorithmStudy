class Solution:
    def leastInterval(self, tasks: List[str], n: int) -> int:
        answer, h = 0, []
        c = collections.Counter(tasks)
        for char, count in c.items():
            heapq.heappush(h, (-count, char))

        while h:
            i, temp = 0, []
            while i <= n:
                answer += 1
                if h:
                    count, char = heapq.heappop(h)
                    count += 1
                    if count < 0:
                        temp.append((count, char))
                if not h and not temp:
                    break
                i += 1
            for count, char in temp:
                heapq.heappush(h, (count, char))
        return answer
