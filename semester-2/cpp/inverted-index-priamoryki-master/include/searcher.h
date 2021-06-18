#pragma once

#include <map>
#include <set>
#include <string>
#include <vector>

class Searcher
{
    std::set<std::string> m_files;
    std::map<std::string, std::map<std::string, std::set<size_t>>> m_words;

public:
    using Filename = std::string; // or std::filesystem::path

    // index modification
    void add_document(const Filename & filename, std::istream & strm);

    void remove_document(const Filename & filename);

    // queries
    class DocIterator
    {
        std::vector<Filename> m_files;
        size_t m_id = 0;

    public:
        using value_type = const Filename;
        using difference_type = std::ptrdiff_t;
        using iterator_category = std::forward_iterator_tag;
        using reference = Filename &;
        using pointer = Filename *;

        DocIterator(const std::set<Filename> & a, size_t pos = 0)
            : m_files(a.begin(), a.end())
            , m_id(pos)
        {
        }

        Filename operator*() const
        {
            return m_files[m_id];
        }

        DocIterator & operator++()
        {
            m_id++;
            return *this;
        }

        DocIterator operator++(int)
        {
            auto temp(*this);
            operator++();
            return temp;
        }

        bool operator==(const DocIterator & other) const
        {
            return this->m_id == other.m_id && this->m_files == other.m_files;
        }

        bool operator!=(const DocIterator & other) const
        {
            return this->m_id != other.m_id || this->m_files != other.m_files;
        }
    };

    class BadQuery : public std::exception
    {
        const char * what() const throw() override
        {
            return "Search query syntax error:";
        }
    };

    std::pair<DocIterator, DocIterator> search(const std::string & query) const;
};