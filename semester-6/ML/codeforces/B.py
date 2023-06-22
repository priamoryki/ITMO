import math
import operator
import itertools
from functools import reduce


class Matrix:
    def __init__(self, a, b, init_val):
        self.a = a
        self.b = b
        self.matrix = [[init_val for _ in range(b)] for _ in range(a)]

    def __getitem__(self, item):
        i, j = item
        return self.matrix[i][j]

    def set(self, i, j, val):
        self.matrix[i][j] = val

    def mul(self, i, j, factor):
        self.matrix[i][j] *= factor

    def add(self, i, j, delta):
        self.matrix[i][j] += delta

    def fill(self):
        for i in range(self.a):
            line = [*map(int, input().split())]
            for j in range(self.b):
                self.set(i, j, line[j])

    def __str__(self):
        return "\n".join([" ".join([*map(str, i)]) for i in self.matrix])


class Module:
    def __init__(self, a, b, init_val):
        self.values = Matrix(a, b, init_val)
        self.difs = Matrix(a, b, 0)

    def vals(self):
        pass

    def diff(self):
        pass


class Container(Module):
    def __init__(self, a, b, init_val, stored_value):
        super().__init__(a, b, init_val)
        self.stored_value = stored_value


class TanH(Container):
    def __init__(self, arg):
        super().__init__(arg.values.a, arg.values.b, 0, arg)

    def vals(self):
        for i, j in itertools.product(range(self.values.a), range(self.values.b)):
            self.values.set(i, j, math.tanh(self.stored_value.values[i, j]))

    def diff(self):
        for i, j in itertools.product(range(self.stored_value.difs.a), range(self.stored_value.difs.b)):
            self.stored_value.difs.add(i, j, (1 - self.values[i, j] ** 2) * self.difs[i, j])


class ReLU(Container):
    def __init__(self, arg, alpha):
        super().__init__(arg.values.a, arg.values.b, 0, arg)
        self.alpha = alpha

    def vals(self):
        for i, j in itertools.product(range(self.values.a), range(self.values.b)):
            v = self.stored_value.values[i, j]
            if v < 0:
                v /= self.alpha
            self.values.set(i, j, v)

    def diff(self):
        for i, j in itertools.product(range(self.stored_value.difs.a), range(self.stored_value.difs.b)):
            v = self.difs[i, j]
            if self.stored_value.values[i, j] < 0:
                v /= self.alpha
            self.stored_value.difs.add(i, j, v)


class Pair:
    def __init__(self, left, right):
        self.left = left
        self.right = right


class Mul(Container):
    def __init__(self, pair):
        super().__init__(pair.left.values.a, pair.right.values.b, 0, pair)

    def vals(self):
        for i, j, k in itertools.product(range(self.stored_value.left.values.a), range(self.stored_value.left.values.b),
                                         range(self.stored_value.right.values.b)):
            self.values.add(i, k, self.stored_value.left.values[i, j] * self.stored_value.right.values[j, k])

    def iterate_diff(self, m1, m2, max_i, max_j, max_k, rev):
        for i, j, k in itertools.product(range(max_i), range(max_j), range(max_k)):
            v = self.difs[k, j] * m2.values[k, i] if rev else self.difs[i, k] * m2.values[j, k]
            m1.difs.add(i, j, v)

    def diff(self):
        self.iterate_diff(self.stored_value.left, self.stored_value.right, self.stored_value.left.difs.a,
                          self.stored_value.left.difs.b, self.stored_value.right.values.b, False)
        self.iterate_diff(self.stored_value.right, self.stored_value.left, self.stored_value.left.difs.b,
                          self.stored_value.right.values.b, self.stored_value.left.difs.a, True)


class Sum(Container):
    def __init__(self, arg):
        super().__init__(arg[0].values.a, arg[0].values.b, 0, arg)

    def update(self, to, _from):
        for i, j in itertools.product(range(to.a), range(to.b)):
            to.add(i, j, _from[i, j])

    def vals(self):
        for a in self.stored_value:
            self.update(self.values, a.values)

    def diff(self):
        for a in self.stored_value:
            self.update(a.difs, self.difs)


class MultyMul(Container):
    def __init__(self, args):
        super().__init__(args[0].values.a, args[0].values.b, 1, args)

    def vals(self):
        for a in self.stored_value:
            for i, j in itertools.product(range(a.values.a), range(a.values.b)):
                self.values.mul(i, j, a.values[i, j])

    def diff(self):
        for m1 in range(len(self.stored_value)):
            for i, j in itertools.product(range(self.stored_value[m1].difs.a), range(self.stored_value[m1].difs.b)):
                v = reduce(operator.mul,
                           [self.stored_value[m2].values[i, j] for m2 in range(len(self.stored_value)) if
                            m1 != m2], 1) * self.difs[i, j]
                self.stored_value[m1].difs.add(i, j, v)


n, m, k = map(int, input().split())
nodes = []
for i in range(n):
    line = input().split()
    if line[0] == "var":
        nodes.append(Module(int(line[1]), int(line[2]), 0))
    elif line[0] == "tnh":
        nodes.append(TanH(nodes[int(line[1]) - 1]))
    elif line[0] == "rlu":
        nodes.append(ReLU(nodes[int(line[2]) - 1], int(line[1])))
    elif line[0] == "mul":
        nodes.append(Mul(Pair(nodes[int(line[1]) - 1], nodes[int(line[2]) - 1])))
    elif line[0] == "sum":
        nodes.append(Sum([nodes[j - 1] for j in map(int, line[2:])]))
    elif line[0] == "had":
        nodes.append(MultyMul([nodes[j - 1] for j in map(int, line[2:])]))
for v in range(m):
    nodes[v].values.fill()
for i in range(k):
    nodes[n - k + i].difs.fill()

for node in nodes:
    node.vals()
for node in reversed(nodes):
    node.diff()

for i in range(k):
    print(nodes[n + i - k].values)
for i in range(m):
    print(nodes[i].difs)
