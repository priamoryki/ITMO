import math

def f(a):
    res, st = 0, 1
    for i in range(k):
        for j in range(st, a[i]):
            res += math.factorial(n - j)/(math.factorial(k - (i + 1)) * math.factorial((n - j) - (k - (i + 1))))
        st = a[i] + 1
    return int(res)

inp = open('choose2num.in', 'r')
n, k = map(int, inp.readline().split())
a = list(map(int, inp.readline().split()))
inp.close()

out = open('choose2num.out', 'w')
out.write(str(f(a)))
out.close()