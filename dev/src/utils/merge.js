// 对象合并工具（对应产物里的 c["b"]）
// 原产物写法：Object(c["b"])({}, source) / Object(c["b"])(target, {}, source2)
// 语义等同于 Object.assign
export function merge(...args) {
  return Object.assign({}, ...args.filter((a) => a !== null && a !== undefined))
}

export default merge
