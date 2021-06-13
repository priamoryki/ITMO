import sys

def get(x):
    return p[x]

def union(x, y):
    x, y = get(x), get(y)
    if (x != y):
        if (len(lists[x]) > len(lists[y])):
            x, y = y, x
        m1[y] = min(m1[x], m1[y])
        m2[y] = max(m2[x], m2[y])
        for i in lists[x]:
            p[i] = y
            lists[y].append(i)

n = int(input())
lists = [[i] for i in range(n + 1)]
p = [i for i in range(n + 1)]
m1 = [i for i in range(n + 1)]
m2 = [i for i in range(n + 1)]

for i in sys.stdin:
    a = list(i.split())
    if (a[0] == 'union'):
        union(int(a[1]), int(a[2]))
    elif (a[0] == 'get'):
        x = get(int(a[1]))
        print(m1[x], m2[x], len(lists[x]))