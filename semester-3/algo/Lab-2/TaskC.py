from dataclasses import dataclass


@dataclass(frozen=True)
class Edge:
    u:int
    v:int
    val:int


def solve():
    for i in range(n):
        d, p, d[i], x = [float('inf') for _ in range(n)], [-1 for _ in range(n)], 0, 0
        for _ in range(n):
            x = -1
            for edge in edges:
                if (d[edge.u] < float('inf') and d[edge.v] > d[edge.u] + edge.val):
                    d[edge.v], p[edge.v], x = max(float('-inf'), d[edge.u] + edge.val), edge.u, edge.v
        if (x != -1):
            y = x
            for _ in range(n):
                y = p[y]
            cur, path = y, []
            while (cur != y or len(path) <= 1):
                path.append(cur)
                cur = p[cur]
            path.reverse()
            print('YES', len(path), ' '.join(list(map(lambda x: str(x + 1), path))), sep='\n')
            return
    print('NO')


edges = []
n = int(input())
for i in range(n):
    temp = list(map(int, input().split()))
    for j in range(n):
        if (temp[j] != 100_000):
            edges.append(Edge(i, j, temp[j]))

solve()
