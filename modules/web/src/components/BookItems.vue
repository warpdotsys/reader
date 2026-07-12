<template>
  <div class="books-wrapper" :class="`layout-${layout}`" :style="shelfStyle">
    <div class="wrapper">
      <div
        class="book"
        v-for="book in books"
        :key="book.bookUrl"
        @click="handleClick(book)"
      >
        <div class="cover-img">
          <img
            v-if="coverUrl(book)"
            class="cover"
            :src="coverUrl(book)"
            :key="book.coverUrl"
            @error.once="proxyImage"
            alt=""
            loading="lazy"
          />
          <div v-else class="fallback-cover" :style="fallbackCoverStyle">
            <strong v-if="showCoverName">{{ book.name }}</strong>
            <span v-if="showCoverAuthor">{{ book.author }}</span>
          </div>
        </div>
        <div class="info">
          <div class="name">{{ book.name }}</div>
          <div class="sub">
            <div class="author">
              {{ book.author }}
            </div>
            <div class="tags" v-if="isSearch">
              <el-tag
                v-for="tag in book.kind?.split(',').slice(0, 2)"
                :key="tag"
              >
                {{ tag }}
              </el-tag>
            </div>
            <div class="update-info" v-if="!isSearch">
              <div class="dot">•</div>
              <div class="size">共{{ (book as Book).totalChapterNum }}章</div>
              <template v-if="showUnread && unreadCount(book) > 0">
                <div class="dot">•</div>
                <div class="unread-count">{{ unreadLabel }} {{ unreadCount(book) }}</div>
              </template>
              <template v-if="showLastUpdate">
                <div class="dot">•</div>
                <div class="date">{{ dateFormat((book as Book).lastCheckTime) }}</div>
              </template>
            </div>
          </div>
          <div class="intro" v-if="isSearch">{{ book.intro }}</div>

          <div class="dur-chapter" v-if="!isSearch">
            已读：{{ (book as Book).durChapterTitle }}
          </div>
          <div class="last-chapter">最新：{{ book.latestChapterTitle }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import type { Book, SeachBook } from '@/book'
import { dateFormat, isLegadoUrl } from '../utils/utils'
import API from '@api'
const props = defineProps<{
  books: Array<Book | SeachBook>
  isSearch: boolean
  layout?: 'list' | 'grid' | 'wall'
  margin?: number
  showLastUpdate?: boolean
  showUnread?: boolean
  defaultCover?: string
  showCoverName?: boolean
  showCoverAuthor?: boolean
  loadCovers?: boolean
  fallbackCoverStyle?: Record<string, string | undefined>
}>()

const layout = computed(() => props.layout || 'list')
const showLastUpdate = computed(() => props.showLastUpdate !== false)
const showUnread = computed(() => props.showUnread !== false)
const showCoverName = computed(() => props.showCoverName !== false)
const showCoverAuthor = computed(() => props.showCoverAuthor !== false)
const shelfStyle = computed(() => ({
  padding: `${Math.max(0, props.margin || 0)}px`,
}))
const unreadLabel = '\u672a\u8bfb'
const unreadCount = (book: Book | SeachBook) => {
  if (!("totalChapterNum" in book)) return 0
  const total = Number(book.totalChapterNum || 0)
  const readIndex = Number(book.durChapterIndex || 0)
  return Math.max(0, total - readIndex - 1)
}

const emit = defineEmits(['bookClick'])
const handleClick = (book: Book | SeachBook) => emit('bookClick', book)
const coverUrl = (book: Book | SeachBook) => {
  if (props.loadCovers === false) return ''
  const customCoverUrl = 'customCoverUrl' in book ? book.customCoverUrl : undefined
  const preferredCover = customCoverUrl || book.coverUrl
  if (preferredCover) {
    return isLegadoUrl(preferredCover) ? API.getProxyCoverUrl(preferredCover) : preferredCover
  }
  return props.defaultCover || ''
}
const proxyImage = (evt: Event) => {
  const target = evt.target as HTMLImageElement
  target.src = API.getProxyCoverUrl(target.src)
}

const subJustify = computed(() =>
  props.isSearch ? 'space-between' : 'flex-start',
)
</script>

<style lang="scss" scoped>
.books-wrapper {
  overflow: auto;

  .wrapper {
    display: grid;
    grid-template-columns: repeat(auto-fill, 380px);
    justify-content: space-around;
    grid-gap: 10px;

    .book {
      user-select: none;
      display: flex;
      cursor: pointer;
      margin-bottom: 18px;
      padding: 24px 24px;
      width: 360px;
      flex-direction: row;
      justify-content: space-around;

      .cover-img {
        width: 84px;
        height: 112px;

        .cover {
          width: 84px;
          height: 112px;
        }
      }
        .fallback-cover {
          width: 84px;
          height: 112px;
          padding: 12px;
          box-sizing: border-box;
          border-radius: 6px;
          background: linear-gradient(145deg, var(--legado-primary), var(--legado-accent));
          color: #fff;
          display: flex;
          flex-direction: column;
          justify-content: flex-end;
          gap: 4px;
          overflow: hidden;

          strong,
          span {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }

          strong {
            font-size: 13px;
          }

          span {
            font-size: 11px;
            opacity: 0.82;
          }
        }

      .info {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: left;
        height: 112px;
        margin-left: 20px;
        flex: 1;
        overflow: hidden;

        .name {
          width: fit-content;
          font-size: 16px;
          font-weight: 700;
          color: #33373d;
        }

        .sub {
          display: flex;
          flex-direction: row;
          align-items: baseline;
          justify-content: v-bind('subJustify');
          font-size: 12px;
          font-weight: 600;
          color: #6b6b6b;
          .tags {
            :deep(.el-tag) {
              margin-right: 0.5em;
            }
          }
          .update-info {
            display: flex;
            align-items: center;

            .unread-count {
              color: #b45309;
              font-weight: 700;
            }
            .dot {
              margin: 0 7px;
            }
          }
        }

        .intro,
        .dur-chapter,
        .last-chapter {
          color: #969ba3;
          font-size: 13px;
          margin-top: 3px;
          font-weight: 500;
          word-wrap: break-word;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 1;
          line-clamp: 1;
          text-align: left;
        }
      }
    }

    .book:hover {
      background: rgba(0, 0, 0, 0.1);
      transition-duration: 0.5s;
    }
  }


  &.layout-grid,
  &.layout-wall {
    .wrapper {
      grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
      justify-content: initial;
      gap: 18px;

      .book {
        width: auto;
        min-width: 0;
        margin: 0;
        padding: 12px;
        border: 1px solid #e5e7eb;
        border-radius: 8px;
        background: #fff;
        flex-direction: column;
        align-items: stretch;

        .cover-img,
        .cover-img .cover,
        .cover-img .fallback-cover {
          width: 100%;
          height: auto;
          aspect-ratio: 3 / 4;
          object-fit: cover;
        }

        .info {
          height: auto;
          min-height: 92px;
          margin: 12px 0 0;
          gap: 6px;
        }
      }
    }
  }

  &.layout-wall {
    .wrapper {
      grid-template-columns: repeat(auto-fill, minmax(118px, 1fr));
      gap: 14px;

      .book {
        padding: 0;
        overflow: hidden;
        border: 0;
        background: transparent;

        .cover-img,
        .cover-img .cover,
        .cover-img .fallback-cover {
          border-radius: 6px;
        }

        .info {
          min-height: 0;
          margin: 8px 2px 0;

          .sub,
          .dur-chapter,
          .last-chapter {
            display: none;
          }

          .name {
            font-size: 14px;
          }
        }
      }
    }
  }
  .wrapper:last-child {
    margin-right: auto;
  }
}

.books-wrapper::-webkit-scrollbar {
  width: 0 !important;
}

@media screen and (max-width: 750px) {
  .books-wrapper {
    .wrapper {
      display: flex;
      flex-direction: column;

      .book {
        box-sizing: border-box;
        width: 100%;
        margin-bottom: 0;
        padding: 10px 20px;
      }
    }
  }
}
</style>
