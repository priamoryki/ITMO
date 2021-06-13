def f(pref):
    out.write(' '.join(pref) + '\n')
    if (len(pref) != 0):
        for i in range(int(pref[-1]) + 1, n + 1):
            temp = pref.copy()
            temp.append(str(i))
            f(temp)
    else:
        for i in range(1, n + 1):
            temp = pref.copy()
            temp.append(str(i))
            f(temp)

inp = open('subsets.in', 'r')
n = int(inp.readline())
inp.close()

out = open('subsets.out', 'w')
f([])
out.close()