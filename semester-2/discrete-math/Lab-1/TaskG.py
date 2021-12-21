states = [dict(), dict()]
transitions = [[], []]


def f(x, y, z):
    try:
        return transitions[x][y][z]
    except (BaseException):
        return 0
    transitions[x][y][z]


def bfs():
    q = [(1, 1)]
    while (len(q) != 0):
        (u, v) = q.pop(0)
        if ((u in states[0]) != (v in states[1])):
            return False
        is_used[(u, v)] = True
        for c in 'abcdefghijklmnopqrstuvwxyz':
            u_to, v_to = f(0, u, c), f(1, v, c)
            if ((u_to, v_to) not in is_used):
                q.append((u_to, v_to))
    return True

with open('equivalence.in', 'r') as inp:
    for num in range(2):
        n, m, k = map(int, inp.readline().split())
        for i in map(int, inp.readline().split()):
            states[num][i] = 1

        transitions[num] = [dict() for i in range(n + 1)]
        for i in range(m):
            temp = inp.readline().split()
            transitions[num][int(temp[0])][temp[2]] = int(temp[1])

with open('equivalence.out', 'w') as out:
    is_used = dict()
    out.write('YES' if bfs() else 'NO')