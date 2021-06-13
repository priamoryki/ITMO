import math

def f(k, pref):
    if (k == 0 and len(pref) == n):
        return pref
    for i in range(1, n + 1):
        if (str(i) not in pref):
            m = math.factorial(n - (len(pref) + 1))
            if (k >= m):
                k -= m
            else:
                return f(k, [*pref, str(i)])

inp = open('num2perm.in', 'r')
n, k = map(int, inp.readline().split())
inp.close()

out = open('num2perm.out', 'w')
out.write(' '.join(f(k, [])))
out.close()