#include <bits/stdc++.h>
using namespace std;
 
int main() {
    long long n;
    int x, y;
    cin >> n >> x >> y;
    if (x > y) {
        swap(x, y);
    }
    int l = 0, r = y*n;
    while (r - l > 1) {
        int t = (l + r)/2;
        if (t/x + t/y >= n - 1) {
            r = t;
        } else {
            l = t;
        }
    }
    if (n != 1) {
        cout << r + x << endl;
    } else {
        cout << x << endl;
    }
}