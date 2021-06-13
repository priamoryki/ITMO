def next(a):
    a.append(n + 1)
    i = k - 1
    while ((i >= 0) and (a[i + 1] - a[i] < 2)):
        i -= 1
    if (i >= 0):
        a[i] += 1
        for j in range(i + 1, k):
            a[j] = a[j - 1] + 1
        return a[:k]
    else:
        return [-1]

inp = open('nextchoose.in', 'r')
n, k = map(int, inp.readline().split())
a = list(map(int, inp.readline().split()))
inp.close()

out = open('nextchoose.out', 'w')
out.write(' '.join(map(str, next(a))))
out.close()