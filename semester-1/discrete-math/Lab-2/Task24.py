def prev(a):
    a = a.copy()
    for i in range(n - 2, -1, -1):
        if (a[i] > a[i + 1]):
            m = i + 1
            for j in range(i + 1, n, 1):
                if ((a[j] > a[m]) and (a[j] < a[i])):
                    m = j
            a[i], a[m] = a[m], a[i]
            return [*a[:i + 1], *reversed(a[i + 1:n])]
    return [0] * n

def next(a):
    a = a.copy()
    for i in range(n - 2, -1, -1):
        if (a[i] < a[i + 1]):
            m = i + 1;
            for j in range(i + 1, n, 1):
                if ((a[j] < a[m]) and (a[j] > a[i])):
                    m = j
            a[i], a[m] = a[m], a[i]
            return [*a[:i + 1], *reversed(a[i + 1:n])]
    return [0] * n 

inp = open('nextperm.in', 'r')
n = int(inp.readline())
a = list(map(int, inp.readline().split()))
inp.close()

out = open('nextperm.out', 'w')
out.write(' '.join(map(str, prev(a))) + '\n' + ' '.join(map(str, next(a))))
out.close()