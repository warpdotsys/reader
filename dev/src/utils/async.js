// 异步工具（对应产物里的 regenerator-runtime asyncToGenerator 与 babel 包装）
// 现代浏览器原生支持 async/await，这里提供等价实现以便调用点不必改写。

/** 把 generator 函数包装成返回 Promise 的函数（原 asyncToGenerator） */
export function asyncToGenerator(fn) {
  return function (...args) {
    const self = this
    return new Promise((resolve, reject) => {
      const gen = fn.apply(self, args)
      function step(key, arg) {
        let info
        try {
          info = gen[key](arg)
        } catch (e) {
          reject(e)
          return
        }
        if (info.done) resolve(info.value)
        else Promise.resolve(info.value).then((v) => step('next', v), (e) => step('throw', e))
      }
      step('next')
    })
  }
}

/** asyncGeneratorStep 的简化别名（部分调用点用这个名字） */
export const asyncGeneratorStep = (gen, resolve, reject, _next, _throw, key, arg) => {
  try {
    const info = gen[key](arg)
    if (info.done) resolve(info.value)
    else Promise.resolve(info.value).then(_next, _throw)
  } catch (e) {
    reject(e)
  }
}

export default { asyncToGenerator, asyncGeneratorStep }
