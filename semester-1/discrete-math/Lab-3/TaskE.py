s = input().split()[0]

cur, dictionary = '', [chr(i + 97) for i in range(26)]
for i in s:
    if ((cur + i) in dictionary):
        cur += i
    else:
        print(dictionary.index(cur), end=' ')
        dictionary.append(cur + i)
        cur = i

print(dictionary.index(cur))