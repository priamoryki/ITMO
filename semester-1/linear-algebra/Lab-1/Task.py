def sum(A, B):
    if (len(A) == len(B) and len(A[0]) == len(B[0])):
        res = [[0 for row in range(len(B[0]))] for col in range(len(B))]
        for i in range(len(B)):
            for j in range(len(B[0])):
                res[i][j] = A[i][j] + B[i][j]
    else:
        out.write('0')
        exit()
    return res

def subtraction(A, B):
    if (len(A) == len(B) and len(A[0]) == len(B[0])):
        res = [[0 for row in range(len(B[0]))] for col in range(len(B))]
        for i in range(len(B)):
            for j in range(len(B[0])):
                res[i][j] = A[i][j] - B[i][j]
    else:
        out.write('0')
        exit()
    return res

def transpone(A):
    res = [[0 for row in range(len(A))] for col in range(len(A[0]))]
    for i in range(len(A)):
        for j in range(len(A[0])):
            res[j][i] = A[i][j]
    return res

def multiply(A, B):
    if (type(A) == float):
        res = [[0 for row in range(len(B[0]))] for col in range(len(B))]
        for i in range(len(B)):
            for j in range(len(B[0])):
                res[i][j] = A * B[i][j]
    else:
        if (len(A[0]) == len(B)):
            res = [[0 for row in range(len(B[0]))] for col in range(len(A))]
            for i in range(len(A)):
                for j in range(len(B[0])):
                    for k in range(len(A[0])):
                        res[i][j] += A[i][k] * B[k][j]
        else:
            out.write('0')
            exit()
    return res

def read_Matrix():
    n, m = map(int, inp.readline().split())
    k, res = 0, []
    a = list(map(float, inp.readline().split()))
    for i in range(n):
        temp = []
        for j in range(m):
            temp.append(a[k])
            k += 1
        res.append(temp)
    return res

inp = open('input.txt', 'r')
out = open('output.txt', 'w')

alpha, beta = map(float, inp.readline().split())
A = read_Matrix()
B = read_Matrix()
C = read_Matrix()
D = read_Matrix()
F = read_Matrix()
inp.close()

X = subtraction(multiply(multiply(C, transpone(sum(multiply(alpha, A), multiply(beta, transpone(B))))), D), F)
out.write(str(1) + '\n')
out.write(str(len(X)) + ' ' + str(len(X[0])) + '\n')
for i in X:
    for j in i:
        out.write(str(j) + ' ')
    out.write('\n')
out.close()