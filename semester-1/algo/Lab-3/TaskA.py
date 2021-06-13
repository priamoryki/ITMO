n, k = map(int, input().split())
a = [0, 0, *list(map(int, input().split())), 0]
nums, d = [0 for i in range(n + 1)], [0 for i in range(n + 1)]

for i in range(2, n + 1):
    d[i] = float('-inf')
    for j in range(1, k + 1):
        if (i - j >= 1):
            if (d[i - j] + a[i] > d[i]):
                d[i] = d[i - j] + a[i]
                nums[i] = i - j

i, res = n, []
while (i != 0):
    res = [i, *res]
    i = nums[i]

print(d[-1])
print(len(res) - 1)
print(' '.join(list(map(str, res))))