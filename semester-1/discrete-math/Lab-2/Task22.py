def f(pref):
    res, last, s = 0, 1, 0
    for i in range(len(pref)):
        for j in range(last, pref[i]):
            res += d[n - s - j][j]
        last = pref[i]
        s += pref[i]
    return res

with open('part2num.in', 'r') as inp:
    a = list(map(int, inp.readline().split('+')))

n = sum(a)

d = [[0 for i in range(n + 1)] for i in range(n + 1)]
for i in range(n + 1):
    for j in range(i, -1, -1):
        if (i == j):
            d[i][j] = 1
        elif (i < j):
            d[i][j] = 0
        else:
            d[i][j] = d[i][j + 1] + d[i - j][j]

with open('part2num.out', 'w') as out:
    out.write(str(f(a)))