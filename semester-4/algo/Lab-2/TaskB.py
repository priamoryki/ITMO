class Edge:
    def __init__(self, c, f, to, num, rev_num, from_, index):
        self.c = c
        self.f = f
        self.to = to
        self.num = num
        self.rev_num = rev_num
        self.from_ = from_
        self.index = index


def dfs(v: int, min_c: float):
    global t, is_used, g, edges
    if v == t:
        return min_c
    is_used[v] = True
    for i in range(len(g[v])):
        e = edges[g[v][i]]
        if not is_used[e.to] and e.f < e.c:
            delta = dfs(e.to, min(min_c, e.c - e.f))
            if delta > 0:
                edges[g[v][i]].f += delta
                edges[edges[g[v][i]].rev_num].f -= delta
                return delta
    return 0


def find_min_cut(v: int):
    global is_used, g, edges, min_cut
    if is_used[v]:
        return
    is_used[v] = True
    for i in range(len(g[v])):
        e = edges[g[v][i]]
        if e.f == e.c:
            min_cut.append(e.index)
        if not is_used[e.to] and e.f < e.c:
            find_min_cut(e.to)


min_cut, edges = [], []
n, m = map(int, input().split())
g = [[] for _ in range(n)]
for i in range(m):
    u, v, c = map(lambda x: int(x) - 1, input().split())
    c += 1
    edges.append(Edge(c, 0, v, len(edges), len(edges) + 1, u, i))
    g[u].append(edges[-1].num)
    edges.append(Edge(c, 0, u, len(edges), edges[-1].num, v, i))
    g[v].append(edges[-1].num)

s, t, is_used = 0, n - 1, [False for _ in range(n)]
while dfs(s, 1e9 + 100) > 0:
    is_used = [False for _ in range(n)]

max_flow = sum(abs(edges[i].f) for i in g[0])
is_used = [False for _ in range(n)]
find_min_cut(s)
res = []
for i in range(0, len(edges), 2):
    if is_used[edges[i].from_] ^ is_used[edges[i].to]:
        res.append(edges[i].index + 1)

print(len(res), max_flow)
print(" ".join(map(str, res)))
