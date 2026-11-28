// regenerator 运行时（对应产物里模块 c7eb）
//
// Babel 编译 async 函数后生成的形态：
//   asyncToGenerator(regeneratorRuntime.mark(function _callee() {
//     return regeneratorRuntime.wrap(function _callee$(_context) { ... }, _callee)
//   }))
//
// 所以需要导出：
//   regeneratorRuntime.mark(fn)                 标记 generator
//   regeneratorRuntime.wrap(innerFn, outerFn)   生成迭代器
//
// 现代浏览器原生支持 async/await，但 .vue 里是编译后的 generator 代码，
// 因此需要提供等价的运行时。

/** 标记 generator 函数 */
export function mark(genFun) {
  return genFun
}

/** 生成 generator 迭代器：执行 innerFn（一个手写的状态机） */
export function wrap(innerFn, outerFn, self) {
  const context = {
    prev: 0,
    next: 0,
    sent: undefined,
    rval: undefined,
    done: false,
    method: 'next',
    arg: undefined,
    // 供状态机使用的辅助方法
    stop() { this.done = true; return this.rval },
    abrupt(type, arg) {
      this.method = type
      this.arg = arg
      return { value: arg, done: true }
    },
    // 简化：直接透传
    delegate: null,
  }

  let finished = false
  let step

  return {
    next(arg) {
      if (finished) return { value: undefined, done: true }
      context.sent = arg
      try {
        step = innerFn.call(self, context)
      } catch (e) {
        finished = true
        throw e
      }
      // 状态机返回 { value, done } 或直接返回表达式的值
      if (step && typeof step === 'object' && 'done' in step) {
        finished = !!step.done
        return step
      }
      finished = true
      return { value: step, done: true }
    },
    throw(e) {
      finished = true
      throw e
    },
    return(v) {
      finished = true
      return { value: v, done: true }
    },
    [Symbol.iterator]() {
      return this
    },
  }
}

/** 运行时对象（产物里 Object(s["a"])() 返回的就是它） */
export const regeneratorRuntime = { mark, wrap }

export default regeneratorRuntime
