def f(pref):
    if (n == len(pref)):
        out.write(pref + '\n')
        return
    f(pref + '0')
    f(pref + '1')

inp = open('allvectors.in', 'r')
n = int(inp.readline())
inp.close()

out = open('allvectors.out', 'w')
f('')
out.close()