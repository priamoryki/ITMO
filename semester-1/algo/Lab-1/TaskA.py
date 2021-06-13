def merge(a, b):
    n, m = len(a), len(b)
    i, j = 0, 0
    c = []
    while (i < n or j < m):
        if (j == m or (i < n and a[i] < b[j])):
            c.append(a[i])
            i += 1
        else:
            c.append(b[j])
            j += 1
    return c

def sort(a):
    n = len(a)
    if (n <= 1):
        return a
    al = a[:(n//2)]
    ar = a[(n//2):]
    al, ar = sort(al), sort(ar)
    return merge(al, ar)

a = []
n = int(input())
el = list(map(int, input().split()))
for i in range(n):
    a.append(el[i])

a = sort(a)

for i in a:
    print(i, end = ' ')