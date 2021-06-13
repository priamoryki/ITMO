def f(pref):
    if (2*n == len(pref)):
        out.write(pref + '\n')
    for i in range(1, n + 1):
        if (str(i) not in pref):
            f(pref + str(i) + ' ')

inp = open('permutations.in', 'r')
n = int(inp.readline())
inp.close()

out = open('permutations.out', 'w')
f('')
out.close()