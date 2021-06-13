def f(pref, k):
    if (k == 0):
        out.write('+'.join(pref) + '\n')
    if (len(pref) != 0):
        for i in range(int(pref[-1]), n + 1):
            if (k >= i):
                temp = pref.copy()
                temp.append(str(i))
                f(temp, k - i)
    else:
        for i in range(1, n + 1):
            if (k >= i):
                temp = pref.copy()
                temp.append(str(i))
                f(temp, k - i)

inp = open('partition.in', 'r')
n = int(inp.readline())
inp.close()

out = open('partition.out', 'w')
f([], n)
out.close()