def cycle_check():
    is_visited = [False for i in range(len(is_used))]

    def visit(u):
        is_visited[u] = True
        for to in reversed_[u]:
            if (not is_visited[to]):
                visit(to)

    for u in states:
        visit(u)
    color = [0 for i in range(len(is_used))]

    def check(u):
        color[u] = 1
        for to in transitions[u]:
            if (is_visited[to] and (color[to] == 1 or color[to] == 0 and check(to))):
                return True
        color[u] = 2
        return False

    return check(1)


def dfs(u, v):
    is_used[u] = True
    for to in transitions[u]:
        if (not is_used[to]):
            v = dfs(to, v)
    v.append(u)
    return v


def sort():
    v = []
    for i in range(1, len(transitions)):
        if (not is_used[i]):
            v = dfs(i, v)
    v.reverse()
    return v


with open('problem3.in', 'r') as inp:
    n, m, k = map(int, inp.readline().split())
    states = list(map(int, inp.readline().split()))
    transitions, reversed_, is_used = [[] for i in range(n + 1)], [[] for i in range(n + 1)], [False for i in range(n + 1)]

    for i in range(m):
        temp = list(map(int, inp.readline().split()[:2]))
        transitions[temp[0]].append(temp[1])
        reversed_[temp[1]].append(temp[0])


with open('problem3.out', 'w') as out:
    result = -1
    if (not cycle_check()):
        dp = [0 for i in range(n + 1)]
        dp[1] = 1
        for u in sort():
            for frm in reversed_[u]:
                dp[u] += dp[frm]
        result = sum(dp[i] for i in states) % (10 ** 9 + 7)
    out.write(str(result))