import math

def toBin(n, k):
    r = bin(n)[2::]
    return '0'*(int(k) - len(r)) + r

def comp(x, y):
    for i in range(len(x)):
        if (x[i] < y[i]):
            return False
    return True

def fun(f):
    for i in f:
        for j in f:
            if (comp(toBin(i, math.log2(len(f))), toBin(j, math.log2(len(f)))) and (f[i] < f[j])):
                return True
    return False

def linary(f):
    r, res, temp = {}, [], list(f)
    res.append(temp[0])
    while (len(temp) != 1):
        for i in range(len(temp) - 1):
            temp[i] = temp[i] ^ temp[i + 1]
        res.append(temp[0])
        temp.pop()
    for i in range(len(f)):
        if (toBin(i, math.log2(len(f))).count('1') > 1):
            r[i] = 0
        else:
            r[i] = 1
    for i in range(len(f)):
        if (res[i] == 1 and r[i] == 0):
            return False
    return True

def res(functions):
    a, b, c, d, e = False, False, False, False, False
    for i in functions:
        if (i[0] == 1):
            a = True
        if (i[len(i) - 1] == 0):
            b = True
        for j in i:
            if (i[j] == i[len(i) - (j + 1)]):
                c = True
        if (fun(i)):
            d = True
        if (not linary(list(i.values()))):
            e = True
    return a and b and c and d and e

f =[]
n = int(input())
for i in range(n):
    a = list(map(str, input().split()))
    k, x = 0, {}
    for j in a[1]:
        x[k] = int(j)
        k += 1
    f.append(x)

if (res(f)):
    print('YES')
else:
    print('NO')