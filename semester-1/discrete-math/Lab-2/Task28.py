def next(a):
    i = n - 2
    while ((i >= 0) and (a[i] >= a[i + 1])):
        i -= 1
    if (i >= 0):
        j = i + 2
        while ((j < n) and (a[j] > a[i])):
            j += 1
        a[i], a[j - 1] = a[j - 1], a[i]
        return [*a[:i + 1], *reversed(a[i + 1:n])]
    else:
        return [0] * n

inp = open('nextmultiperm.in', 'r')
n = int(inp.readline())
a = list(map(int, inp.readline().split()))
inp.close()

out = open('nextmultiperm.out', 'w')
out.write(' '.join(map(str, next(a))))
out.close()