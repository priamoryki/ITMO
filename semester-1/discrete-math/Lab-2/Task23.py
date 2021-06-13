def prev(a):
    a = [i for i in a]
    n = len(a) - 1
    while ((n >= 0) and (a[n] != '1')):
        a[n] = '1'
        n -= 1
    if (n == -1):
        return '-'
    a[n] = '0'
    return ''.join(a)

def nxt(a):
    a = [i for i in a]
    n = len(a) - 1
    while ((n >= 0) and (a[n] != '0')):
        a[n] = '0'
        n -= 1
    if (n == -1):
        return '-'
    a[n] = '1'
    return ''.join(a)

inp = open('nextvector.in', 'r')
s = str(inp.readline().split()[0])
inp.close()

out = open('nextvector.out', 'w')
out.write(prev(s) + '\n' + nxt(s))
out.close()