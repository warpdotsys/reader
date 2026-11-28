// 字体加载工具（对应产物里 N["f"]）
// 动态注入 @font-face 并等待加载完成

const loaded = new Set()

/** 加载自定义字体
 * @param name  字体族名
 * @param style 样式对象 { url, weight, style }
 */
export function loadCustomFont(name, style = {}) {
  if (!name || loaded.has(name)) return Promise.resolve(name)
  const url = typeof style === 'string' ? style : (style && style.url)
  if (!url) return Promise.resolve(name)

  const family = name.replace(/\s+/g, '-')
  const face = `@font-face{font-family:'${family}';src:url('${url}');}`
  const el = document.createElement('style')
  el.setAttribute('data-custom-font', family)
  el.textContent = face
  document.head.appendChild(el)
  loaded.add(name)

  try {
    if (document.fonts && document.fonts.load) {
      return document.fonts.load(`16px "${family}"`).then(() => name).catch(() => name)
    }
  } catch (e) {}
  return Promise.resolve(name)
}

export default { loadCustomFont }
