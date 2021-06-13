def f(pref):
    if (n == len(pref)):
        out.write(pref + '\n')
        out.write(g(pref, 1) + '\n')
        out.write(g(pref, 2) + '\n')
        return
    for i in range(3):
        f(pref + str(i))

def g(s, k):
    res = ''
    for i in s:
        res += str((int(i) + k) % 3)
    return res

inp = open('antigray.in', 'r')
n = int(inp.readline())
inp.close()

out = open('antigray.out', 'w')
f('0')
out.close()