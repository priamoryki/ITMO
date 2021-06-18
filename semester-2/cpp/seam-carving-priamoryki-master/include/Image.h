#pragma once
#include <iostream>
#include <vector>

struct Image
{
    struct Pixel
    {
        Pixel(int red, int green, int blue);
        int m_red;
        int m_green;
        int m_blue;
    };

    Image(std::vector<std::vector<Pixel>> table);

    size_t GetImageWidth() const;
    size_t GetImageHeight() const;

    Pixel GetPixel(size_t columnId, size_t rowId) const;
    Pixel GetLeftPixel(size_t columnId, size_t rowId) const;
    Pixel GetRightPixel(size_t columnId, size_t rowId) const;
    Pixel GetUpperPixel(size_t columnId, size_t rowId) const;
    Pixel GetLowerPixel(size_t columnId, size_t rowId) const;

    std::vector<std::vector<Pixel>> m_table;
};