MOD = 998_244_353


def gcdex(a, b):
    if a == 0:
        return 0, 1, b
    x1, y1, d = gcdex(b % a, a)
    return y1 - b // a * x1, x1, d


def elem(a):
    x, y, d = gcdex(a, MOD)
    return x


def polynomial_mul(a, b):
    result = [0 for _ in range(101)]
    for i in range(len(result)):
        for j in range(i + 1):
            result[i] += a[j] * b[i - j]
    return [i % MOD for i in result]


exp = [1]
for i in range(1, 101):
    exp.append((exp[i - 1] * elem(i)) % MOD)

log = [0, 1]
for i in range(2, 101):
    log.append((-log[i - 1] * (i - 1) * elem(i)) % MOD)

sqrt = [1]
for i in range(1, 101):
    sqrt.append((sqrt[i - 1] * (2 * i) * (2 * i - 1) * (2 * i - 3) * elem((i * i * 4 * (1 - 2 * i) + MOD))) % MOD)

n, m = map(int, input().split())
p = list(map(int, input().split()))
p.extend([0 for _ in range(101 - len(p))])
for func in [sqrt, exp, log]:
    temp, res = [0 if i != 0 else 1 for i in range(101)], [0 for _ in range(101)]
    for i in range(m):
        for j in range(len(temp)):
            res[j] += func[i] * temp[j]
        temp = polynomial_mul(temp, p)
    print(" ".join(map(lambda x: str(x % MOD), res[:m])))
