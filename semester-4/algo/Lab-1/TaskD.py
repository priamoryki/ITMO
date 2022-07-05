import math


def dfs(v: int) -> bool:
    global is_visited, px, py
    if is_visited[v]:
        return False

    is_visited[v] = True
    for u in edges[v]:
        if py[u] == -1 or dfs(py[u]):
            py[u], px[v] = v, u
            return True
    return False


n, v = map(int, input().split())
edges = [[] for _ in range(n)]
time, x, y = [], [], []

for _ in range(n):
    temp = input().split()
    time.append(int(temp[0].split(':')[0]) * 60 + int(temp[0].split(':')[1]))
    x.append(int(temp[1]))
    y.append(int(temp[2]))

for i in range(n):
    for j in range(n):
        if i != j and 60 * math.hypot(x[i] - x[j], y[i] - y[j]) <= v * (time[j] - time[i]):
            edges[i].append(j)

px, py = [-1 for _ in range(n)], [-1 for _ in range(n)]
while True:
    flag, is_visited = False, [False for _ in range(n)]
    for i in range(n):
        if px[i] == -1 and dfs(i):
            flag = True
    if not flag:
        break

print(px.count(-1))
