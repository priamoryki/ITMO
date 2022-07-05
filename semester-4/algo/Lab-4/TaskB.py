import random


def is_prime(n: int) -> bool:
    if n == 1 or n % 2 == 0:
        return n == 2
    if n == 3:
        return True
    r, s = 0, n - 1
    while s % 2 == 0:
        r += 1
        s //= 2
    for _ in range(10):
        a = random.randrange(2, n - 1)
        x = pow(a, s, n)
        if x == 1 or x == n - 1:
            continue
        for _ in range(r - 1):
            x = pow(x, 2, n)
            if x == 1:
                return False
            if x == n - 1:
                break
        if x != n - 1:
            return False
    return True


for _ in range(int(input())):
    print("YES" if is_prime(int(input())) else "NO")
