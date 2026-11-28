// localStorage 工具（还原自 webpack 模块 63f0）
// 原产物导出三个具名函数：c=写入、a=删除、b=读取
// 这里按语义重命名，并与原实现保持一致的行为（含 try/catch 与 JSON 解析）

/** 写入 localStorage（非字符串会 JSON 序列化） */
export function setStorage(key, value) {
  const v = typeof value === 'string' ? value : JSON.stringify(value)
  if (window.localStorage) window.localStorage.setItem(key, v)
}

/** 删除 localStorage 项 */
export function removeStorage(key) {
  if (window.localStorage) window.localStorage.removeItem(key)
}

/** 读取 localStorage，解析失败返回 defaultValue */
export function getStorage(key, defaultValue = null) {
  let raw = defaultValue
  try {
    raw = window.localStorage && window.localStorage.getItem(key)
    if (raw === null) return defaultValue
    if (raw) {
      const parsed = JSON.parse(raw)
      if (parsed !== null) return parsed
    }
    return raw
  } catch (e) {
    return raw
  }
}

export default { setStorage, removeStorage, getStorage }
