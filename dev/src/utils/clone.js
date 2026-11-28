// 对象工具（对应产物里的模块 5530 等）
// 原产物用 Object(i["a"])(a, b) 做浅拷贝合并、Object(i["a"])({}, x) 做浅拷贝。

/** 浅拷贝并合并（Object.assign 的等价实现，兼容老写法） */
export function clone(...args) {
  if (args.length === 0) return {}
  if (args.length === 1) {
    const src = args[0]
    if (Array.isArray(src)) return src.slice()
    if (src && typeof src === 'object') return { ...src }
    return src
  }
  return Object.assign({}, ...args)
}

/** 深拷贝（JSON 安全场景） */
export function deepClone(obj) {
  if (obj === null || typeof obj !== 'object') return obj
  try {
    return JSON.parse(JSON.stringify(obj))
  } catch (e) {
    return obj
  }
}

export default clone
