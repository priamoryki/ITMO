import math
import operator
import fileinput
import itertools
from functools import reduce
from copy import deepcopy


class Reader:
    def __init__(self):
        self.inp, self.counter = [], 0
        for line in fileinput.input():
            self.inp.extend([*map(int, line.split())])

    def next(self):
        value = self.inp[self.counter]
        self.counter += 1
        return value


class Matrix:
    def __init__(self, a, b, init_val):
        self.a = a
        self.b = b
        self.matrix = [[init_val for _ in range(b)] for _ in range(a)]

    def __getitem__(self, item) -> float:
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
            for j in range(self.b):
                self.set(i, j, reader.next())

    def transpose(self):
        self.a, self.b, self.matrix = self.b, self.a, [[self.matrix[i][j] for i in range(self.a)] for j in
                                                       range(self.b)]

    def __str__(self):
        return "\n".join([" ".join([*map(str, i)]) for i in self.matrix])


class Module:
    def __init__(self, a, b, init_val=0):
        self.values = Matrix(a, b, init_val)
        self.difs = Matrix(a, b, 0)

    def vals(self):
        pass

    def diff(self):
        pass

    def transpose(self):
        self.values.transpose()
        self.difs.transpose()


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


class Sigma(Container):
    def __init__(self, arg):
        super().__init__(arg.values.a, arg.values.b, 0, arg)
        self.values = deepcopy(arg.values)
        self.difs = deepcopy(arg.difs)

    def vals(self):
        for i, j in itertools.product(range(self.values.a), range(self.values.b)):
            self.values.set(i, j, 1 / (1 + math.exp(-self.values[i, j])))

    def diff(self):
        for i, j in itertools.product(range(self.values.a), range(self.values.b)):
            self.stored_value.difs.add(i, j, self.values[i, j] * self.difs[i, j] * (1 - self.values[i, j]))


def read_w_u_b(n):
    w = Module(n, n)
    w.values.fill()
    u = Module(n, n)
    u.values.fill()
    b = Module(n, 1)
    b.values.fill()
    return w, u, b


reader = Reader()

n = reader.next()
wf, uf, bf = read_w_u_b(n)
wi, ui, bi = read_w_u_b(n)
wo, uo, bo = read_w_u_b(n)
wc, uc, bc = read_w_u_b(n)
m = reader.next()

hts = [Module(n, 1)]
hts[0].values.fill()
ccts = [Module(n, 1)]
ccts[0].values.fill()
xts = [Module(1, 1)]
for i in range(1, m + 1):
    xts.append(Module(n, 1))
    xts[i].values.fill()

fts = [Module(1, 1)]
its = [Module(1, 1)]
ots = [Module(1, 1)]
cts = [Module(1, 1)]
diff_transforms = []

for i in range(1, m + 1):
    def calc(w, u, b, ts, is_sigma=True):
        oper1 = Mul(Pair(w, xts[i]))
        oper1.vals()
        oper2 = Mul(Pair(u, hts[i - 1]))
        oper2.vals()
        oper3 = Sum([oper1, oper2, b])
        oper3.vals()
        ts.append(Sigma(oper3) if is_sigma else TanH(oper3))
        ts[i].vals()
        diff_transforms.append(oper1)
        diff_transforms.append(oper2)
        diff_transforms.append(oper3)
        diff_transforms.append(ts[i])


    calc(wf, uf, bf, fts)
    calc(wi, ui, bi, its)
    calc(wo, uo, bo, ots)
    calc(wc, uc, bc, cts, False)

    oper1 = MultyMul([fts[i], ccts[i - 1]])
    oper1.vals()
    oper2 = MultyMul([its[i], cts[i]])
    oper2.vals()
    ccts.append(Sum([oper1, oper2]))
    ccts[i].vals()
    hts.append(MultyMul([ots[i], ccts[i]]))
    hts[i].vals()
    diff_transforms.append(oper1)
    diff_transforms.append(oper2)
    diff_transforms.append(ccts[i])
    diff_transforms.append(hts[i])

for i in range(1, m + 1):
    print(ots[i].values)

print(hts[-1].values)
print(ccts[-1].values)

hts[-1].difs.fill()
ccts[-1].difs.fill()
for o in reversed(ots[1:]):
    o.difs.fill()

for t in reversed(diff_transforms):
    t.diff()

print(*map(lambda x: x.difs, reversed(xts[1:])), sep="\n")
print(hts[0].difs, ccts[0].difs, wf.difs, uf.difs, bf.difs, wi.difs, ui.difs, bi.difs, wo.difs, uo.difs, bo.difs,
      wc.difs, uc.difs, bc.difs, sep="\n")
