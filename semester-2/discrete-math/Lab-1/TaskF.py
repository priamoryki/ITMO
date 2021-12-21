states = [dict(), dict()]
transitions = [[], []]

def dfs(u, v):
    is_used[u] = True
    if ((u in states[0]) != (v in states[1])):
        return False
    associations[u] = v
    result = True
    for state, u_to in transitions[0][u].items():
        if (state not in transitions[1][v]):
            return False
        v_to = transitions[1][v][state]
        if (not is_used[u_to]):
            result &= dfs(u_to, v_to)
        else:
            result &= (associations[u_to] == v_to)
    return result

with open('isomorphism.in', 'r') as inp:
    for num in range(2):
        n, m, k = map(int, inp.readline().split())
        for i in map(int, inp.readline().split()):
            states[num][i] = 1

        transitions[num] = [dict() for i in range(n + 1)]
        for i in range(m):
            temp = inp.readline().split()
            transitions[num][int(temp[0])][temp[2]] = int(temp[1])

with open('isomorphism.out', 'w') as out:
    is_used, associations = [False for i in range(max(len(transitions[0]), len(transitions[1])))], [0 for i in range(max(len(transitions[0]), len(transitions[1])))]
    out.write('YES' if dfs(1, 1) else 'NO')