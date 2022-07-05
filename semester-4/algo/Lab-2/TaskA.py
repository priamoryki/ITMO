class Edge:
    def __init__(self, u: int, v: int, c: int):
        self.u = u
        self.v = v
        self.c = c
        self.flow = 0


def dfs(u: int, flow: int) -> int:
    global g, is_used, edges, n
    if u == n:
        return flow
    if is_used[u]:
        return 0
    is_used[u] = True
    for v in g[u]:
        uv = edges[v]
        if uv.flow < uv.c:
            delta = dfs(uv.v, min(flow, uv.c - uv.flow))
            if delta > 0:
                uv.flow += delta
                edges[v ^ 1].flow -= delta
                return delta
    return 0


n, m = int(input()), int(input())
edges, g = [], [[] for _ in range(101)]
for i in range(m):
    u, v, c = map(int, input().split())
    g[u].append(2 * i)
    edges.append(Edge(u, v, c))
    g[v].append(2 * i + 1)
    edges.append(Edge(v, u, c))

res = 0
while True:
    is_used = [False for _ in range(101)]
    delta = dfs(1, 9223372036854775807)
    should_break = False
    if delta == 0:
        print(res)
        print("\n".join(str(edges[2 * i].flow) for i in range(m)))
        should_break = True
    else:
        res += delta
    if should_break:
        break
