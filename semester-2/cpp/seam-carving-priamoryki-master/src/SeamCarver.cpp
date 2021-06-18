#include "SeamCarver.h"

#include <algorithm>
#include <cmath>

SeamCarver::SeamCarver(Image image)
    : m_image(std::move(image))
{
}

const Image & SeamCarver::GetImage() const
{
    return m_image;
}

size_t SeamCarver::GetImageWidth() const
{
    return m_image.GetImageWidth();
}

size_t SeamCarver::GetImageHeight() const
{
    return m_image.GetImageHeight();
}

double SeamCarver::GetPixelEnergy(size_t columnId, size_t rowId) const
{
    Image::Pixel LeftPixel = m_image.GetLeftPixel(columnId, rowId);
    Image::Pixel RightPixel = m_image.GetRightPixel(columnId, rowId);
    Image::Pixel UpperPixel = m_image.GetUpperPixel(columnId, rowId);
    Image::Pixel LowerPixel = m_image.GetLowerPixel(columnId, rowId);

    double Rx = RightPixel.m_red - LeftPixel.m_red;
    double Gx = RightPixel.m_green - LeftPixel.m_green;
    double Bx = RightPixel.m_blue - LeftPixel.m_blue;
    double Ry = UpperPixel.m_red - LowerPixel.m_red;
    double Gy = UpperPixel.m_green - LowerPixel.m_green;
    double By = UpperPixel.m_blue - LowerPixel.m_blue;

    double DeltaX = Rx * Rx + Gx * Gx + Bx * Bx;
    double DeltaY = Ry * Ry + Gy * Gy + By * By;

    return sqrt(DeltaX + DeltaY);
}

std::vector<std::vector<double>> SeamCarver::GetImageEnergy() const
{
    std::vector<std::vector<double>> result(GetImageWidth(), std::vector<double>(GetImageHeight()));
    for (size_t columnId = 0; columnId < GetImageWidth(); columnId++) {
        for (size_t rowId = 0; rowId < GetImageHeight(); rowId++) {
            result[columnId][rowId] = GetPixelEnergy(columnId, rowId);
        }
    }
    return result;
}

std::vector<std::vector<double>> SeamCarver::Transpose(std::vector<std::vector<double>> matrix) const
{
    std::vector<std::vector<double>> result(matrix.at(0).size(), std::vector<double>(matrix.size()));
    for (size_t rowId = 0; rowId < matrix.at(0).size(); rowId++) {
        for (size_t columnId = 0; columnId < matrix.size(); columnId++) {
            result[rowId][columnId] = matrix[columnId][rowId];
        }
    }
    return result;
}

SeamCarver::Seam SeamCarver::FindSeam(std::vector<std::vector<double>> dp) const
{
    std::vector<std::vector<size_t>> way(dp.size(), std::vector<size_t>(dp.at(0).size()));

    for (size_t columnId = 0; columnId < dp.size(); columnId++) {
        for (size_t rowId = 0; rowId < dp.at(0).size(); rowId++) {
            if (columnId > 0) {
                double minimal = std::numeric_limits<double>::max();
                for (int shift = -1; shift <= 1; shift++) {
                    if ((shift >= 0 || static_cast<size_t>(-shift) <= rowId) && (rowId + shift < dp.at(0).size()) && (minimal > dp[columnId - 1][rowId + shift])) {
                        minimal = dp[columnId - 1][rowId + shift];
                        way[columnId][rowId] = rowId + shift;
                    }
                }
                dp[columnId][rowId] += minimal;
            }
        }
    }

    size_t id = std::distance(dp[dp.size() - 1].begin(),
                              std::min_element(dp[dp.size() - 1].begin(), dp[dp.size() - 1].end()));

    Seam result;
    result.reserve(dp.size());
    for (size_t columnId = dp.size() - 1; columnId > 0; columnId--) {
        result.push_back(id);
        id = way[columnId][id];
    }
    result.push_back(id);
    std::reverse(result.begin(), result.end());
    return result;
}

SeamCarver::Seam SeamCarver::FindHorizontalSeam() const
{
    return FindSeam(GetImageEnergy());
}

SeamCarver::Seam SeamCarver::FindVerticalSeam() const
{
    return FindSeam(Transpose(GetImageEnergy()));
}

void SeamCarver::RemoveHorizontalSeam(const Seam & seam)
{
    for (size_t columnId = 0; columnId < GetImageWidth(); columnId++) {
        m_image.m_table[columnId].erase(m_image.m_table[columnId].begin() + seam[columnId]);
    }
}

void SeamCarver::RemoveVerticalSeam(const Seam & seam)
{
    for (size_t rowId = 0; rowId < GetImageHeight(); rowId++) {
        // Moving m_table[seam[RowId]][RowId] to the end of the row
        for (size_t columnId = seam[rowId]; columnId < GetImageWidth() - 1; columnId++) {
            std::swap(m_image.m_table[columnId][rowId], m_image.m_table[columnId + 1][rowId]);
        }
    }
    // Deleting useless element in column
    m_image.m_table.pop_back();
}