def f(i, j):
    if (dp[i][j] != -1):
        return dp[i][j]
    if (j > i):
        return float('inf')
    else:
        if (j == 0):
            if (i != 0):
                if (a[i] <= 100):
                    dp[i][j] = min(f(i - 1, j) + a[i], f(i - 1, j + 1))
                else:
                    return f(i - 1, j + 1)
            else:
                return 0
        else:
            if (a[i] <= 100):
                dp[i][j] = min(f(i - 1, j) + a[i], f(i - 1, j + 1))
            else:
                dp[i][j] = min(f(i - 1, j - 1) + a[i], f(i - 1, j + 1))
        return dp[i][j]


def find_days(i, j):
    if (j < i):
        if (j <= 0):
            if (i >= 1):
                if ((a[i] > 100) or f(i - 1, j + 1) == f(i, j)):
                    days.append(i)
                    find_days(i - 1, j + 1)
                else:
                    find_days(i - 1, j)
        else:
            if (a[i] <= 100):
                if (f(i - 1, j + 1) == f(i, j)):
                    days.append(i)
                    find_days(i - 1, j + 1)
                else:
                    find_days(i - 1, j)
            else:
                if (f(i - 1, j + 1) == f(i, j)):
                    days.append(i)
                    find_days(i - 1, j + 1)
                else:
                    find_days(i - 1, j - 1)


n = int(input())
k1, days, a = 0, [], [0]
for i in range(n): 
    a.append(int(input()))
dp = [[-1 for j in range(n + 2)] for i in range(n + 1)]

for i in range(n + 1):
    if (f(n, k1) >= f(n, i)):
        k1 = i
print(f(n, k1))

find_days(n, k1)
print(k1, len(days))
while (len(days) != 0):
    print(days.pop())