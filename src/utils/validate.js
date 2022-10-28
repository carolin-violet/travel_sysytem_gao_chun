/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  return str.trim().length > 0
}



// 密码中必须包含大小写 字母、数字、特称字符，至少8个字符，最多32个字符
export function validPassword(str) {
  var pwdRegex = new RegExp('(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,32}');
  return pwdRegex.test(str)
}

/**
 * 判断字符串是否键盘三连（横着、竖着）
 * @param {String} str
 * @returns boolean 是否满足键盘3连键
 */
export function checkKeyboardContinuousChar(str) {
  const c1 = [
    ['!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+'],
    ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '{', '}', '|'],
    ['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ':', '"'],
    ['z', 'x', 'c', 'v', 'b', 'n', 'm', '<', '>', '?']
  ]
  const c2 = [
    ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '='],
    ['q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'],
    ['a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\''],
    ['z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/']
  ]
  str = str.toLowerCase().split('')
  // 获取坐标位置
  const y = []
  const x = []
  for (let c = 0; c < str.length; c++) {
    y[c] = 0// 当做~`键处理
    x[c] = -1
    for (let i = 0; i < c1.length; i++) {
      for (let j = 0; j < c1[i].length; j++) {
        if (str[c] == c1[i][j]) {
          y[c] = i
          x[c] = j
        }
      }
    }
    if (x[c] != -1) continue
    for (let i = 0; i < c2.length; i++) {
      for (let j = 0; j < c2[i].length; j++) {
        if (str[c] == c2[i][j]) {
          y[c] = i
          x[c] = j
        }
      }
    }
  }
  // 匹配坐标连线
  for (let c = 1; c < str.length - 1; c++) {
    // 横着同一行
    if (y[c - 1] == y[c] && y[c] == y[c + 1]) {
      // 从左往右或者从右往左一排
      if ((x[c - 1] + 1 == x[c] && x[c] + 1 == x[c + 1]) || (x[c + 1] + 1 == x[c] && x[c] + 1 == x[c - 1])) {
        return true
      }
    }
    // 竖着同一列
    if (x[c - 1] == x[c] && x[c] == x[c + 1]) {
      // 从下往上或者从下往下同一列
      if ((y[c - 1] + 1 == y[c] && y[c] + 1 == y[c + 1]) || (y[c + 1] + 1 == y[c] && y[c] + 1 == y[c - 1])) {
        return true
      }
    }
    // 竖着同一列（类似/而不是\的一列）
    if ((x[c - 1] + 1 == x[c] && x[c] + 1 == x[c + 1]) || (x[c - 1] - 1 == x[c] && x[c] - 1 == x[c + 1])) {
      // 从下往上或者从下往下同一列
      if ((y[c - 1] + 1 == y[c] && y[c] + 1 == y[c + 1]) || (y[c + 1] + 1 == y[c] && y[c] + 1 == y[c - 1])) {
        return true
      }
    }
  }
  return false
}

export function isPhone(value) {
  const res = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(value)
  return res
}

// 检测小数点后一到两位的正数价格
export function testPrice(value) {
  const res = /^[0-9]+(\.[0-9]{1,2})?$/.test(value)
  return res
}
