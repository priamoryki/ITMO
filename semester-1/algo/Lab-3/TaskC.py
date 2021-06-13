n = int(input())
a = list(map(int, input().split()))
maxId, d, p = 0, [1 for i in range(n)], [i for i in range(n)]

for i in range(n):
    for j in range(i):
        if (a[j] < a[i] and d[i] < 1 + d[j]):
            d[i] = 1 + d[j]
            p[i] = j
    if (d[maxId] < d[i]):
        maxId = i

i, k, res = maxId, d[maxId], []
while (k != 0):
    res = [a[i], *res]
    i = p[i]
    k -= 1

print(d[maxId])
print(' '.join(map(str, res)))