def polynomial_mul(a, b):
    result = [0 for _ in range(len(a) + len(b) - 1)]
    for i in range(len(a)):
        for j in range(len(b)):
            result[i + j] += a[i] * b[j]
    return [i % MOD for i in result]


def get_result(a, q):
    global n, k
    while n >= k:
        for i in range(k, 2 * k):
            a[i] = sum((-q[j] * a[i - j]) % MOD for j in range(1, k + 1)) % MOD
        r = polynomial_mul(q, [(q[i] if i % 2 == 0 else -q[i]) % MOD for i in range(len(q))])
        a = [a[i] % MOD for i in range(len(a)) if i % 2 == n % 2]
        a.extend(0 for _ in range(2 * k - len(a)))
        q = [r[i] for i in range(len(r)) if i % 2 == 0]
        n //= 2
    return a[n]


MOD = 104_857_601
k, n = map(int, input().split())
n -= 1
a = list(map(int, input().split()))
a.extend(0 for _ in range(2 * k - len(a)))
c = [1, *map(lambda x: MOD - int(x), input().split())]
print(get_result(a, c))
