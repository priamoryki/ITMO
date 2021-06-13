def reflexivity(rat):
    for i in range(len(rat)):
        for j in range(len(rat)):
            if (rat[i][j] and not (rat[i][i] and rat[j][j])):
                return 0
    return 1

def antireflexivity(rat):
    for i in range(len(rat)):
        for j in range(len(rat)):
            if (rat[i][j] and (rat[i][i] and rat[j][j])):
                return 0
    return 1

def symmetry(rat):
    for i in range(len(rat)):
        for j in range(len(rat)):
            if (rat[i][j] and not rat[j][i]):
                return 0
    return 1

def antisymmetry(rat):
    for i in range(len(rat)):
        for j in range(len(rat)):
            if (i != j and rat[i][j] and rat[j][i]):
                return 0
    return 1

def transitivity(rat):
    for i in range(len(rat)):
        for j in range(len(rat)):
            for k in range(len(rat)):
                if (rat[i][j] and rat[j][k] and not rat[i][k]):
                    return 0
    return 1


rat1, rat2 = [], []

n = int(input())
for i in range(n):
    rat1.append(list(map(int, input().split())))
for i in range(n):
    rat2.append(list(map(int, input().split())))

print(reflexivity(rat1), antireflexivity(rat1), symmetry(rat1), antisymmetry(rat1), transitivity(rat1))
print(reflexivity(rat2), antireflexivity(rat2), symmetry(rat2), antisymmetry(rat2), transitivity(rat2))

rat3 = [[0 for i in range(n)] for j in range(n)]
for i in range(n):
    for j in range(n):
        for k in range(n):
            if (rat1[i][j] and rat2[j][k]):
                rat3[i][k] = 1

for i in range(n):
    for j in range(n):
        print(rat3[i][j], end = ' ')
    print()