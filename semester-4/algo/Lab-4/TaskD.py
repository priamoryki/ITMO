def gcdex(a, b):
    if a == 0:
        return 0, 1, b
    x1, y1, d = gcdex(b % a, a)
    return y1 - b // a * x1, x1, d


def get_reverse(a, mod):
    x, _, g = gcdex(a, mod)
    return (x // g) % mod


n, e, c = int(input()), int(input()), int(input())
for i in range(2, n + 1):
    if n % i == 0:
        break
p, q = i, n // i
d = get_reverse(e, (p - 1) * (q - 1))

print(pow(c, d, n))
