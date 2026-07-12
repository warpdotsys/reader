import { URL } from 'node:url'
import fs from 'node:fs'

const LEGADO_ASSETS_WEB_VUE_DIR = new URL(
  '../../../server/src/main/resources/web/vue',
  import.meta.url,
)
const VUE_DIST_DIR = new URL('../dist', import.meta.url)

if (!fs.existsSync(VUE_DIST_DIR)) {
  throw new Error('Vue dist directory does not exist. Run vite build first.')
}

console.log('> sync', VUE_DIST_DIR.pathname, '->', LEGADO_ASSETS_WEB_VUE_DIR.pathname)
fs.rmSync(LEGADO_ASSETS_WEB_VUE_DIR, { force: true, recursive: true })
fs.mkdirSync(LEGADO_ASSETS_WEB_VUE_DIR, { recursive: true })
fs.cpSync(VUE_DIST_DIR, LEGADO_ASSETS_WEB_VUE_DIR, { recursive: true })
console.log('> sync success')
