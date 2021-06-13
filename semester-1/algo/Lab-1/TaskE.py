a, b = [], []
n = int(input())
el = list(map(int, input().split()))
for i in range(n):
    a.append(el[i])
k = int(input())
a.sort()

for i in range(k):
    l, r = map(int, input().split())
    L, R = -1, len(a)
    while (R - L > 1):
        m = (L + R)//2
        if (a[m] <= r):
            L = m
        else:
            R = m
    end =  R
    L, R = -1, len(a)
    while (R - L > 1):
        m = (L + R)//2
        if (a[m] < l):
            L = m
        else:
            R = m
    st =  R
    b.append(end - st)

for i in b:
    print(i, end = ' ')