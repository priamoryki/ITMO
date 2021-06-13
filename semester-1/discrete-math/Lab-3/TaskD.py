s = input().split()[0]

aplhabet = [i + 1 for i in range(26)]
for i in s:
    cur = ord(i) - 97
    print(aplhabet[cur], end=' ')
    for j in range(26):
        if (aplhabet[j] < aplhabet[cur]):
            aplhabet[j] += 1
    aplhabet[cur] = 1