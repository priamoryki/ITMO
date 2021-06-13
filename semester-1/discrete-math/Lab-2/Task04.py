def f(n):
    current, q = '0' * n, True
    res = {current : 0}
    while ('0' * n != current or q):
        if(q):
            q = False
        else:
            res[current] = 0
        prefix = current[1:]
        if (prefix + '1' not in res.keys()):
            current = prefix + '1'
        else:
            current = prefix + '0'
    return res

inp = open('chaincode.in', 'r')
n = int(inp.readline())
inp.close()

out = open('chaincode.out', 'w')
out.write('\n'.join(f(n).keys()))
out.close()