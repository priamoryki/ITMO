import heapq

n = int(input())
a = list(map(int, input().split()))
heapq.heapify(a)

res = 0
while (len(a) != 1):
    temp = heapq.heappop(a) + heapq.heappop(a)
    heapq.heappush(a, temp)
    res += temp

print(res)