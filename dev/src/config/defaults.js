// 默认配置（从 webpack 模块 bfff 提取）
// 内含：baseConfig / shelfConfig / searchConfig / speechVoiceConfig / themes / fonts
export const baseConfig = {
  theme: 0,
  font: 0,
  fontSize: 1,
  fontWeight: 4,
  animateMSTime: 3,
  readWidth: 8,
  lineHeight: 1,
  autoReadingPixel: 1,
  autoReadingLineTime: 1,
  topPadding: 0,
  bottomPadding: 0,
  horizontalPadding: 0,
  epubMode: 'iframe',
  chapterRequestTimeout: 10,
  quickKeyMode: '默认',
}

export const shelfConfig = {
                    showBookGroup: -1,
                    viewCate: "list",
                    bookOrder: "durChapterTime",
                    imageProxy: "noProxy",
                    virtualOptimize: "no"
                }

export const searchConfig = {
                    searchType: "multi",
                    bookSourceGroup: "",
                    bookSourceUrl: "",
                    concurrentCount: 4
                }

export const speechVoiceConfig = {
                    ttsType: "local",
                    cacheTTSAudio: false,
                    voiceName: "",
                    speechRate: 1,
                    speechPitch: 1
                }

export const defaultNightTheme = 6

export const themes = [{
                    body: "none repeat",
                    content: "none repeat",
                    popup: "none repeat"
                }, {
                    body: "none repeat",
                    content: "none repeat",
                    popup: "none repeat"
                }, {
                    body: "none repeat",
                    content: "none repeat",
                    popup: "none repeat"
                }, {
                    body: "none repeat",
                    content: "none repeat",
                    popup: "none repeat"
                }, {
                    body: "#ebcece repeat",
                    content: "#f5e4e4 repeat",
                    popup: "#faeceb repeat"
                }, {
                    body: "none repeat",
                    content: "none repeat",
                    popup: "none repeat"
                }, {
                    body: "none repeat",
                    content: "none repeat",
                    popup: "#121212"
                }, {
                    body: "#f7f7f7 repeat",
                    content: "#fff repeat",
                    popup: "#f7f7f7 repeat"
                }]

export const fonts = [{
                    fontFamily: "custom-system"
                }, {
                    fontFamily: "custom-ht, reader-ht"
                }, {
                    fontFamily: "custom-kt, reader-kt"
                }, {
                    fontFamily: "custom-st, reader-st"
                }, {
                    fontFamily: "custom-fs, reader-fs"
                }]

export const config = {
  ...baseConfig,
  customConfig: '内置白天',
  autoTheme: true,
  pageType: '正常',
}

export default {
  config,
  shelfConfig,
  searchConfig,
  speechVoiceConfig,
  defaultNightTheme,
  themes,
  fonts,
}
