def good(x, C):
    return x**2 + x**(1/2) > C

def solve(EPS, C):
    l, r = 0, C
    while (r - l > EPS):
        m = (l + r)/2
        if (good(m, C)):
            r = m
        else:
            l = m
    return r

EPS = 10**(-6)
C = float(input())

print("%.6f" % solve(EPS, C))