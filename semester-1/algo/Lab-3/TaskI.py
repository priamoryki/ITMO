n , m = map(int, input().split())
a = []
for i in range(n):
    a.append(input().split()[0])

dp = [[[0 for p in range(2 ** n)] for j in range(n)] for i in range(m)]
dp[0][0][0] = 1

for i in range(m):
    for j in range(n):
        for p in range(2 ** n):
            if ((p >> j) & 1):
                new_p = p - 2 ** j
                dp[i][j + 1][new_p] = dp[i][j][p]
            else:
                new_p = p + 2 ** j
                dp[i][j + 1][new_p] += dp[i][j][p]
                if ((j < n - 1) and ((p >> (j + 1)) & 1 == 0)):
                    new_p = p + 2 ** (j + 1)
                    dp[i][j + 1][new_p] += dp[i][j][p]
    for p in range(2 ** n):
        dp[i + 1][0][p] = dp[i][n][p]

print(sum(dp[-1][-1]))