class Edge:
    def __init__(self, u: int, v: int, w: int, number: int):
        self.u = u
        self.v = v
        self.w = w
        self.number = number
        self.is_used = True


def get(v: int) -> int:
    if (parent[v] != v):
        parent[v] = get(parent[v])
    return parent[v]


def unite(v: int, u: int):
    x, y = get(u), get(v)
    if (x == y):
        return
    if (c[x] == c[y]):
        c[x] += 1
    if (c[x] < c[y]):
        parent[x] = y
    else:
        parent[y] = x


with open('destroy.in') as f:
    n, m, s = map(int, f.readline().split())
    edges, parent, c = [], [*range(n)], [0 for _ in range(n)]
    for i in range(m):
        temp = [*map(int, f.readline().split())]
        edges.append(Edge(temp[0] - 1, temp[1] - 1, temp[2], i + 1))
    
edges, res, cur_sum = sorted(edges, key=lambda x: x.w), set(), 0

for i in edges:
    if (get(i.u) != get(i.v)):
        i.is_used = False
        unite(i.u, i.v)

for i in edges:
    if (cur_sum + i.w <= s and i.is_used):
        cur_sum += i.w
        res.add(i.number)

print(f'{len(res)}\n' + ' '.join(map(str, res)), file=open('destroy.out', 'w'))