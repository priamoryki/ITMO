def siftUp(a, x):
    a.append(x)
    i = len(a) - 1
    while (i > 0 and a[i] > a[(i - 1)//2]):
        a[i], a[(i - 1)//2] = a[(i - 1)//2], a[i]
        i = (i - 1)//2
    return a

def removeMax(a):
    a[0] = a[len(a) - 1]
    a.pop()
    n = len(a)
    i = 0
    while (True):
        j = i
        if (2*i + 1 < n and a[2*i + 1] > a[j]):
            j = 2*i + 1
        if (2*i + 2 < n and a[2*i + 2] > a[j]):
            j = 2*i + 2
        if (i == j):
            break
        a[i], a[j] = a[j], a[i]
        i = j
    return a

a = []
res = []
n = int(input())
for i in range(n):
    el = list(map(int, input().split()))
    if (el[0] == 0):
        a = siftUp(a, el[1])
    else:
        res.append(a[0])
        removeMax(a)

for i in res:
    print(i)