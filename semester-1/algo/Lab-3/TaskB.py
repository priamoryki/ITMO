n, m = map(int, input().split())
way, a, d = '', [[0] * (m + 1)], [[0 for j in range(m + 1)] for i in range(n + 1)]
p = [['' for j in range(m + 1)] for i in range(n + 1)]
for i in range(n):
    a.append([0, *map(int, input().split())])

for i in range(1, n + 1):
    for j in range(1, m + 1):
        if (i == j == 1):
            d[i][j] = a[i][j]
        else:
            d[i][j] = float('-inf')
            if (j > 1 and d[i][j] < d[i][j - 1] + a[i][j]):
                d[i][j] = d[i][j - 1] + a[i][j]
                p[i][j] = 'R'
            if (i > 1 and d[i][j] < d[i - 1][j] + a[i][j]):
                d[i][j] = d[i - 1][j] + a[i][j]
                p[i][j] = 'D'

i, j = n, m
while (i != 1 or j != 1):
    way = p[i][j] + way
    if (p[i][j] == 'R'):
        j -= 1
    elif (p[i][j] == 'D'):
        i -= 1

print(d[n][m])
print(way)