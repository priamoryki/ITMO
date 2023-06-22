def summary(arr):
    r, acc = [], 0
    for i in range(len(arr) - 1):
        acc += arr[i + 1] - arr[i]
        r.append(acc)

    l, r = 0, sum(r)
    res = r
    for i in range(1, len(arr)):
        l += i * (arr[i] - arr[i - 1])
        r -= (len(arr) - i) * (arr[i] - arr[i - 1])
        res += r + l

    return res


k = int(input())
n = int(input())
pairs = []
for _ in range(n):
    x, y = map(int, input().split())
    pairs.append((x, y))

pairs.sort(key=lambda p: p[0])

out = summary(list(map(lambda p: p[0], pairs)))

groups = {}
for pair in pairs:
    x, y = pair[0], pair[1]
    groups[y] = groups.get(y, [])
    groups[y].append(x)

for key, group in groups.items():
    groups[key] = summary(group)

inp = sum(groups.values())

print(inp)
print(out - inp)
