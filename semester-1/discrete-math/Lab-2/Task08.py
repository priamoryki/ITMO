def f(pref):
    if (k == len(pref)):
        out.write(' '.join(pref) + '\n')
        return
    if (len(pref) != 0):
        for i in range(int(pref[-1]) + 1, n + 1):
            if (str(i) not in pref):
                temp = pref.copy()
                temp.append(str(i))
                f(temp)

    else:
        for i in range(1, n + 1):
            temp = pref.copy()
            temp.append(str(i))
            f(temp)

inp = open('choose.in', 'r')
n, k = map(int, inp.readline().split())
inp.close()

out = open('choose.out', 'w')
f([])
out.close()