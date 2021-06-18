#include <algorithm>
#include <cstring>
#include <fstream>
#include <iostream>
#include <vector>

bool isNumber(const std::string & s)
{
    try {
        std::stoi(s);
    }
    catch (std::invalid_argument const &) {
        return false;
    }
    return true;
}

struct compareStringsIgnoreCase
{
    bool operator()(const std::string & a, const std::string & b)
    {
        return std::lexicographical_compare(a.begin(), a.end(), b.begin(), b.end(), [](char c1, char c2) { return toupper(c1) < toupper(c2); });
    }
};

struct compareNumbers
{
    bool operator()(const std::string & a, const std::string & b)
    {
        if (isNumber(a) && isNumber(b)) {
            return std::stoi(a) < std::stoi(b);
        }
        else if (isNumber(a)) {
            return std::stoi(a) < 0;
        }
        else if (isNumber(b)) {
            return std::stoi(b) >= 0;
        }
        else {
            return a < b;
        }
    }
};

int main(int argc, char ** argv)
{
    bool ignore_case = false;
    bool numeric_sort = false;
    char * input_stream;
    for (int i = 1; i < argc; i++) {
        if (std::strcmp(argv[i], "-f") == 0 || std::strcmp(argv[i], "--ignore-case") == 0) {
            ignore_case = true;
        }
        else if (std::strcmp(argv[i], "-n") == 0 || std::strcmp(argv[i], "--numeric-sort") == 0) {
            numeric_sort = true;
        }
        else if (std::strcmp(argv[i], "-nf") == 0) {
            numeric_sort = true;
            ignore_case = true;
        }
        else {
            input_stream = argv[i];
        }
    }

    std::ifstream stream(input_stream);
    std::vector<std::string> lines;
    std::string line;
    while (std::getline(stream, line)) {
        lines.emplace_back(line);
    }

    if (numeric_sort) {
        std::sort(lines.begin(), lines.end(), compareNumbers());
    }
    else if (ignore_case) {
        std::sort(lines.begin(), lines.end(), compareStringsIgnoreCase());
    }
    else {
        std::sort(lines.begin(), lines.end());
    }

    for (auto & s : lines) {
        std::cout << s << '\n';
    }
    return 0;
}