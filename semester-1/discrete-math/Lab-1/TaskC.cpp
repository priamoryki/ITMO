#include <bits/stdc++.h>
using namespace std;

int rec(vector<vector<int>> a, int x, int k) {
  if (a[x].size() != 1) {
      int m = 0;
      for (int i = 1; i < a[x][0]; i++) {
          m = max(m, rec(a, i, k));
      }
      return m + k;
  } else {
      return 1;
  }
}

int main() {
    map<int, map<int, int>> f;
    int n, k = 0;
    cin >> n;
    vector<vector<int>> a(n);
    for (int i = 0; i < n; i++) {
        int m;
        cin >> m;
        vector<int> x(m + 1);
        x[0] = m;
        if (m == 0) {
            k++;
        } else {
            for (int j = 0; j < m; j++) {
                cin >> x[j + 1];
            }
            map<int, int> w;
            for (int j = 0; j < pow(2, m); j++) {
                int q;
                cin >> q;
                w[j] = q;
            }
            f[i] = w;
        }
        a[i] = x;
    }
    cout << rec(a, a.size() - 1, 0);
}