import sys

def get(x):
    if (x == p[x]):
        return x
    else:
        return get(p[x])

def union(x, y):
    x, y = get(x), get(y)
    if (x != y):
        if (clans[x] < clans[y]):
            x, y = y, x
        p[y] = x
        exp[y] -= exp[x]
        if (clans[x] == clans[y]):
            clans[x] += 1

def add(x, y):
    exp[get(x)] += y

def countExp(x):
    if (x == p[x]):
        return exp[x]
    else:
        return countExp(p[x]) + exp[x]

n, m = map(int, input().split())
clans = [0 for i in range(n + 1)]
p = [i for i in range(n + 1)]
exp = [0 for i in range(n + 1)]

for i in sys.stdin:
    a = list(i.split())
    if (a[0] == 'join'):
        union(int(a[1]), int(a[2]))
    elif (a[0] == 'add'):
        add(int(a[1]), int(a[2]))
    elif (a[0] == 'get'):
        print(countExp(int(a[1])))