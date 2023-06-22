def get_rank(arr):
    result, cur = {}, 1
    for a in sorted(arr):
        if a not in result:
            result[a] = cur
            cur += 1
    return result


n = int(input())
positions = [[*map(int, input().split())] for _ in range(n)]
xs, ys = [positions[i][0] for i in range(n)], [positions[i][1] for i in range(n)]

x_ranks, y_ranks = get_rank(xs), get_rank(ys)
print(1 - 6 * sum((x_ranks[pos[0]] - y_ranks[pos[1]]) ** 2 for pos in positions) / (n ** 3 - n))
