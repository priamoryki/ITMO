from dataclasses import dataclass


@dataclass(frozen=True)
class Section:
    x_start: float
    y_start: float
    x_end: float
    y_end: float


def is_lines_crossing(s1: Section, s2: Section) -> bool:
    return s2.y_start <= s1.y_start <= s2.y_end and s1.x_start <= s2.x_start <= s1.x_end


def dfs(v: int) -> bool:
    global px, py, is_visited
    if is_visited[v]:
        return False

    is_visited[v] = True
    for u in horizontal_edges[v]:
        if py[u] == -1 or dfs(py[u]):
            py[u] = v
            px[v] = u
            return True
    return False


def horizontal_dfs(v: int) -> None:
    global px, horizontal_visited
    horizontal_visited[v] = True
    for u in horizontal_edges[v]:
        if px[v] != u and not vertical_visited[u]:
            vertical_dfs(u)


def vertical_dfs(v: int) -> None:
    global py, vertical_visited
    vertical_visited[v] = True
    for u in vertical_edges[v]:
        if py[v] == u and not horizontal_visited[u]:
            horizontal_dfs(u)


n = int(input())
sections, vertical_part, horizontal_part, vertical_map, horizontal_map = [], set(), set(), {}, {}
for i in range(n):
    tmpx1, tmpy1, tmpx2, tmpy2 = map(int, input().split())
    if (tmpx1 == tmpx2 and tmpy1 > tmpy2) or (tmpy1 == tmpy2 and tmpx1 > tmpx2):
        sections.append(Section(tmpx2, tmpy2, tmpx1, tmpy1))
    else:
        sections.append(Section(tmpx1, tmpy1, tmpx2, tmpy2))
    if sections[i].x_start == sections[i].x_end:
        vertical_part.add(i)
        vertical_map[i] = len(vertical_map)
    elif sections[i].y_start == sections[i].y_end:
        horizontal_part.add(i)
        horizontal_map[i] = len(horizontal_map)

vertical_size, horizontal_size = len(vertical_map), len(horizontal_map)
vertical_edges, horizontal_edges = [[] for _ in range(vertical_size)], [[] for _ in range(horizontal_size)]
for i in horizontal_part:
    for j in vertical_part:
        if is_lines_crossing(sections[i], sections[j]):
            horizontal_edges[horizontal_map[i]].append(vertical_map[j])
            vertical_edges[vertical_map[j]].append(horizontal_map[i])

px, py = [-1 for _ in range(horizontal_size)], [-1 for _ in range(vertical_size)]
is_visited = [False for _ in range(horizontal_size)]
while True:
    flag, is_visited = False, [False for _ in range(horizontal_size)]
    for i in range(horizontal_size):
        if px[i] == -1 and dfs(i):
            flag = True
    if not flag:
        break

vertical_visited, horizontal_visited = [False for _ in range(vertical_size)], [False for _ in range(horizontal_size)]
for i in range(horizontal_size):
    if px[i] == -1:
        horizontal_dfs(i)
vertical = set(i for i in range(vertical_size) if not vertical_visited[i])
horizontal = set(i for i in range(horizontal_size) if horizontal_visited[i])

print(len(vertical) + len(horizontal))
