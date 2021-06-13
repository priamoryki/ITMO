x, temp, res = [], [], []

n = int(input())
for i in range(2**n):
    a = list(map(str, input().split()))
    temp.append(int(a[1]))
    x.append(a[0])

res.append(temp[0])
while (len(temp) != 1):
    for i in range(len(temp) - 1):
        temp[i] = temp[i] ^ temp[i + 1]
    res.append(temp[0])
    temp.pop()

for i in range(2**n):
    print(x[i], res[i])