import math


def fun():
    for i in range(1, n + 1):
        if (p[i] != 0):
            dp[i][0] = p[i]

    for i in range(1, math.ceil(math.log2(n))):
        for j in range(1, n + 1):
            if (dp[j][i - 1] == -1):
                dp[j][i] = -1
            else:
                dp[j][i] = dp[dp[j][i - 1]][i - 1]


n = int(input().split()[0])
root, dp = -1, [[-1 for j in range(math.ceil(math.log2(n)))] for i in range(n + 1)]

p = [0, *map(int, input().split())]
for i in range(1, n + 1):
    if (p[i] == 0):
        root = i

fun()

for i in range(1, n + 1):
    print(str(i) + ':', end=' ')
    if (i != root):
        print(*(j for j in dp[i] if j != -1), end='')
    print()