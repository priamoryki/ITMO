s, t = input().split()[0], input().split()[0]
d = [[0 for j in range(len(t) + 1)] for i in range(len(s) + 1)]

for i in range(len(s) + 1):
    for j in range(len(t) + 1):
        if (i == 0 or j == 0):
            d[i][j] = i + j
        else:
            if (s[i - 1] == t[j - 1]):
                d[i][j] = d[i - 1][j - 1]
            else:
                d[i][j] = min(d[i - 1][j - 1], d[i - 1][j], d[i][j - 1]) + 1

for i in d:
    for j in i:
        print(j, end='')
    print()

print(d[-1][-1])