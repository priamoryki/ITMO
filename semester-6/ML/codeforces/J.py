import math

kx, ky = map(int, input().split())
n = int(input())
p_x, p_xy = {}, {}
for _ in range(n):
    x, y = map(int, input().split())
    p_x[x] = p_x.get(x, 0) + 1 / n
    new_p_xy = p_xy.get(x, {})
    new_p_xy[y] = new_p_xy.get(y, 0) + 1 / n
    p_xy[x] = new_p_xy

print(-sum(sum(p_xy[x][y] * math.log(p_xy[x][y] / p_x[x]) for y in p_xy[x]) for x in p_x))
