// 通用工具（对应产物里的模块 U / cx 等聚合工具）
// 从调用点语义还原：
//   U["e"]()      -> isMiniInterface()   判断是否移动端小界面
//   U["g"](fn)    -> throttle(fn)        节流
//   U["i"](fn, key, force, onDone) -> cachedRequest(...)  带 localStorage 缓存的请求
//   U["b"](fn, key, force)         -> cachedFetch(...)    带缓存的取数

import { getStorage, setStorage } from './storage'

/** 是否移动端/小屏界面 */
export function isMiniInterface() {
  const ua = (window.navigator && window.navigator.userAgent) || ''
  const isMobileUA = /Android|iPhone|iPad|iPod|Mobile|HarmonyOS/i.test(ua)
  const isNarrow = window.innerWidth < 720
  return isMobileUA || isNarrow
}

/** 简易节流 */
export function throttle(fn, wait = 300) {
  let last = 0
  let timer = null
  return function (...args) {
    const now = Date.now()
    const remain = wait - (now - last)
    if (remain <= 0) {
      if (timer) { clearTimeout(timer); timer = null }
      last = now
      return fn.apply(this, args)
    }
    if (!timer) {
      timer = setTimeout(() => {
        last = Date.now()
        timer = null
        fn.apply(this, args)
      }, remain)
    }
  }
}

/** 带 localStorage 缓存的请求
 * @param fn       返回 Promise 的取数函数
 * @param key      缓存键
 * @param force    强制刷新
 * @param onDone   成功后额外回调
 */
export function cachedRequest(fn, key, force, onDone) {
  if (!force && key) {
    const cached = getStorage(key)
    if (cached) {
      if (onDone) onDone(cached)
      return Promise.resolve(cached)
    }
  }
  return fn().then((res) => {
    if (key) setStorage(key, res)
    if (onDone) onDone(res)
    return res
  })
}

/** 带缓存的取数（不同调用点用 b / i，语义一致） */
export const cachedFetch = cachedRequest

export default { isMiniInterface, throttle, cachedRequest, cachedFetch }
