#include <bits/stdc++.h>
using namespace std;

bool good(vector<long long> a, long long x, long long k) {
    long long t = 0, q = 1, n = a.size();
    for (long long i = 0; i < n; i++) {
        if (t + a[i] <= x) {
            t += a[i];
        } else {
            t = a[i];
            if ((++q > k) || (t > x)) {
                return false;
            }
        }
    }
    return true;
}
 
int main() {
    long long n, k, l = 0, r = 0;
    cin >> n >> k;
    vector<long long> a(n);
    for (long long i = 0; i < n; i++) {
        cin >> a[i];
        r += a[i];
    }

    while (r - l > 1) {
        long long m = (l + r)/2;
        if (good(a, m, k)) {
            r = m;
        } else {
            l = m;
        }
    }

    cout << r;
}