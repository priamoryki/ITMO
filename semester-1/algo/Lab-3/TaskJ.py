def comp(p1, p2):
    global n
    for i in range(1, n):
        if (((p1 >> i) & 1 == (p2 >> i) & 1) and ((p1 >> (i - 1)) & 1 == (p1 >> i) & 1) and ((p2 >> (i - 1)) & 1 == (p2 >> i) & 1)):
            return False
    return True


n, m = map(int, input().split())

if (n > m):
    n, m = m, n

dp = [[0 for j in range(2 ** n)] for i in range(m)]
dp[0] = [1] * (2 ** n)

for i in range(m - 1):
    for p in range(2 ** n):
        for new_p in range(2 ** n):
            if (comp(p, new_p)):
                dp[i + 1][new_p] += dp[i][p]

print(sum(dp[-1]))