def dfs(v: int) -> bool:
    global g, matching, is_used
    if is_used[v]:
        return False

    is_used[v] = True
    for u in g[v]:
        if matching[u] == -1 or dfs(matching[u]):
            matching[u] = v
            return True
    return False


max_n = 250
n, m = map(int, input().split())
matching = [-1 for _ in range(max_n)]
g = [list(map(lambda x: int(x) - 1, input().split()))[:-1] for _ in range(n)]

for i in range(n):
    is_used = [False for _ in range(max_n)]
    dfs(i)

res = []
for i in range(m):
    if matching[i] != -1:
        res.append((matching[i] + 1, i + 1))

print(len(res))
print('\n'.join(f"{i[0]} {i[1]}" for i in res))
