def f(last, s, pref):
    global n, k
    while (s != n):
        for j in range(last, n + 1):
            if (k >= d[n - s - j][j] and s + (j + 1) <= n):
                k -= d[n - s - j][j]
            else:
                pref.append(j)
                s += j
                last = j
                break
    return pref
    
with open('num2part.in', 'r') as inp:
    n, k = map(int, inp.readline().split())

d = [[0 for i in range(n + 1)] for i in range(n + 1)]
for i in range(n + 1):
    for j in range(i, -1, -1):
        if (i == j):
            d[i][j] = 1
        elif (i < j):
            d[i][j] = 0
        else:
            d[i][j] = d[i][j + 1] + d[i - j][j]

with open('num2part.out', 'w') as out:
    out.write('+'.join(map(str, f(1, 0, []))))