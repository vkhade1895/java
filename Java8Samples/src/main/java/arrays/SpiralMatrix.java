package arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Print a matrix in spiral order: right along the top, down the right side,
 * left along the bottom, up the left side, then repeat on the inner part.
 *
 * <p>Keywords: 2D array, matrix traversal, four boundaries.
 *
 * <p>First written: 2026-04-07 (old location: {@code march.SpiralMatrix}).
 */
public class SpiralMatrix {

    static List<Integer> spirallyTraverse(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        List<Integer> res = new ArrayList<>();

        // Borders of the part that hasn't been printed yet
        int top = 0;
        int bottom = m - 1;
        int left = 0;
        int right = n - 1;

        while (top <= bottom && left <= right) {
            // Top row, left to right
            for (int i = left; i <= right; ++i) {
                res.add(mat[top][i]);
            }
            top++;

            // Right column, top to bottom
            for (int i = top; i <= bottom; ++i) {
                res.add(mat[i][right]);
            }
            right--;

            // Bottom row, right to left (if a row is left)
            if (top <= bottom) {
                for (int i = right; i >= left; --i) {
                    res.add(mat[bottom][i]);
                }
                bottom--;
            }

            // Left column, bottom to top (if a column is left)
            if (left <= right) {
                for (int i = bottom; i >= top; --i) {
                    res.add(mat[i][left]);
                }
                left++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] mat = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        // Expected: [1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]
        System.out.println(spirallyTraverse(mat));
    }
}
