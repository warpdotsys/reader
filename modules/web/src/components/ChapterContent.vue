<template>
  <div
    v-if="!hideRenderedTitle"
    class="title"
    :class="{ 'title-clickable': titleClickable }"
    data-chapterpos="0"
    ref="titleRef"
    @click="handleTitleClick"
  >
    {{ renderedTitle }}<small v-if="titleAddition">{{ titleAddition }}</small>
  </div>
  <div
    v-for="(para, index) in renderedContents"
    :key="index"
    ref="paragraphRef"
    :data-chapterpos="chapterPos[index]"
  >
    <img
      class="full"
      v-if="/^\s*<img[^>]*src[^>]+>$/.test(String(para))"
      :src="getImageSrc(para)"
      @error.once="proxyImage"
      loading="lazy"
      @click="handleImageClick"
      :class="{ 'manga-scale-disabled': disableMangaScale }"
      :style="{ filter: mangaImageFilter }"
    />
    <p v-else :class="{ justified, selectable, 'zh-layout': zhLayout, 'bottom-justified': bottomJustify, 'line-height-grid': bodyToLineHeight, 'manga-scale-disabled': disableMangaScale }" :style="{ fontFamily, fontSize, '--manga-image-filter': mangaImageFilter }" v-html="replaceImage(para)" @click="handleContentClick" @error.capture="handleImgLoadError" />
  </div>
</template>

<script setup lang="ts">
import { isLegadoUrl, lazyRegex } from '@/utils/utils'
import API from '@api'
import jump from '@/plugins/jump'
import type { webReadConfig } from '@/web'
import { Converter as createSimplifiedToTraditional } from 'opencc-js/cn2t'
import { Converter as createTraditionalToSimplified } from 'opencc-js/t2cn'

const traditionalToSimplified = createTraditionalToSimplified({ from: 't', to: 'cn' })
const simplifiedToTraditional = createSimplifiedToTraditional({ from: 'cn', to: 't' })

const store = useBookStore()
const readWidth = computed(() => store.config.readWidth)
const lineImgWidth = computed(() => store.config.fontSize * 2)
const bookUrl = computed(() => store.readingBook.bookUrl)

const props = defineProps<{
  chapterIndex: number
  contents: Array<string>
  title: string
  spacing: webReadConfig['spacing']
  fontFamily: string
  fontSize: string
  justified?: boolean
  bottomJustify?: boolean
  selectable?: boolean
  zhLayout?: boolean
  titleAddition?: string
  imageClickWay?: string
  openLinksExternally?: boolean
  adaptSpecialStyle?: boolean
  bodyToLineHeight?: boolean
  mangaUi?: boolean
  hideMangaTitle?: boolean
  disableMangaScale?: boolean
  mangaImageFilter?: string
  titleClickable?: boolean
  chineseConverterType?: string | number
}>()

const justified = computed(() => props.justified !== false)
const bottomJustify = computed(() => props.bottomJustify === true)
const bodyToLineHeight = computed(() => props.bodyToLineHeight !== false)
const disableMangaScale = computed(() => props.mangaUi === true && props.disableMangaScale === true)
const mangaImageFilter = computed(() => props.mangaUi === true ? props.mangaImageFilter || 'none' : 'none')
const mangaParagraphCount = computed(() =>
  props.contents.filter(content => /^\s*<img[^>]*src/i.test(String(content))).length,
)
const isMangaChapter = computed(() =>
  props.mangaUi === true && mangaParagraphCount.value >= Math.max(1, props.contents.length / 2),
)
const hideRenderedTitle = computed(() => props.hideMangaTitle === true && isMangaChapter.value)
const selectable = computed(() => props.selectable !== false)
const zhLayout = computed(() => props.zhLayout === true)
const titleAddition = computed(() => props.titleAddition || '')
const imageClickWay = computed(() => props.imageClickWay || '0')
const chineseConverter = computed(() => {
  const type = String(props.chineseConverterType ?? '0')
  return type === '1'
    ? traditionalToSimplified
    : type === '2'
      ? simplifiedToTraditional
      : null
})
const convertVisibleText = (value: string) => {
  const converter = chineseConverter.value
  if (!converter || !value) return value
  const documentFragment = new DOMParser().parseFromString(`<body>${value}</body>`, 'text/html')
  const walker = documentFragment.createTreeWalker(documentFragment.body, NodeFilter.SHOW_TEXT)
  let node = walker.nextNode()
  while (node) {
    node.nodeValue = converter(node.nodeValue || '')
    node = walker.nextNode()
  }
  return documentFragment.body.innerHTML
}
const renderedTitle = computed(() => chineseConverter.value?.(props.title) ?? props.title)
const renderedContents = computed(() => props.contents.map(convertVisibleText))

const imgPatternStr = '<img[^>]*src=[\'"]([^\'"]*(?:[\'"][^>]+\\})?)[\'"][^>]*>'
const imgPattern = lazyRegex(imgPatternStr)
const imgPatternAll = lazyRegex(imgPatternStr, 'g')
const imgDataUrlPattern = lazyRegex('data:image[^;]+;base64,[^,]{39,}')

const replaceImage = (content: string) => {
  const rendered = content.replace(imgPatternAll(), (match, src) => {
    const dataUrl = src.match(imgDataUrlPattern())
    if (dataUrl) {
      return dataUrl[0]
    }
    if (isLegadoUrl(src)) {
      const proxySrc = API.getProxyImageUrl(
        bookUrl.value,
        src,
        lineImgWidth.value,
      )
      return match.replace(src, proxySrc)
    }
    return match
  })
  return props.adaptSpecialStyle === false
    ? rendered.replace(/\sstyle=(['"]).*?\1/gi, '')
    : rendered
}

const getImageSrc = (content: string) => {
  const src = content.match(imgPattern())![1] //reg tested in template
  const dataUrl = src.match(imgDataUrlPattern())
  if (dataUrl) {
      return dataUrl[0] //现成的base64图片，去掉阅读格式后缀
  }
  if (isLegadoUrl(src))
    return API.getProxyImageUrl(
      bookUrl.value,
      src,
      readWidth.value,
    )
  return src
}
const emit = defineEmits(['readedLengthChange', 'imageClick', 'linkClick', 'titleClick'])
const handleTitleClick = (event: MouseEvent) => {
  if (!props.titleClickable) return
  event.stopPropagation()
  emit('titleClick')
}
const handleImageClick = (event: MouseEvent) => {
  const image = event.currentTarget as HTMLImageElement
  event.preventDefault()
  event.stopPropagation()
  emit('imageClick', image.currentSrc || image.src, imageClickWay.value)
}
const handleContentClick = (event: MouseEvent) => {
  const target = event.target as Element | null
  const image = target?.closest('img') as HTMLImageElement | null
  if (image) {
    event.preventDefault()
    event.stopPropagation()
    emit('imageClick', image.currentSrc || image.src, imageClickWay.value)
    return
  }
  const link = target?.closest('a[href]') as HTMLAnchorElement | null
  if (link && props.openLinksExternally) {
    event.preventDefault()
    event.stopPropagation()
    emit('linkClick', link.href)
  }
}
const proxyImage = (event: Event) => {
  /* 获取IMG标签原始的src
    <img src="/test" />
    假设location.href = http://example.com
    event.target.src 返回 http://example.com/test
    (event.target as HTMLImageElement)?.getAttribute("src")  返回/test
  */
  const src = (event.target as HTMLImageElement)?.getAttribute("src")
  if (src != null && src.length > 0) {
    (event.target as HTMLImageElement).src = API.getProxyImageUrl(
      bookUrl.value,
      src,
      readWidth.value,
    )
  }
}

/**
 * 处理传入的IMG标签错误事件，自动替换图片的代理链接
 */
const handleImgLoadError = (event: Event) => {
  const target = event.target
  if (target instanceof HTMLImageElement) {
    const srcUrl = target.getAttribute("src")
    console.log(
      "[ChapterContent]: IMG Load Error, replace src:",
      srcUrl,
      "=>",
      API.getProxyImageUrl(
        bookUrl.value,
        srcUrl ?? "",
        readWidth.value,
      )
    )
    proxyImage(event)
  }
}

const calculateWordCount = (paragraph: string) => {
  //内嵌图片文字为1
  const imagePlaceHolder = ' '
  return paragraph.replace(imgPatternAll(), imagePlaceHolder).length
}
const chapterPos = computed(() => {
  let pos = -1
  return Array.from(props.contents, content => {
    pos += calculateWordCount(content) + 1 //计算上一段的换行符
    return pos
  })
})

const titleRef = ref<HTMLElement>()
const paragraphRef = ref<HTMLParagraphElement[]>()
const scrollToReadedLength = (length: number) => {
  if (length === 0) return
  const paragraphIndex = chapterPos.value.findIndex(
    wordCount => wordCount >= length,
  )
  if (paragraphIndex === -1) return
  nextTick(() => {
    jump(paragraphRef.value![paragraphIndex], {
      duration: 0,
    })
  })
}
defineExpose({
  scrollToReadedLength,
})
let intersectionObserver: IntersectionObserver | null = null
onMounted(() => {
  intersectionObserver = new IntersectionObserver(
    entries => {
      for (const { target, isIntersecting } of entries) {
        if (isIntersecting) {
          emit(
            'readedLengthChange',
            props.chapterIndex,
            parseInt((target as HTMLElement).dataset.chapterpos as string),
          )
        }
      }
    },
    {
      rootMargin: `0px 0px -${window.innerHeight - 24}px 0px`,
    },
  )
  intersectionObserver.observe(titleRef.value!)
  paragraphRef.value!.forEach(element => {
    intersectionObserver!.observe(element)
  })
})

onUnmounted(() => {
  intersectionObserver?.disconnect()
  intersectionObserver = null
})
</script>

<style lang="scss" scoped>
.title {
  margin-bottom: 57px;

  small {
    display: block;
    margin-top: 8px;
    color: #8a8f98;
    font-size: 13px;
    font-weight: 400;
  }
  font:
    24px / 32px PingFangSC-Regular,
    HelveticaNeue-Light,
    'Helvetica Neue Light',
    'Microsoft YaHei',
    sans-serif;
}


.justified {
  text-align: justify;
  text-justify: inter-ideograph;
}

.bottom-justified {
  text-align-last: justify;
}

.line-height-grid {
  min-block-size: 1lh;
  break-inside: avoid;
}

.title-clickable {
  cursor: pointer;

  &:hover {
    color: var(--legado-primary, #0f766e);
  }
}

.selectable {
  user-select: text;
}

p:not(.selectable) {
  user-select: none;
}

.zh-layout {
  line-break: strict;
  word-break: normal;
  overflow-wrap: anywhere;
}
p {
  display: block;
  word-wrap: break-word;
  /*   word-break: break-all; */
  letter-spacing: calc(v-bind('props.spacing.letter') * 1em);
  line-height: calc(1 + v-bind('props.spacing.line'));
  margin: calc(v-bind('props.spacing.paragraph') * 1em) 0;

  :deep(img) {
    height: 1em;
    filter: var(--manga-image-filter, none);
  }
}

img.manga-scale-disabled,
.manga-scale-disabled :deep(img) {
  max-width: 100%;
  touch-action: pan-y;
  user-select: none;
}

.full {
  display: block;
  width: 100%;
}
</style>
