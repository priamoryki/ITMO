def polynomial_mod(a):
    return [i % MOD for i in a]


def polynomial_sum(a, b):
    result = [0 for _ in range(max(len(a), len(b)))]
    for i in range(len(a)):
        result[i] += a[i]
    for i in range(len(b)):
        result[i] += b[i]
    return polynomial_mod(result)


def polynomial_mul(a, b):
    result = [0 for _ in range(len(a) + len(b) - 1)]
    for i in range(len(a)):
        for j in range(len(b)):
            result[i + j] += a[i] * b[j]
    return polynomial_mod(result)


def polynomial_div(a, b):
    result = [0 for _ in range(1000)]
    result[0] = a[0] * b[0] ** (MOD - 2)
    for i in range(1, len(result)):
        if i < len(a):
            result[i] = a[i]
        result[i] -= sum(result[j] * b[i - j] for j in range(i) if 0 <= i - j < len(b))
    return polynomial_mod(result)


MOD = 998_244_353
n, m = map(int, input().split())
a = list(map(int, input().split()))
b = list(map(int, input().split()))
c = polynomial_sum(a, b)
d = polynomial_mul(a, b)
print(len(c) - 1)
print(" ".join(map(str, c)))
print(len(d) - 1)
print(" ".join(map(str, d)))
print(" ".join(map(str, polynomial_div(a, b))))
