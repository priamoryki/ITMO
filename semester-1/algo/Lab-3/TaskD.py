n, mod = int(input()), 10 ** 9
d = [[0 for j in range(n + 1)] for i in range(10)]

for i in range(10):
    d[i][1] = 1

for i in range(2, n + 1):
    d[0][i] = (d[4][i - 1] + d[6][i - 1]) % mod
    d[1][i] = (d[6][i - 1] + d[8][i - 1]) % mod
    d[2][i] = (d[9][i - 1] + d[7][i - 1]) % mod
    d[3][i] = (d[8][i - 1] + d[4][i - 1]) % mod
    d[4][i] = (d[0][i - 1] + d[3][i - 1] + d[9][i - 1]) % mod
    d[6][i] = (d[0][i - 1] + d[1][i - 1] + d[7][i - 1]) % mod
    d[7][i] = (d[6][i - 1] + d[2][i - 1]) % mod
    d[8][i] = (d[1][i - 1] + d[3][i - 1]) % mod
    d[9][i] = (d[2][i - 1] + d[4][i - 1]) % mod

res = 0
for i in range(1, 10):
    if (i != 8):
        res += d[i][n]
        res %= mod

print(res)