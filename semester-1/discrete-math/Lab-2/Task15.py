import math

def f(m, pref):
    if (m == 0 and len(pref) == k):
        return pref
    for i in range(1, n + 1):
        if (len(pref) == 0 or int(pref[-1]) < i):
            r = math.factorial(n - i)/(math.factorial(k - len(pref) - 1) * math.factorial((n - i) - (k - len(pref) - 1)))
            if (m >= r):
                m -= r
            else:
                return f(m, [*pref, str(i)])

inp = open('num2choose.in', 'r')
n, k, m = map(int, inp.readline().split())
inp.close()

out = open('num2choose.out', 'w')
out.write(' '.join(f(m, [])))
out.close()