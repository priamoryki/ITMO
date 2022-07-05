def dfs(v: int) -> bool:
    global g, matching, is_used
    if is_used[v]:
        return False

    is_used[v] = True
    for i in range(matrix_size):
        if g[v][i] != 0 and (matching[i] == -1 or dfs(matching[i])):
            matching[i] = v
            return True
    return False


m, k, n = map(int, input().split())
t = int(input())
matrix_size = m + k - n
g = [[0 if (m <= i and k <= j) else 1 for j in range(matrix_size + 100)] for i in range(matrix_size + 100)]
matching = [-1 for _ in range(matrix_size + 100)]

for i in range(t):
    x, y = sorted(map(lambda a: int(a) - 1, input().split()))
    g[x][y - m] = 0

q = int(input())
for x in list(map(lambda a: int(a) - 1, input().split())):
    if x < m:
        for j in range(k, matrix_size):
            g[x][j] = 0
    else:
        for j in range(m, matrix_size):
            g[j][x - m] = 0

flag = True
for i in range(matrix_size):
    is_used = [False for _ in range(matrix_size)]
    if not dfs(i):
        print("NO")
        flag = False
if flag:
    print("YES")
    print('\n'.join([f"{matching[i] + 1} {i + m + 1}" for i in range(k) if matching[i] < m]))
