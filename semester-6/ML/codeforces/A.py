def mk_or(i):
    r = [-1.0 if (i >> j) & 1 == 1 else 1.0 for j in range(m)]
    r.append(bin(i)[2:].count("1") - 0.5)
    return r


def mk_and(i):
    r = [1.0 if (i >> j) & 1 == 1 else -1.0 for j in range(m)]
    r.append(-(bin(i)[2:].count("1") - 0.5))
    return r


def default_print(rw):
    print(2)
    print(f"{len(rw)} 1")
    print("\n".join(" ".join([*map(str, i)]) for i in rw))
    print(" ".join(["1.0"] * len(rw)), end=" ")


def mk_dnf():
    rw = [mk_and(i) for i in range(n) if fun[i] == 1]
    if len(rw) != 0:
        default_print(rw)
    else:
        print(1)
        print(1)
        print(" ".join(["1.0"] * m), end=" ")
    print("-0.5")


def mk_cnf():
    rw = [mk_or(i) for i in range(n) if fun[i] == 0]
    default_print(rw)
    print(-len(rw) + 0.5)


m = int(input())
n = 1 << m
fun = [int(input()) for _ in range(n)]

if sum(fun) > 512:
    mk_cnf()
else:
    mk_dnf()
