def to_bin(n, k):
    r = bin(n)[2::]
    return '0' * (int(k) - len(r)) + r


n, a = int(input()), []
for i in range(n):
    a.append(list(map(int, input().split())))

dp = [[float('inf') for j in range(n)] for i in range(2 ** n)]
dp[0] = [0] * n
p = [[0 for j in range(n)] for i in range(2 ** n)]

for x in range(2 ** n):
    b = to_bin(x, n)
    for u in range(n):
        if (b[n - (u + 1)] == '1'):
            for v in range(n):
                if (dp[x - 2 ** u][v] + a[v][u] < dp[x][u]):
                    dp[x][u] = dp[x - 2 ** u][v] + a[v][u]
                    p[x][u] = v

index = 0
for i in range(n):
    if(dp[-1][i] < dp[-1][index]):
        index = i

print(dp[-1][index])

x, res = 2 ** n - 1, []
while (len(res) != n):
    res.append(index + 1)
    x -= 2 ** index
    index = p[x + 2 ** index][index]

print(*reversed(res))