k, n = int(input()), int(input())
ys, p_x, p_e = [], {}, {}
for _ in range(n):
    x, y = map(int, input().split())
    ys.append(y)
    p_x[x] = p_x.get(x, 0) + 1 / n
    p_e[x] = p_e.get(x, 0) + y / n

print(sum(i ** 2 / n for i in ys) - sum(p_e[x] ** 2 / p_x[x] for x in p_x))
