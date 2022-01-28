class Edge:
    u:int
    v:int
    val:int

    def __init__(self, u, v, val) -> None:
        self.u = u
        self.v = v
        self.val = val


def dfs(v):
    used[v] = True
    for u in out[v]:
        if (not used[u]):
            dfs(u)


n, m, s = map(int, input().split())
edges, out, used, d, d[s] = [], [[] for _ in range(n + 1)], [False for _ in range(n + 1)], [float('inf') for _ in range(n + 1)], 0

for _ in range(m):
    u, v, val = map(int, input().split())
    out[u].append(v)
    edges.append(Edge(u, v, val))

for _ in range(n - 1):
    for e in edges:
        if (d[e.u] < float('inf') and d[e.v] > d[e.u] + e.val):
            d[e.v] = max(float('-inf'), d[e.u] + e.val)

for e in edges:
    if (d[e.u] < float('inf') and d[e.v] > d[e.u] + e.val):
        dfs(e.v)

for i in range(1, n + 1):
    if (used[i]):
        print('-')
    elif (d[i] == float('inf')):
        print('*')
    else:
        print(d[i])
