k = int(input())
a = list(map(int, input().split()))
c = list(map(int, input().split()))
q = [-c[i - 1] if i != 0 else 1 for i in range(k + 1)]
p = [a[i] + sum(a[i - j] * q[j] for j in range(1, i + 1)) for i in range(k)]
d = next(i for i in range(k - 1, -1, -1) if p[i])

print(d)
print(" ".join(map(str, p[:d + 1])))
print(k)
print(" ".join(map(str, q[:k + 1])))
