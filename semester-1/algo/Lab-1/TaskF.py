a = []
n, m = map(int, input().split())
el = list(map(int, input().split()))
for i in range(n):
    a.append(el[i])

el = list(map(int, input().split()))
for i in range(m):
    x = el[i]
    l, r = -1, n
    while (r - l > 1):
        m = (l + r)//2
        if (a[m] <= x):
            l = m
        else:
            r = m
    if (abs(a[r - 1] - x) <= abs(a[r] - x)):
        print(a[r - 1])
    else:
        print(a[r])