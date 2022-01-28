m = []
n = int(input())

for _ in range(n):
    m.append(list(map(int, input().split())))

dp = m
for k in range(n):
    for i in range(n):
        for j in range(n):
            dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j])

for i in dp:
    print(' '.join(map(str, i)))