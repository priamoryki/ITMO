#include "searcher.h"

#include <iostream>
#include <sstream>
#include <string>
#include <vector>

std::vector<size_t> quotes_indexes(const std::string & text)
{
    std::vector<size_t> result;
    for (size_t i = 0; i < text.size(); i++) {
        if (text[i] == '"') {
            result.push_back(i);
        }
    }
    return result;
}

std::string make_word(std::string word)
{
    while (!word.empty() && std::ispunct(word[0])) {
        word.erase(0, 1);
    }
    while (!word.empty() && std::ispunct(word[word.size() - 1])) {
        word.erase(word.size() - 1);
    }
    return word;
}

std::vector<std::string> split_into_words(std::istream & text, bool is_query)
{
    std::vector<std::string> result;
    std::string temp;
    char i;
    while (text.get(i)) {
        i = std::tolower(static_cast<unsigned char>(i));
        if (std::isspace(static_cast<unsigned char>(i)) || (is_query && i == '"')) {
            std::string word = make_word(temp);
            if (!word.empty()) {
                result.push_back(make_word(temp));
                temp = "";
            }
        }
        else {
            temp.push_back(i);
        }
    }
    std::string word = make_word(temp);
    if (!word.empty()) {
        result.push_back(word);
    }
    return result;
}

void Searcher::add_document(const Filename & filename, std::istream & strm)
{
    m_files.insert(filename);
    std::vector<std::string> words = split_into_words(strm, false);
    for (size_t i = 0; i < words.size(); i++) {
        if (m_words[words[i]].count(filename)) {
            m_words[words[i]][filename].insert(i);
        }
        else {
            m_words[words[i]][filename] = {i};
        }
    }
}

void Searcher::remove_document(const Filename & filename)
{
    m_files.erase(filename);
}

std::pair<Searcher::DocIterator, Searcher::DocIterator> Searcher::search(const std::string & query) const
{
    std::set<Filename> result = m_files;
    std::vector<size_t> indexes = quotes_indexes(query);
    if (indexes.size() % 2 != 0) {
        throw BadQuery();
    }
    auto s = std::stringstream(query);
    std::vector<std::string> words = split_into_words(s, true);
    if (words.empty()) {
        throw BadQuery();
    }
    for (const Filename & filename : m_files) {
        bool contains_all_words = true;
        for (const Filename & word : words) {
            if (!(m_words.count(word) && m_words.at(word).count(filename))) {
                contains_all_words = false;
                break;
            }
        }
        if (!contains_all_words) {
            result.erase(filename);
        }
    }

    std::set<Filename> temp = result;
    for (size_t i = 0; i < indexes.size(); i += 2) {
        s = std::stringstream(query.substr(indexes[i], indexes[i + 1] - indexes[i]));
        words = split_into_words(s, true);
        if (words.empty()) {
            throw BadQuery();
        }
        for (const Filename & filename : temp) {
            std::vector<size_t> arr;
            if (m_words.at(words[0]).count(filename)) {
                for (const size_t & pos : m_words.at(words[0]).at(filename)) {
                    arr.push_back(pos);
                }
            }
            for (size_t j = 1; j < words.size(); j++) {
                std::vector<size_t> new_arr;
                for (size_t & el : arr) {
                    if (m_words.at(words[j]).at(filename).count(el + 1) != 0u) {
                        new_arr.push_back(++el);
                    }
                }
                arr = new_arr;
            }
            if (arr.empty()) {
                result.erase(filename);
            }
        }
    }

    DocIterator it_begin(result);
    DocIterator it_end(result, result.size());
    return std::make_pair(it_begin, it_end);
}