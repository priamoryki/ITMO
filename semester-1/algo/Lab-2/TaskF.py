n = int(input())
a = list(map(int,input().split()))
x, res, stack = 1, [], []
 
for i in range(n):
    res.append('push')
    stack.append(a[i])
    while(len(stack) > 0 and stack[- 1] == x):
        res.append('pop')
        stack.pop()
        x += 1

if(len(stack) != 0):
    print('impossible')
else:
    for i in res:
        print(i)