#include "Image.h"

Image::Image(std::vector<std::vector<Image::Pixel>> table)
    : m_table(std::move(table))
{
}

size_t Image::GetImageWidth() const
{
    return m_table.size();
}

size_t Image::GetImageHeight() const
{
    return m_table.at(0).size();
}

Image::Pixel::Pixel(int red, int green, int blue)
    : m_red(red)
    , m_green(green)
    , m_blue(blue)
{
}

Image::Pixel Image::GetPixel(size_t columnId, size_t rowId) const
{
    return m_table[columnId][rowId];
}

Image::Pixel Image::GetLeftPixel(size_t columnId, size_t rowId) const
{
    return columnId > 0 ? GetPixel(columnId - 1, rowId) : GetPixel(GetImageWidth() - 1, rowId);
}

Image::Pixel Image::GetRightPixel(size_t columnId, size_t rowId) const
{
    return columnId < GetImageWidth() - 1 ? GetPixel(columnId + 1, rowId) : GetPixel(0, rowId);
}

Image::Pixel Image::GetUpperPixel(size_t columnId, size_t rowId) const
{
    return rowId > 0 ? GetPixel(columnId, rowId - 1) : GetPixel(columnId, GetImageHeight() - 1);
}

Image::Pixel Image::GetLowerPixel(size_t columnId, size_t rowId) const
{
    return rowId < GetImageHeight() - 1 ? GetPixel(columnId, rowId + 1) : GetPixel(columnId, 0);
}