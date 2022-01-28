def kuhn_algo(v: int) -> bool:
    if (used[v]):
        return False
    used[v] = True
    for to in out[v]:
        if (match[to] == -1 or kuhn_algo(match[to])):
            match[to] = v
            return True
    return False

with open('matching.in') as f:
    n = int(f.readline())
    used, match = [False for _ in range(n)], [-1 for _ in range(n)]
    temp = [*map(int, f.readline().split())]
    out = [[*map(lambda x: int(x) - 1, f.readline().split()[1:])] for _ in range(n)]

for v in sorted([[temp[i], i] for i in range(n)], reverse=True):
    used = [False for _ in range(n)]
    kuhn_algo(v[1])

result = [-1 for _ in range(n)]
for i in range(n):
    if (match[i] != -1):
        result[match[i]] = i
print(' '.join(map(lambda x: str(x + 1), result)), file=open('matching.out', 'w'))