import math

def f(a):
    k = 0
    for i in range(len(a)):
        for j in range(1, a[i]):
            if (j not in a[:i]):
                k += math.factorial(n - (i + 1))
    return k

inp = open('perm2num.in', 'r')
n = int(inp.readline())
a = list(map(int, inp.readline().split()))
inp.close()

out = open('perm2num.out', 'w')
out.write(str(f(a)))
out.close()