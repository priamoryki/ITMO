def set(i, v):
    x = n + i - 1
    delta = v - tree[x]
    while (x >= 0):
        tree[x] += delta
        x = (x + 1) // 2 - 1

def my_sum(l, r):
    result = 0
    l += n - 1
    r += n - 2
    while (r >= l):
        if (l % 2 == 0):
            result += tree[l]
        l = l // 2
        if (r % 2 == 1):
            result += tree[r]
        r = r // 2 - 1
    return result

n, m = map(int, input().split())
a = list(map(int, input().split()))

tree = [0 for i in range(2 * n - 1)]
for i in range(n):
    tree[n + i - 1] = a[i]
for i in range(n - 2, -1, -1):
    tree[i] = tree[2 * i + 1] + tree[2 * i + 2]

print(tree)
result = []
for i in range(m):
    temp = list(map(int, input().split()))
    if (temp[0] == 1):
        set(temp[1], temp[2])
    elif (temp[0] == 2):
        result.append(my_sum(temp[1], temp[2]))

print('\n'.join(map(str, result)))