def sort(a):
    res, counter = [], [0]*101
    for i in a:
        counter[i] += 1
    for i in range(101):
        for j in range(counter[i]):
            res.append(i)
    return res

a = []
n = int(input())
el = list(map(int, input().split()))
for i in range(n):
    a.append(el[i])

a = sort(a)

for i in a:
    print(i, end = ' ')