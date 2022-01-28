n = int(input())
out = [[] for _ in range(100_010)]
parent = [0 for _ in range(100_010)]


for i in range(n - 1):
    u, v = map(int, input().split())
    out[u].append(v)
    out[v].append(u)


def dfs(v: int) -> None:
    for i in out[v]:
        if (i != parent[v]):
            parent[i] = v
            dfs(i)


def prufers_code() -> None:
    result, power = [], [len(out[i]) for i in range(n + 1)]
    parent[n] = -1
    dfs(n)
    p = 1
    while (p < n and power[p] != 1):
        p += 1
    node = p
    for _ in range(n - 2):
        next_ = parent[node]
        if (next_ != -1):
            result.append(next_)
        power[next_] -= 1
        if (next_ != -1 and power[next_] == 1 and next_ < p):
            node = next_
        else:
            p += 1
            while (p <= n and power[p] != 1):
                p += 1
            node = p
    return result
 
print(' '.join(list(map(str, prufers_code()))))