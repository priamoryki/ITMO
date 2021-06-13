def toBin(n, k):
    r = bin(n)[2::]
    return '0'*(int(k) - len(r)) + r

def bitNot(n):
    res = ''
    for i in s:
        if (int(i)):
            res += '0'
        else:
            res += '1'
    return int(res, base = 2)

def bitAnd(x, y):
    res = ''
    for i in range(len(x)):
        if (x[i] == '1' and y[i] == '1'):
            res += '1'
        else:
            res += '0'
    return res

def bitOr(x, y):
    res = ''
    for i in range(len(x)):
        if (x[i] == '1' or y[i] == '1'):
            res += '1'
        else:
            res += '0'
    return res

a = []
n = int(input())
for i in range(n):
    x = toBin(int(input()), 32)
    a.append(x)
    a.append(bitNot(x))
s = toBin(int(input()), 32)