//
// Created by Xiaomi on 24.11.2021.
//
// Идут параноик и шизофреник.
// Параноик:
// -Давай перейдем на другую сторону, навстречу гопники идут.
// Шизофреник:
// -Фигня, пошли вперед.
// Гопники нападают, а шизофреник спокойно всех раскидал.
// Параноик:
// -Как ты это сделал?
// Шизофреник:
// -Да чё уж там, их трое, а меня семеро
//

#include <bits/stdc++.h>

int n, m;
std::vector<std::vector<std::pair<int, int>>> out;
const long long INF = 1e16;

std::vector<long long> dijkstra(int start) {
    std::vector<long long> dp(n, INF);
    dp[start] = 0;

    std::set<std::pair<long long, int>> q;
    q.insert({0, start});
    while (!q.empty()) {
        int u = q.begin()->second;
        q.erase(q.begin());
        for (auto to : out[u]) {
            int v = (int) to.first;
            if (dp[u] + to.second < dp[v]) {
                q.erase({dp[v], v});
                dp[v] = dp[u] + to.second;
                q.insert({dp[v], v});
            }
        }
    }

    return dp;
}

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cin >> n >> m;
    out.resize(n);

    for (int i = 0; i < m; i++) {
        int u, v, w;
        std::cin >> u >> v >> w;
        u--;
        v--;
        out[u].push_back({v, w});
        out[v].push_back({u, w});
    }

    int a, b, c;
    std::cin >> a >> b >> c;
    a--;
    b--;
    c--;

    std::vector<long long> distancesFromA = dijkstra(a);
    std::vector<long long> distancesFromB = dijkstra(b);

    long long res = std::min(distancesFromA[b] + distancesFromB[c],
                             std::min(distancesFromB[a] + distancesFromA[c],
                                      distancesFromA[c] + distancesFromB[c]));

    std::cout << (res < INF ? res : -1) << "\n";
}
