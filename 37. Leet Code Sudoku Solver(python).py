class Solution:
    def solveSudoku(self, board: List[List[str]]) -> None:
        def dfs(k):
            nonlocal ok
            if k == len(t):
                ok = True
                return
            i, j = t[k]
            for v in range(9):
                if row[i][v] == col[j][v] == block[i // 3][j // 3][v] == False:
                    row[i][v] = col[j][v] = block[i // 3][j // 3][v] = True
                    board[i][j] = str(v + 1)
                    dfs(k + 1)
                    row[i][v] = col[j][v] = block[i // 3][j // 3][v] = False
                if ok:
                    return

        row = [[False] * 9 for _ in range(9)]
        col = [[False] * 9 for _ in range(9)]
        block = [[[False] * 9 for _ in range(3)] for _ in range(3)]
        t = []
        ok = False
        for i in range(9):
            for j in range(9):
                if board[i][j] == '.':
                    t.append((i, j))
                else:
                    v = int(board[i][j]) - 1
                    row[i][v] = col[j][v] = block[i // 3][j // 3][v] = True
        dfs(0)

############

class Solution(object):
  def solveSudoku(self, board):
    """
    :type board: List[List[str]]
    :rtype: void Do not return anything, modify board in-place instead.
    """
    cacheBox = [[0] * len(board) for _ in range(len(board))]
    cacheRow = [[0] * len(board) for _ in range(len(board))]
    cacheCol = [[0] * len(board) for _ in range(len(board))]

    def helper(board, i, j, cacheRow, cacheCol, cacheBox):
      if board[i][j] == ".":
        for k in range(1, 10):
          if i < 0 or i >= len(board) or j < 0 or j >= len(board):
            continue
          ib = (i / 3) * 3 + j / 3
          if cacheRow[i][k - 1] == 1 or cacheCol[j][k - 1] == 1 or cacheBox[ib][k - 1] == 1:
            continue

          cacheRow[i][k - 1] = cacheCol[j][k - 1] = cacheBox[ib][k - 1] = 1
          board[i][j] = str(k)
          if i == j == len(board) - 1:
            return True
          if i + 1 < len(board):
            if helper(board, i + 1, j, cacheRow, cacheCol, cacheBox):
              return True
          elif j + 1 < len(board):
            if helper(board, 0, j + 1, cacheRow, cacheCol, cacheBox):
              return True
          board[i][j] = "."
          cacheRow[i][k - 1] = cacheCol[j][k - 1] = cacheBox[ib][k - 1] = 0
      else:
        if i == j == len(board) - 1:
          return True
        if i + 1 < len(board):
          if helper(board, i + 1, j, cacheRow, cacheCol, cacheBox):
            return True
        elif j + 1 < len(board):
          if helper(board, 0, j + 1, cacheRow, cacheCol, cacheBox):
            return True
      return False

    for i in range(len(board)):
      for j in range(len(board)):
        if board[i][j] != ".":
          ib = (i / 3) * 3 + j / 3
          k = int(board[i][j]) - 1
          cacheRow[i][k] = cacheCol[j][k] = cacheBox[ib][k] = 1
    print
    helper(board, 0, 0, cacheRow, cacheCol, cacheBox)