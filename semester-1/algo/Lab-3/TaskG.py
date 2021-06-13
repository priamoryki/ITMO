def answer(left, right):
    global s, res, p
    if (dp[left][right] == right - left + 1):
        return
    elif (dp[left][right] == 0):
        res += s[left:right + 1]
    elif (p[left][right] == -1):
        res += s[left]
        answer(left + 1, right - 1)
        res += s[right]
    else:
        answer(left, p[left][right])
        answer(p[left][right] + 1, right)

res, s = '', input().split()[0]
n = len(s)
dp, p = [[0 for j in range(n)] for i in range(n)], [[0 for j in range(n)] for i in range(n)]

for right in range(0, n):
    for left in range(right, -1, -1):
        if (left == right):
            dp[left][right] = 1
        else:
            index, minimal = -1, float('inf')
            if ((s[left] == '(' and s[right] == ')') or (s[left] == '[' and s[right] == ']') or (s[left] == '{' and s[right] == '}')):
                minimal = dp[left + 1][right - 1]
            for i in range(left, right):
                if (minimal > dp[left][i] + dp[i + 1][right]):
                    index, minimal = i, dp[left][i] + dp[i + 1][right]
            p[left][right], dp[left][right] =  index, minimal

answer(0, n - 1)
print(res)