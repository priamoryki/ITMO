def get_id(x: int, y: int) -> int:
    global m
    return m * x + y


def is_free(x: int, y: int) -> bool:
    global n, m, board
    return 0 <= x < n and 0 <= y < m and not board[x][y]


def is_black_cell(x: int, y: int) -> bool:
    return (x + y) % 2 == 1


def dfs(v: int) -> bool:
    global g, matching, is_used
    if is_used[v]:
        return False

    is_used[v] = True
    for u in g[v]:
        if matching[u] == -1 or dfs(matching[u]):
            matching[u] = v
            return True
    return False


max_board_size = 10000
n, m, a, b = map(int, input().split())
g, matching = [[] for _ in range(max_board_size)], [-1 for _ in range(max_board_size)]
count_free, board = 0, [[False for _ in range(m)] for _ in range(n)]
for i in range(n):
    line = input()
    for j in range(m):
        if line[j] == '.':
            board[i][j] = True
        else:
            count_free += 1


shifts = [(0, 1), (1, 0), (0, -1), (-1, 0)]
for i in range(n):
    for j in range(m):
        if is_free(i, j) and is_black_cell(i, j):
            for x_shift, y_shift in shifts:
                x, y = i + x_shift, j + y_shift
                if is_free(x, y):
                    g[get_id(i, j)].append(get_id(x, y))

for i in range(n * m):
    is_used = [False for _ in range(max_board_size)]
    dfs(i)

res = [(matching[i] + 1, i + 1) for i in range(n * m) if matching[i] != -1]
print(count_free)
print(b * count_free + min(0, len(res) * (a - 2 * b)))
