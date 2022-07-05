MOD = 1_000_000_007
k, m = map(int, input().split())
c = list(map(int, input().split()))
result, a = [0 if i != 0 else 1 for i in range(m + 1)], [0 if i != 0 else 1 for i in range(m + 1)]

for i in range(1, len(result)):
    result[i] = sum(a[i - j] for j in c if i >= j) % MOD
    a[i] = sum(result[j] * result[i - j] for j in range(i + 1)) % MOD

print(" ".join(map(str, result[1: m + 1])))
