def f(i):
    return i & (i + 1)

def update(x, y, z, d):
    i = x
    while (i < n):
        j = y
        while (j < n):
            k = z
            while (k < n):
                a[i][j][k] += d
                k = k | (k + 1)
            j = j | (j + 1)
        i = i | (i + 1)

def my_sum(x, y, z):
    result = 0
    i = x
    while (i >= 0):
        j = y
        while (j >= 0):
            k = z
            while(k >= 0):
                result += a[i][j][k]
                k = f(k) - 1
            j = f(j) - 1
        i = f(i) - 1
    return result


n = int(input())
a = [[[0 for i in range(n)] for i in range(n)] for i in range(n)]

while (True):
    temp = list(map(int, input().split()))
    if (temp[0] == 1):
        update(temp[1], temp[2], temp[3], temp[4])
    elif (temp[0] == 2):
        x1, y1, z1, x2, y2, z2 = temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]
        print(my_sum(x2, y2, z2) - my_sum(x2, y1 - 1, z2) - my_sum(x1 - 1, y2, z2) - my_sum(x2, y2, z1 - 1) + my_sum(x2, y1 - 1, z1 - 1) + my_sum(x1 - 1, y2, z1 - 1) + my_sum(x1 - 1, y1 - 1, z2) - my_sum(x1 - 1, y1 - 1, z1 - 1))
    elif (temp[0] == 3):
        exit()