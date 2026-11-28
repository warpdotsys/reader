// Date.prototype.format 扩展（原版依赖，非标准 API）
//
// .vue 里大量使用 `new Date(x).format("yy-MM-dd hh:mm")`，
// 这是 Java SimpleDateFormat 风格的格式串，浏览器原生没有。
// 这里按原版语义实现常用占位符。

if (!Date.prototype.format) {
  // eslint-disable-next-line no-extend-native
  Date.prototype.format = function (fmt) {
    if (!fmt) return this.toString()
    const d = this
    const pad = (n, len) => String(n).padStart(len, '0')
    const map = {
      yyyy: d.getFullYear(),
      yy: String(d.getFullYear()).slice(-2),
      MM: pad(d.getMonth() + 1, 2),
      M: d.getMonth() + 1,
      dd: pad(d.getDate(), 2),
      d: d.getDate(),
      HH: pad(d.getHours(), 2),
      H: d.getHours(),
      hh: pad(d.getHours() % 12 || 12, 2),
      h: d.getHours() % 12 || 12,
      mm: pad(d.getMinutes(), 2),
      m: d.getMinutes(),
      ss: pad(d.getSeconds(), 2),
      s: d.getSeconds(),
      SSS: pad(d.getMilliseconds(), 3),
      a: d.getHours() < 12 ? 'AM' : 'PM',
    }
    // 长 key 先替换，避免 yy 吃掉 yyyy
    const keys = Object.keys(map).sort((a, b) => b.length - a.length)
    let out = fmt
    for (const k of keys) {
      out = out.split(k).join(String(map[k]))
    }
    return out
  }
}

export default true
