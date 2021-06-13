def f(n):
    if (n == 0):
        return ['0', '1']
    a = f(n - 1)
    for i in reversed(a):
        a.append(i)
    for i in range(len(a)):
        if (i < len(a)/2):
            a[i] = '0' + a[i]
        else:
            a[i] = '1' + a[i]
    return a

inp = open('gray.in', 'r')
n = int(inp.readline())
inp.close()

out = open('gray.out', 'w')
for i in f(n - 1):
    out.write(i + '\n')
out.close()