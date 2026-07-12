import axios from 'axios'

/** @type {string} localStorage保存自定义阅读http服务接口的键值 */
export const baseURL_localStorage_key = 'remoteUrl'
export const local_server_token_key = 'legadoServerToken'
const SECOND = 1000

const ajax = axios.create({
  baseURL:
    import.meta.env.VITE_API ||
    localStorage.getItem(baseURL_localStorage_key) ||
    location.origin,
  timeout: 120 * SECOND,
})

ajax.interceptors.request.use(config => {
  const token = localStorage.getItem(local_server_token_key)
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

export const setLocalServerToken = (token: string) => {
  if (token) localStorage.setItem(local_server_token_key, token)
  else localStorage.removeItem(local_server_token_key)
}

export default ajax
