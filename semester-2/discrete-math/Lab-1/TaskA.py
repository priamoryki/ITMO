with open('problem1.in', 'r') as inp:
    s = inp.readline().split()[0]
    n, m, k = map(int, inp.readline().split())
    states = list(map(int, inp.readline().split()))

    transitions = [dict() for i in range(n + 1)]
    for i in range(m):
        temp = inp.readline().split()
        transitions[int(temp[0])][temp[2]] = int(temp[1])

result = 'Accepts'
current = 1
for i in s:
    if (i in transitions[current].keys()):
        current = transitions[current][i]
    else:
        result = 'Rejects'
        break

if (current not in states):
    result = 'Rejects'

with open('problem1.out', 'w') as out:
    out.write(result)