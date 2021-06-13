def f(pref):
    if (n == len(pref)):
        a.append(pref)
        return
    f(pref + '0')
    f(pref + '1')

inp = open('vectors.in', 'r')
n = int(inp.readline())
inp.close()

a, res = [], []
out = open('vectors.out', 'w')
f('')
for i in a:
    if ('11' not in i):
        res.append(i)
out.write(str(len(res)) + '\n')
for i in res:
    out.write(i + '\n')
out.close()