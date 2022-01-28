#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>

bool isNumber(const std::string &s) {
    try {
        std::stoi(s);
    }
    catch (std::invalid_argument const &) {
        return false;
    }
    return true;
}

struct compareStringsIgnoreCase {
    bool operator()(const std::string &a, const std::string &b) {
        return std::lexicographical_compare(a.begin(), a.end(), b.begin(), b.end(),
                                            [](char c1, char c2) { return toupper(c1) < toupper(c2); });
    }
};

struct compareNumbers {
    bool operator()(const std::string &a, const std::string &b) {
        if (isNumber(a) && isNumber(b)) {
            return std::stoi(a) < std::stoi(b);
        } else if (isNumber(a)) {
            return std::stoi(a) < 0;
        } else if (isNumber(b)) {
            return std::stoi(b) >= 0;
        } else {
            return a < b;
        }
    }
};

std::vector<std::string> readStream(std::istream &stream) {
    std::vector<std::string> lines;
    std::string line;
    while (std::getline(stream, line)) {
        lines.emplace_back(line);
    }
    return lines;
}

std::vector<std::string> sort(const std::vector<std::string> &argv) {
    bool ignore_case = false;
    bool numeric_sort = false;
    std::string input_stream;
    for (const auto &flag : argv) {
        if (flag == "-f" || flag == "--ignore-case") {
            ignore_case = true;
        } else if (flag == "-n" || flag == "--numeric-sort") {
            numeric_sort = true;
        } else if (flag == "-nf") {
            numeric_sort = true;
            ignore_case = true;
        } else {
            input_stream = flag;
        }
    }

    std::ifstream stream(input_stream);
    std::vector<std::string> lines = readStream(stream);

    if (numeric_sort) {
        std::sort(lines.begin(), lines.end(), compareNumbers());
    } else if (ignore_case) {
        std::sort(lines.begin(), lines.end(), compareStringsIgnoreCase());
    } else {
        std::sort(lines.begin(), lines.end());
    }

    return lines;
}

std::vector<std::string> unique(const std::vector<std::string> &argv) {
    bool count_unique = false;
    std::string input_stream;
    for (const auto &flag : argv) {
        if (flag == "-c" || flag == "--count") {
            count_unique = true;
        } else {
            input_stream = flag;
        }
    }

    std::ifstream stream(input_stream);
    std::vector<std::string> lines = readStream(stream);

    std::vector<std::string> result;
    size_t counter = 0;
    for (auto it = lines.begin(), prev = it; prev != lines.end(); ++it) {
        if (it == lines.end() || *prev != *it) {
            std::string s = *prev;
            if (count_unique) {
                s += " " + std::to_string(counter);
            }
            result.push_back(std::move(s));
            prev = it;
            counter = 0;
        }
        ++counter;
    }
    return result;
}

int main() {
    std::cout << "SORT:\n";
    std::vector<std::string> lines = sort({"a.txt", "-nf"});
    for (auto &s : lines) {
        std::cout << s << '\n';
    }

    std::cout << "\nUNIQUE:\n";
    lines = unique({"b.txt", "-c"});
    for (auto &s : lines) {
        std::cout << s << '\n';
    }
    return 0;
}