def fun(t):
    global k
    while(len(t) != 1):
        print('2 ' + t[0] + ' '+ t[1])
        t[1] = str(k)
        k += 1
        t.pop(0)

n = int(input())
a, res, k = [], [], 2*n + 1
for i in range(2**n):
    b = list(map(str, input().split()))
    if (int(b[1])):
        a.append(b[0])

for i in a:
    b = []
    for j in range(len(i)):
        if (not int(i[j])):
            b.append(str(n + j + 1))
        else:
            b.append(str(j + 1))
    res.append(b)

if (len(a) != 0) :
    print(n*(len(res) + 2) - 1)
    for i in range(n):
        print('1 ' + str(i + 1))
    for i in range(len(res)):
        fun(res[i])
        res[i] = res[i][0]
    
    while(len(res) != 1):
        print('3 ' + res[0] + ' '+ res[1])
        res[1] = str(k)
        k += 1
        res.pop(0)
else:
    print(n + 2)
    print('1 1')
    print('2 1 ' + str(n + 1))