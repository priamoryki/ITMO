def dfs(v: int) -> bool:
    global reversed_g, matching, is_used
    if is_used[v]:
        return False

    is_used[v] = True
    for u in reversed_g[v]:
        if matching[u] == -1 or dfs(matching[u]):
            matching[u] = v
            return True
    return False


def new_dfs(v: int) -> None:
    global is_visited, org
    if is_visited[v]:
        return
    is_visited[v] = True
    for u in org[v]:
        new_dfs(u)


for _ in range(int(input())):
    n, m = map(int, input().split())
    g, reversed_g, matching = [[] for _ in range(n)], [[] for _ in range(n)], [-1 for _ in range(m)]
    rev = [[True for _ in range(m)] for _ in range(n)]
    for i in range(n):
        g[i] = list(map(lambda x: int(x) - 1, input().split()))[:-1]
        for j in g[i]:
            rev[i][j] = False

    for i in range(n):
        for j in range(m):
            if rev[i][j]:
                reversed_g[i].append(j)

    for i in range(n):
        is_used = [False for _ in range(n)]
        dfs(i)

    matched, matrix = set(), [[False for _ in range(m)] for _ in range(n)]
    for i in range(m):
        if matching[i] != -1:
            matrix[matching[i]][i] = True
            matched.add(matching[i])

    org = [[] for _ in range(n + m)]
    for i in range(n):
        for j in reversed_g[i]:
            if matrix[i][j]:
                org[j + n].append(i)
            else:
                org[i].append(j + n)

    is_visited = [False for _ in range(n + m)]
    for i in set(range(n)).difference(matched):
        new_dfs(i)

    cover = set()
    for i in range(n + m):
        if (i < n and not is_visited[i]) or (i >= n and is_visited[i]):
            cover.add(i)

    boys, girls = set(), set()
    for i in set(range(n + m)).difference(cover):
        if i < n:
            boys.add(i + 1)
        else:
            girls.add(i - n + 1)

    print(len(boys) + len(girls))
    print(f"{len(boys)} {len(girls)}")
    print(" ".join(map(str, boys)))
    print(" ".join(map(str, girls)))
