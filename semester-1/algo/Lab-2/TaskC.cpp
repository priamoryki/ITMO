#include <bits/stdc++.h>
using namespace std;
 
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    vector<int> a;
    int n, st = 0;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int x;
        cin >> x;
        if (x == 1) {
            int id;
            cin >> id;
            a.push_back(id);
        }
        if (x == 2) {
            st++;
        }
        if (x == 3) {
            a.pop_back();
        }
        if (x == 4) {
            int c, res = 0, j = st;
            cin >> c;
            while(a[j++] != c) {
                res++;
            }
            cout << res << '\n';
        }
        if (x == 5) {
            cout << a[st] << '\n';
        }
    }
}