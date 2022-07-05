import math


def resize_complex(a, n):
    a += [complex() for _ in range(n - len(a))]


def fft(a, invert):
    n = len(a)
    if n == 1:
        return
    a0, a1 = a[::2], a[1::2]
    fft(a0, invert)
    fft(a1, invert)
    theta = 2 * math.pi / n * (-1 if invert else 1)
    w, wn = complex(1), complex(math.cos(theta), math.sin(theta))
    for i in range(n // 2):
        a[i] = a0[i] + w * a1[i]
        a[i + n // 2] = a0[i] - w * a1[i]
        if invert:
            a[i] /= 2
            a[i + n // 2] /= 2
        w *= wn


def multiply(a, b):
    fa, fb = [complex(i) for i in a], [complex(i) for i in b]
    n = 1
    while n < max(len(a), len(b)):
        n *= 2
    n *= 2
    resize_complex(fa, n)
    resize_complex(fb, n)
    fft(fa, False)
    fft(fb, False)
    for i in range(n):
        fa[i] *= fb[i]
    fft(fa, True)
    return [int(fa[i].real + 0.5) for i in range(n)]


n = int(input())
a, b = list(map(int, input().split())), list(map(int, input().split()))
res = multiply(a, b)
while res[-1] == 0:
    res.pop()
print(" ".join(map(str, res)))
