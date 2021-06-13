import math

def read_vector(z):
    return [*map(float, inp.readline().split()), z]

def length(a):
    return math.sqrt(sum(a[i] ** 2 for i in range(len(a))))

def scalar_product(a, b):
    return sum([a[i] * b[i] for i in range(len(a))])

def vector_product(a, b):
    return [a[(i + 1) % 3] * b[(i + 2) % 3] - a[(i + 2) % 3] * b[(i + 1) % 3] for i in range(3)]

def find_angle(a, b):
    return math.acos(scalar_product(a, b) / (length(a) * length(b))) * 180 / math.pi

with open('input.txt', 'r') as inp:
    v = read_vector(0) # положение корабля
    a = read_vector(0) # ориентация киля судна
    m = read_vector(1) # направление мачты
    w = read_vector(0) # положение вражеского корабля

basic, enemy = [0, 0, 1], [w[i] - v[i] for i in range(len(a))]

angle = find_angle(basic, m)
l_angle = find_angle(vector_product(basic, a), enemy)
r_angle = find_angle(vector_product(a, basic), enemy)

with open('output.txt', 'w') as out:
    if ((l_angle > 60 and r_angle > 60) or angle > 60):
        out.write('0\nGG')
    else:
        if (l_angle <= 60):
            if (find_angle(a, enemy) > 90):
                l_angle *= -1
            if (find_angle(m, vector_product(a, basic)) > 90):
                angle *= -1
            out.write('1\n' + str(l_angle))
        else:
            if (find_angle(a, enemy) > 90):
                r_angle *= -1
            if (find_angle(m, vector_product(a, basic)) < 90):
                angle *= -1
            out.write('-1\n' + str(r_angle))
        out.write('\n' + str(angle) + '\nGG')