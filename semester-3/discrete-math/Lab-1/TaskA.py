n = int(input())
m = [[False for _ in range(n)] for _ in range(n)]


def are_connected(v: int, u: int) -> bool:
    return m[v][u]


def cycle() -> list:
    q = [i for i in range(n)]

    for _ in range((n - 1) * n):
        if (not are_connected(q[0], q[1])):
            i = 2
            while (not are_connected(q[0], q[i]) or not are_connected(q[1], q[i + 1])):
                i += 1
            for j in range(i // 2):
                q[1 + j], q[i - j] = q[i - j], q[1 + j]
        q.append(q.pop(0))

    return q


for i in range(n):
    s = input()
    for j in range(i):
        m[i][j] = bool(int(s[j]))
        m[j][i] = bool(int(s[j]))

print(' '.join(map(str, map(lambda x: x + 1, cycle()))))
