from collections import deque
import sys
sys.setrecursionlimit(10000)


class Edge:
    def __init__(self, u, v, c, f):
        self.u = u
        self.v = v
        self.c = c
        self.f = f


def bfs() -> bool:
    global s, t, g, edges, d
    d = [SIZE for _ in range(SIZE)]
    d[s] = 0
    q = deque()
    q.append(s)
    while len(q) != 0:
        u = q.popleft()
        for id in g[u]:
            uv = edges[id]
            if uv.f < uv.c and d[uv.v] == SIZE:
                d[uv.v] = d[u] + 1
                q.append(uv.v)
    return d[t] != SIZE


def dfs(u, minC) -> int:
    if u == t or minC == 0:
        return minC
    for i in range(len(g[u])):
        uv = edges[g[u][i]]
        if d[uv.v] == d[u] + 1:
            delta = dfs(uv.v, min(minC, uv.c - uv.f))
            if delta > 0:
                uv.f += delta
                edges[g[u][i] ^ 1].f -= delta
                return delta
    return 0


def dfs1(u: int, cur):
    if u == t:
        cur.append(u)
        return
    for v in g[u]:
        if edges[v].f == 1:
            edges[v].f = 0
            cur.append(u)
            dfs1(edges[v].v, cur)
            return
        else:
            continue


SIZE = 100_001
n, m, s, t = map(int, input().split())
edges, d, g = [], [], [[] for _ in range(SIZE)]
for i in range(m):
    u, v = map(int, input().split())
    g[u].append(2 * i)
    edges.append(Edge(u, v, 1, 0))
    g[v].append(2 * i + 1)
    edges.append(Edge(v, u, 0, 0))

maxFlow = 0
while bfs():
    fl = dfs(s, SIZE)
    while fl > 0:
        maxFlow += fl
        fl = dfs(s, SIZE)

if maxFlow >= 2:
    print("YES")
    p1, p2 = [], []
    dfs1(s, p1)
    dfs1(s, p2)
    print(" ".join(map(str, p1)))
    print(" ".join(map(str, p2)))
else:
    print("NO")
