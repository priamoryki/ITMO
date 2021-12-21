with open('problem4.in', 'r') as inp:
    n, m, k, l = map(int, inp.readline().split())
    states = list(map(int, inp.readline().split()))

    transitions = [dict() for i in range(n + 1)]
    for i in range(m):
        temp = inp.readline().split()
        transitions[int(temp[0])][temp[2]] = int(temp[1])

dp = [[0 for j in range(n + 1)] for i in range(l + 1)]
dp[0][1] = 1
for i in range(1, l + 1):
    for j in range(1, n + 1):
        for state, to in transitions[j].items():
            dp[i][to] += dp[i - 1][j]

with open('problem4.out', 'w') as out:
    out.write(str(sum(dp[l][state] for state in states) % (10**9 + 7)))