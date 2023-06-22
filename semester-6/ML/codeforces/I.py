k1, k2 = map(int, input().split())
n = int(input())
X, X1, X2 = {}, {}, {}
for _ in range(n):
    x1, x2 = map(int, input().split())
    pair = (x1, x2)
    X[pair] = X.get(pair, 0) + 1
    X1[x1] = X1.get(x1, 0) + 1
    X2[x2] = X2.get(x2, 0) + 1

print(sum(X[(x1, x2)] ** 2 / (X1[x1] * X2[x2] / n) - 2 * X[(x1, x2)] for (x1, x2) in X) + n)
