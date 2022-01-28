from collections import deque


def get(v: int):
    if (p[v] != v):
        p[v] = get(p[v])
    return p[v]


with open('schedule.in') as f:
    p, result, n = [*range(100_010)], 0, int(f.readline())
    tasks = sorted(deque([*map(int, reversed(f.readline().split()))] for _ in range(n)))

while (tasks):
    cur = tasks.pop()
    if (cur[1] >= n):
        continue
    t = get(cur[1])
    if (t):
        p[t] = t - 1
    else:
        result += cur[0]


print(result, file=open('schedule.out', 'w'))