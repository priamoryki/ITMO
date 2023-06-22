import math

n, k = map(int, input().split())
y = [*map(int, input().split())]
x = [*map(int, input().split())]

count_k = [0 for _ in range(k)]
for i in y:
    count_k[i - 1] += 1

sum_x = sum(x)
sqrt_x = math.sqrt(max(n * sum(i ** 2 for i in x) - sum_x ** 2, 0))
sqrt_y = [math.sqrt(max(count_k[i] * (n - count_k[i]), 0)) for i in range(k)]
corr = [-sum_x * count_k[i] for i in range(k)]

for i in range(n):
    corr[y[i] - 1] += n * x[i]

for i in range(k):
    if sqrt_x * sqrt_y[i] == 0:
        corr[i] = 0
        continue
    corr[i] /= sqrt_x * sqrt_y[i]

print(round(sum(corr[i] * count_k[i] for i in range(k)) / n, 34))
