def fun(x):
    if (check(x)[0] == 0):
        return [False, x]
    res = True
    for i in range(len(x)):
        if (t[i] != -1 and t[i] == x[i]):
            res = False
            for j in range(len(x)):
                x[j] = -1
            break
        if (t[i] == -1 and x[i] != -1):
            res = False
        if ((t[i] == 1 and x[i] == 0) or (t[i] == 0 and x[i] == 1)):
            x[i] = -1
    return [res, x]
 
def check(x):
    res = [i for i in range(len(x)) if x[i] != -1]
    if (len(res) != 0):
        return len(res), res[0]
    else:
        return len(res), -1
 
def res():
    while True:
        w = True
        for i in range(k):
            x = list(check(a[i]))
            if (x[0] == 1):
                w = False
                t[x[1]] = a[i][x[1]]
                break
        for i in range(k):
            q, a[i] = fun(a[i])
            if (q):
                return "YES"
         
        if (w):
            return "NO"
 
n, k = map(int, input().split())
a, t = [], [-1]*n
for i in range(k):
    a.append(list(map(int, input().split())))
 
print(res())