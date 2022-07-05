def gcdex(a, b):
    if a == 0:
        return 0, 1, b
    x, y, d = gcdex(b % a, a)
    return y - b // a * x, x, d


def find_solution(n, m, c):
    x, _, g = gcdex(abs(n), abs(m))
    x *= c // g
    return x if n >= 0 else -x


a, b, n, m = map(int, input().split())
print((a + find_solution(n, m, b - a) * n) % (m * n))
