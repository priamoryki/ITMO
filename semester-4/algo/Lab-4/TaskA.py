import math

n = int(input())
d, result = 2, []

while n != 1:
    if n % d == 0:
        n //= d
        result.append(d)
    else:
        d += 1
    if d > math.sqrt(n) + 1:
        result.append(n)
        break

print(" ".join(map(str, result)))
