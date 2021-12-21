with open('problem2.in', 'r') as inp:
    s = inp.readline().split()[0]
    n, m, k = map(int, inp.readline().split())
    states = list(map(int, inp.readline().split()))

    transitions = [dict() for i in range(n + 1)]
    for i in range(m):
        temp = inp.readline().split()
        if (temp[2] not in transitions[int(temp[0])]):
            transitions[int(temp[0])][temp[2]] = [int(temp[1])]
        else:
            transitions[int(temp[0])][temp[2]].append(int(temp[1]))

possible = [[False for j in range(n + 1)] for i in range(len(s) + 1)]
possible[0][1] = True
for i in range(len(s)):
    for state in range(1, n + 1):
        if (possible[i][state] and s[i] in transitions[state]):
            for to in transitions[state][s[i]]:
                possible[i + 1][to] = True

with open('problem2.out', 'w') as out:
    out.write('Accepts' if True in possible[len(s)] else 'Rejects')