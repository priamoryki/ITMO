from dataclasses import dataclass


@dataclass(frozen=True)
class Edge:
    u:int
    v:int
    val:int


n, m, k, s = map(int, input().split())
d, d[s][0] = [[float('inf') for _ in range(k + 1)] for _ in range(n + 1)], 0
edges = [Edge(*map(int, input().split())) for _ in range(m)]

for i in range(k):
    for e in edges:
        if (d[e.u][i] != float('inf')):
            d[e.v][i + 1] = min(d[e.u][i] + e.val, d[e.v][i + 1])

print('\n'.join(map(lambda i: '-1' if d[i][k] == float('inf') else str(d[i][k]), range(1, n + 1))))
