<template>
  <div class="settings-center">
    <aside class="settings-sidebar">
      <div class="brand">
        <div class="brand-mark">L</div>
        <div>
          <h1>Legado Web</h1>
          <p>Settings Center</p>
        </div>
      </div>

      <nav class="main-nav" aria-label="Legado Web">
        <router-link
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="nav-item"
        >
          <component :is="item.icon" />
          <span>{{ item.label }}</span>
        </router-link>
      </nav>

      <div class="section-list" aria-label="设置分类">
        <button
          v-for="section in sections"
          :key="section.id"
          :class="{ active: activeSection === section.id }"
          type="button"
          @click="activeSection = section.id"
        >
          <component :is="section.icon" />
          <span>{{ section.title }}</span>
        </button>
      </div>
    </aside>

    <main class="settings-main">
      <section class="settings-header">
        <div>
          <h2>设置中心</h2>
          <p>对齐 Android App 的设置入口，服务端保存并参与备份恢复。</p>
        </div>
        <div class="header-actions">
          <el-button :icon="Refresh" :loading="loading" @click="loadSettings">
            刷新
          </el-button>
          <el-button :icon="Download" @click="exportSettings">导出设置</el-button>
          <el-button :icon="Upload" @click="settingsInput?.click()">
            导入设置
          </el-button>
          <el-button
            v-if="activeSection === 'backup'"
            :icon="Connection"
            :loading="testingWebDav"
            :disabled="dirty"
            @click="testWebDavConnection"
          >
            测试 WebDAV
          </el-button>
          <el-button
            v-if="activeSection === 'network'"
            :icon="Upload"
            :loading="testingUploadRule"
            :disabled="dirty"
            @click="testDirectUploadRule"
          >
            测试直链上传
          </el-button>
          <el-button
            v-if="activeSection === 'maintenance'"
            :icon="SetUp"
            :loading="runningMaintenance"
            :disabled="dirty"
            @click="runMaintenance"
          >
            执行维护
          </el-button>
          <el-button
            :icon="Check"
            type="primary"
            :disabled="!dirty"
            :loading="saving"
            @click="saveSettings"
          >
            保存
          </el-button>
          <input
            ref="settingsInput"
            class="visually-hidden"
            type="file"
            accept="application/json,.json"
            @change="importSettings"
          />
        </div>
      </section>

      <section class="settings-layout">
        <article class="settings-panel">
          <div class="panel-heading">
            <div class="panel-icon">
              <component :is="currentSection.icon" />
            </div>
            <div>
              <h3>{{ currentSection.title }}</h3>
              <p>{{ currentSection.subtitle }}</p>
            </div>
          </div>

          <div class="setting-rows">
            <div
              v-for="field in currentSection.fields"
              :key="field.path"
              class="setting-row"
            >
              <div class="setting-copy">
                <div class="setting-title-line">
                  <strong>{{ field.label }}</strong>
                  <el-tag :type="fieldStatusType(field.status)" effect="plain">
                    {{ fieldStatusLabel(field.status) }}
                  </el-tag>
                </div>
                <span>{{ field.summary }}</span>
                <code>{{ field.path }}</code>
              </div>

              <div class="setting-control">
                <el-switch
                  v-if="field.type === 'boolean'"
                  :model-value="booleanValue(field.path)"
                  @change="writeField(field.path, $event)"
                />

                <el-select
                  v-else-if="field.type === 'select'"
                  :model-value="stringValue(field.path)"
                  @change="writeField(field.path, $event)"
                >
                  <el-option
                    v-for="option in field.options"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                  />
                </el-select>

                <el-color-picker
                  v-else-if="field.type === 'color'"
                  :model-value="stringValue(field.path)"
                  @change="writeField(field.path, $event || '')"
                />

                <el-input-number
                  v-else-if="field.type === 'number'"
                  :model-value="numberValue(field.path)"
                  :min="field.min"
                  :max="field.max"
                  :step="field.step || 1"
                  controls-position="right"
                  @change="writeField(field.path, $event ?? 0)"
                />

                <el-input
                  v-else-if="field.type === 'textarea'"
                  :model-value="stringValue(field.path)"
                  :autosize="{ minRows: 3, maxRows: 7 }"
                  type="textarea"
                  @input="writeField(field.path, $event)"
                />

                <el-input
                  v-else
                  :model-value="stringValue(field.path)"
                  :type="field.type === 'password' ? 'password' : 'text'"
                  :show-password="field.type === 'password'"
                  @input="writeField(field.path, $event)"
                />
              </div>
            </div>
          </div>
        </article>

        <aside class="parity-panel">
          <h3>App 功能迁移面</h3>
          <p>这不是最终清单，只是把 Android 现有能力拆成 Web 端可持续推进的工作面。</p>
          <div class="parity-list">
            <div v-for="item in parityItems" :key="item.name" class="parity-row">
              <component :is="item.icon" />
              <div>
                <strong>{{ item.name }}</strong>
                <span>{{ item.state }}</span>
              </div>
            </div>
          </div>

          <div class="shortcut-heading">
            <h4>Android 快捷入口</h4>
            <span>直接打开对应的 Web 工作区</span>
          </div>
          <div class="shortcut-list">
            <button
              v-for="entry in appEntries"
              :key="entry.label"
              type="button"
              @click="openAppEntry(entry)"
            >
              <component :is="entry.icon" />
              <span>
                <strong>{{ entry.label }}</strong>
                <small>{{ entry.summary }}</small>
              </span>
            </button>
          </div>

          <el-button :icon="RefreshLeft" text type="danger" @click="resetSettings">
            恢复默认设置
          </el-button>
        </aside>
      </section>
    </main>
  </div>
</template>

<script setup lang="ts">
import type { Component } from 'vue'
import {
  Check,
  Collection,
  Connection,
  DataAnalysis,
  DocumentCopy,
  Download,
  Files,
  Link,
  Monitor,
  Operation,
  Reading,
  Refresh,
  RefreshLeft,
  Search,
  SetUp,
  Setting,
  Upload,
  VideoPlay,
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import API from '@api'
import { setLocalServerToken } from '@/api/axios'
import type { AppSettings, LeagdoApiResponse } from '@api'
import { applyAppSettings } from '@/settings/runtime'

type ResponseBox<T> = {
  data: LeagdoApiResponse<T>
}

type FieldStatus = 'live' | 'stored' | 'planned'

type FieldType =
  | 'boolean'
  | 'select'
  | 'color'
  | 'number'
  | 'text'
  | 'password'
  | 'textarea'

type FieldOption = {
  label: string
  value: string
}

type SettingField = {
  path: string
  label: string
  summary: string
  type: FieldType
  status: FieldStatus
  options?: FieldOption[]
  min?: number
  max?: number
  step?: number
}

type SettingSection = {
  id: string
  title: string
  subtitle: string
  icon: Component
  fields: SettingField[]
}
type AppEntry = {
  label: string
  summary: string
  icon: Component
  path: string
  query?: Record<string, string>
}

const unwrap = <T,>(response: ResponseBox<T>): T => {
  if (!response.data.isSuccess) {
    throw new Error(response.data.errorMsg || '请求失败')
  }
  return response.data.data
}

const navItems = [
  { label: '书架', path: '/', icon: Reading },
  { label: '书源', path: '/bookSource', icon: Collection },
  { label: '订阅源', path: '/rssSource', icon: Link },
  { label: '功能', path: '/features', icon: SetUp },
  { label: '设置', path: '/settings', icon: Setting },
  { label: '控制台', path: '/server', icon: DataAnalysis },
]

const selectOptions = {
  language: [
    { label: '自动', value: 'auto' },
    { label: '简体中文', value: 'zh' },
    { label: '繁体中文', value: 'zh-rTW' },
    { label: 'English', value: 'en' },
  ],
  defaultHomePage: [
    { label: '书架', value: 'bookshelf' },
    { label: '发现', value: 'explore' },
    { label: '订阅', value: 'rss' },
    { label: '设置', value: 'my' },
  ],
  bookshelfLayout: [
    { label: '列表', value: '0' },
    { label: '网格', value: '1' },
    { label: '封面墙', value: '2' },
  ],
  bookshelfSort: [
    { label: '最近阅读', value: '0' },
    { label: '更新时间', value: '1' },
    { label: '书名', value: '2' },
    { label: '手动排序', value: '3' },
  ],
  bookGroupStyle: [
    { label: '顶部标签', value: '0' },
    { label: '侧边分组', value: '1' },
    { label: '抽屉分组', value: '2' },
  ],
  themeMode: [
    { label: '跟随系统', value: '0' },
    { label: '日间', value: '1' },
    { label: '夜间', value: '2' },
    { label: '墨水屏', value: '3' },
  ],
  screenOrientation: [
    { label: '跟随系统', value: '0' },
    { label: '竖屏', value: '1' },
    { label: '横屏', value: '2' },
  ],
  keepLight: [
    { label: '跟随系统', value: '0' },
    { label: '常亮', value: '1' },
    { label: '5 分钟', value: '300' },
    { label: '10 分钟', value: '600' },
  ],
  doublePage: [
    { label: '关闭', value: '0' },
    { label: '平板横屏', value: 'landscape' },
    { label: '始终启用', value: 'always' },
  ],
  progressBar: [
    { label: '按页', value: 'page' },
    { label: '按章节', value: 'chapter' },
    { label: '按全书', value: 'book' },
  ],
  clickImage: [
    { label: '默认', value: '0' },
    { label: '查看图片', value: 'view' },
    { label: '浏览器打开', value: 'browser' },
  ],
  clickAction: [
    { label: '菜单', value: '0' },
    { label: '下一页', value: '1' },
    { label: '上一页', value: '2' },
    { label: '无动作', value: '3' },
  ],
  contentSelectSpeakMod: [
    { label: '关闭', value: '0' },
    { label: '选中即朗读', value: '1' },
    { label: '显示朗读按钮', value: '2' },
  ],
  chineseConverter: [
    { label: '关闭', value: '0' },
    { label: '繁体转简体', value: '1' },
    { label: '简体转繁体', value: '2' },
  ],
  exportType: [
    { label: 'TXT', value: '0' },
    { label: 'EPUB', value: '1' },
    { label: '自定义', value: '2' },
  ],
  localBookImportSort: [
    { label: '文件名', value: '0' },
    { label: '修改时间', value: '1' },
    { label: '自然排序', value: '2' },
  ],
  editNonPrintable: [
    { label: '隐藏', value: '0' },
    { label: '显示符号', value: '1' },
    { label: '高亮显示', value: '2' },
  ],
  updateVariant: [
    { label: '默认版', value: 'default_version' },
    { label: 'Google Play', value: 'google_play' },
    { label: 'F-Droid', value: 'fdroid' },
  ],
}

const sections: SettingSection[] = [
  {
    id: 'main',
    title: '主页与入口',
    subtitle: '对应 Android “我的”页和主界面设置。',
    icon: Monitor,
    fields: [
      f('main.language', '语言', '设置浏览器语言、日期区域格式和全局导航标签。', 'select', 'live', selectOptions.language),
      f('main.defaultHomePage', '默认首页', '启动时进入书架、发现、订阅或设置。', 'select', 'live', selectOptions.defaultHomePage),
      f('main.themeMode', '主题模式', '日间、夜间、跟随系统、墨水屏。', 'select', 'live', selectOptions.themeMode),
      f('main.showDiscovery', '显示发现', '控制发现入口是否展示。', 'boolean', 'live'),
      f('main.showRss', '显示订阅', '控制 RSS/订阅入口是否展示。', 'boolean', 'live'),
      f('main.auto_refresh', '启动自动刷新', '进入书架后并发刷新书籍章节信息。', 'boolean', 'live'),
      f('main.onlyUpdateRead', '只更新已读', '自动或手动刷新书架时只处理有阅读进度的书籍。', 'boolean', 'live'),
      f('main.defaultToRead', '默认继续阅读', '从默认入口打开时，优先恢复本浏览器最近阅读的位置。', 'boolean', 'live'),
      f('main.webPort', 'Web 服务端口', '未传 --port 时重启后生效；命令行参数优先。', 'number', 'live', undefined, 1, 65535),
      f('main.webServiceWakeLock', 'Web 页面保活', '页面可见时通过浏览器 Wake Lock 阻止设备休眠，隐藏后自动释放并在返回时恢复。', 'boolean', 'live'),
      f('main.bookshelfLayout', '书架布局', '列表、网格或封面墙布局偏好。', 'select', 'live', selectOptions.bookshelfLayout),
      f('main.bookshelfSort', '书架排序', '书籍默认排序策略。', 'select', 'live', selectOptions.bookshelfSort),
      f('main.bookGroupStyle', '分组样式', '控制书架分组使用顶部标签、侧边栏或下拉选择。', 'select', 'live', selectOptions.bookGroupStyle),
      f('main.bookshelfMargin', '书架边距', '书架条目和封面网格的留白。', 'number', 'live', undefined, 0, 48),
      f('main.showUnread', '显示未读数量', '书架条目显示未读章节数量。', 'boolean', 'live'),
      f('main.showLastUpdateTime', '显示更新时间', '书架条目显示最近更新时间。', 'boolean', 'live'),
      f('main.showWaitUpCount', '显示待更数量', '书架显示待更新书籍计数。', 'boolean', 'live'),
      f('main.showBookshelfFastScroller', '书架快速滚动条', '大书架快速定位控件。', 'boolean', 'live'),
      f('main.openBookInfoByClickTitle', '点标题进详情', '阅读页章节标题可点击并打开当前书籍信息。', 'boolean', 'live'),
      f('main.enableReadRecord', '记录阅读历史', '控制阅读进度与最近阅读记录的本地和服务端保存。', 'boolean', 'live'),
      f('main.searchScope', '默认搜索范围', '书源管理默认按名称、URL 或分组过滤，多个条件用逗号分隔。', 'text', 'live'),
      f('main.searchGroup', '默认搜索分组', '书源管理默认分组过滤，多个分组用逗号分隔。', 'text', 'live'),
    ],
  },
  {
    id: 'theme',
    title: '主题与封面',
    subtitle: '对齐主题设置、封面设置和欢迎页设置。',
    icon: Operation,
    fields: [
      f('theme.transparentStatusBar', '沉浸状态栏', '全局导航贴合浏览器安全区顶部。', 'boolean', 'live'),
      f('theme.immNavigationBar', '沉浸导航栏', '全局导航关闭背景模糊以融入页面。', 'boolean', 'live'),
      f('theme.barElevation', '工具栏阴影', '控制全局导航阴影高度。', 'number', 'live', undefined, 0, 24),
      f('theme.fontScale', '界面字体缩放', '影响 Web 工作台字体比例。', 'number', 'live', undefined, 80, 140),
      f('theme.colorPrimary', '日间主色', '日间主题主色。', 'color', 'live'),
      f('theme.colorAccent', '日间强调色', '日间主题强调色。', 'color', 'live'),
      f('theme.colorBackground', '日间背景色', '日间背景色。', 'color', 'live'),
      f('theme.colorPrimaryNight', '夜间主色', '夜间主题主色。', 'color', 'live'),
      f('theme.colorAccentNight', '夜间强调色', '夜间强调色。', 'color', 'live'),
      f('theme.colorBackgroundNight', '夜间背景色', '夜间背景色。', 'color', 'live'),
      f('theme.colorBottomBackground', '日间导航栏色', '底部导航/工具栏背景色。', 'color', 'live'),
      f('theme.colorBottomBackgroundNight', '夜间导航栏色', '夜间底部导航/工具栏背景色。', 'color', 'live'),
      f('theme.backgroundImage', '日间背景图', '主题背景图片路径或 URL。', 'text', 'live'),
      f('theme.backgroundImageBlurring', '日间背景模糊', '背景图模糊半径。', 'number', 'live', undefined, 0, 40),
      f('theme.backgroundImageNight', '夜间背景图', '夜间主题背景图片路径或 URL。', 'text', 'live'),
      f('theme.backgroundImageNightBlurring', '夜间背景模糊', '夜间背景图模糊半径。', 'number', 'live', undefined, 0, 40),
      f('theme.transparentNavBar', '日间透明导航栏', '日间模式使用半透明全局导航背景。', 'boolean', 'live'),
      f('theme.transparentNavBarNight', '夜间透明导航栏', '夜间模式使用半透明全局导航背景。', 'boolean', 'live'),
      f('theme.loadCoverOnlyWifi', '仅 Wi-Fi 加载封面', '浏览器识别为蜂窝网络时使用文字封面。', 'boolean', 'live'),
      f('theme.coverRule', '封面规则', 'JSON 控制文字封面的 background、color、borderColor、radius、showName、showAuthor。', 'textarea', 'live'),
      f('theme.useDefaultCover', '使用默认封面', '封面缺失时优先使用默认封面。', 'boolean', 'live'),
      f('theme.defaultCover', '日间默认封面', '默认封面图片路径或 URL。', 'text', 'live'),
      f('theme.defaultCoverDark', '夜间默认封面', '夜间默认封面图片路径或 URL。', 'text', 'live'),
      f('theme.coverShowName', '封面显示书名', '默认封面中显示书名。', 'boolean', 'live'),
      f('theme.coverShowAuthor', '封面显示作者', '默认封面中显示作者。', 'boolean', 'live'),
      f('theme.coverShowNameN', '夜间封面显示书名', '夜间默认封面中显示书名。', 'boolean', 'live'),
      f('theme.coverShowAuthorN', '夜间封面显示作者', '夜间默认封面中显示作者。', 'boolean', 'live'),
      f('welcome.welcomeShowTime', '欢迎页时长', '欢迎页自动关闭的毫秒数，0 表示手动关闭。', 'number', 'live', undefined, 0, 10000, 50),
      f('welcome.customWelcome', '自定义欢迎页', '启动 Web 界面时显示欢迎页。', 'boolean', 'live'),
      f('welcome.welcomeImagePath', '日间欢迎图', '日间欢迎页背景图片路径或 URL。', 'text', 'live'),
      f('welcome.welcomeShowText', '日间显示文字', '日间欢迎页显示 Legado 名称。', 'boolean', 'live'),
      f('welcome.welcomeShowIcon', '日间显示图标', '日间欢迎页显示 Legado 标记。', 'boolean', 'live'),
      f('welcome.welcomeImagePathDark', '夜间欢迎图', '夜间欢迎页背景图片路径或 URL。', 'text', 'live'),
      f('welcome.welcomeShowTextDark', '夜间显示文字', '夜间欢迎页显示 Legado 名称。', 'boolean', 'live'),
      f('welcome.welcomeShowIconDark', '夜间显示图标', '夜间欢迎页显示 Legado 标记。', 'boolean', 'live'),
    ],
  },
  {
    id: 'read',
    title: '阅读体验',
    subtitle: '阅读页排版、翻页、触控和预加载相关设置。',
    icon: Reading,
    fields: [
      f('read.screenOrientation', '屏幕方向', '阅读页尝试锁定方向；浏览器不支持时自动跟随系统。', 'select', 'live', selectOptions.screenOrientation),
      f('read.keep_light', '保持亮屏', '阅读页使用浏览器 Wake Lock 保持亮屏，按时长自动释放。', 'select', 'live', selectOptions.keepLight),
      f('read.hideStatusBar', '隐藏状态栏', '阅读区首次点击时请求浏览器全屏。', 'boolean', 'live'),
      f('read.hideNavigationBar', '隐藏导航栏', '阅读区首次点击时请求浏览器全屏。', 'boolean', 'live'),
      f('read.readBodyToLh', '正文按行高排版', '段落最小高度按当前行高对齐，并避免跨栏断裂。', 'boolean', 'live'),
      f('read.paddingDisplayCutouts', '避开挖孔屏', '阅读页使用设备安全区边距避开刘海与挖孔。', 'boolean', 'live'),
      f('read.doubleHorizontalPage', '横屏双页', '宽屏阅读区按配置启用双栏排版。', 'select', 'live', selectOptions.doublePage),
      f('read.progressBarBehavior', '进度条行为', '阅读页顶部进度条按页、章节或全书计算。', 'select', 'live', selectOptions.progressBar),
      f('read.useZhLayout', '中文排版优化', '启用中文排版规则。', 'boolean', 'live'),
      f('read.textFullJustify', '两端对齐', '正文段落两端对齐。', 'boolean', 'live'),
      f('read.textBottomJustify', '底部对齐', '段末文字使用末行两端对齐。', 'boolean', 'live'),
      f('read.adaptSpecialStyle', '适配特殊样式', '控制正文中源站内联样式是否保留。', 'boolean', 'live'),
      f('read.mouseWheelPage', '鼠标滚轮翻页', 'Web/桌面阅读器可直接使用。', 'boolean', 'live'),
      f('read.volumeKeyPage', '音量键翻页', '支持的浏览器/硬件将音量键映射为上下翻页。', 'boolean', 'live'),
      f('read.volumeKeyPageOnPlay', '朗读时音量键翻页', '开启后朗读播放期间仍允许音量键翻页；关闭时保留系统音量行为。', 'boolean', 'live'),
      f('read.keyPageOnLongPress', '长按按键翻页', '键盘或遥控器按键重复事件连续翻页。', 'boolean', 'live'),
      f('read.pageTouchSlop', '触控容差', '移动距离超过阈值时不触发九宫格点击，减少误触。', 'number', 'live', undefined, 0, 60),
      f('read.pageTouchClick', '点击触发阈值', '限制连续点击触发间隔，数值越高越不易重复翻页。', 'number', 'live', undefined, 0, 60),
      f('read.autoChangeSource', '自动换源', '正文获取失败时自动切换到首个匹配的可用书源。', 'boolean', 'live'),
      f('read.selectText', '允许选择文字', '正文是否允许选择。', 'boolean', 'live'),
      f('read.expandTextMenu', '展开文本菜单', '选中正文后直接显示复制、搜索和朗读工具。', 'boolean', 'live'),
      f('read.optimizeRender', '优化长章节渲染', '使用浏览器延迟渲染屏幕外章节，减少长内容布局开销。', 'boolean', 'live'),
      f('read.showBrightnessView', '显示亮度条', '阅读页显示可持久化的亮度遮罩控制。', 'boolean', 'live'),
      f('read.noAnimScrollPage', '滚动页无动画', '滚动阅读关闭翻页动画。', 'boolean', 'live'),
      f('read.clickImgWay', '图片点击行为', '点击正文图片后的动作。', 'select', 'live', selectOptions.clickImage),
      f('read.readUrlInBrowser', '链接用浏览器打开', '阅读正文链接交给浏览器打开。', 'boolean', 'live'),
      f('read.brightness', '日间阅读亮度', '日间模式阅读层亮度百分比。', 'number', 'live', undefined, 0, 100),
      f('read.nightBrightness', '夜间阅读亮度', '夜间模式阅读层亮度百分比。', 'number', 'live', undefined, 0, 100),
      f('read.brightnessVwPos', '亮度条位置', '关闭位于左侧，开启位于右侧。', 'boolean', 'live'),
      f('read.prevKeyCodes', '上一页按键码', '支持 KeyboardEvent code、key 或数字键码，以逗号或空格分隔。', 'text', 'live'),
      f('read.nextKeyCodes', '下一页按键码', '支持 KeyboardEvent code、key 或数字键码，以逗号或空格分隔。', 'text', 'live'),
      f('read.disableReturnKey', '禁用回车键', '阅读器捕获回车键，避免页面或控件误触发。', 'boolean', 'live'),
      f('read.clickActionTopLeft', '点击区 左上', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionTopCenter', '点击区 上中', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionTopRight', '点击区 右上', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionMiddleLeft', '点击区 左中', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionMiddleCenter', '点击区 中心', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionMiddleRight', '点击区 右中', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionBottomLeft', '点击区 左下', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionBottomCenter', '点击区 下中', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.clickActionBottomRight', '点击区 右下', '九宫格点击动作。', 'select', 'live', selectOptions.clickAction),
      f('read.preDownloadNum', '预下载章节数', '在内存中预取后续章节内容。', 'number', 'live', undefined, 0, 100),
      f('read.autoReadSpeed', '自动阅读速度', '阅读工具栏自动滚动的每秒像素数。', 'number', 'live', undefined, 1, 60),
      f('read.shareLayout', '共享阅读布局', '开启后漫画与文字章节使用同一个阅读样式索引。', 'boolean', 'live'),
      f('read.readStyleSelect', '文字阅读样式', '选择“阅读样式”工作台中的文字样式索引。', 'number', 'live', undefined, 0, 20),
      f('read.comicStyleSelect', '漫画阅读样式', '未共享布局时选择漫画章节样式索引。', 'number', 'live', undefined, 0, 20),
      f('read.system_typefaces', '系统字体族', '将系统字体偏好映射为 Web 字体族。', 'number', 'live', undefined, 0, 5),
      f('read.chineseConverterType', '中文转换', '使用 OpenCC 转换阅读正文和章节标题，不改动原始内容。', 'select', 'live', selectOptions.chineseConverter),
      f('read.contentReadAloudMod', '选中文本朗读', '正文选中后立即朗读或显示朗读按钮。', 'select', 'live', selectOptions.contentSelectSpeakMod),
      f('read.showReadTitleAddition', '标题栏附加信息', '阅读标题栏显示来源/状态。', 'boolean', 'live'),
      f('read.readBarStyleFollowPage', '阅读栏跟随页面', '工具栏颜色跟随阅读页。', 'boolean', 'live'),
    ],
  },
  {
    id: 'aloud',
    title: '朗读与 TTS',
    subtitle: '系统 TTS、HTTP TTS、媒体按键和音频焦点相关设置。',
    icon: VideoPlay,
    fields: [
      f('aloud.ignoreAudioFocus', '忽略音频焦点', '开启后允许多个 Web 媒体或朗读并行播放；关闭时新播放会暂停其它媒体。', 'boolean', 'live'),
      f('aloud.pauseReadAloudWhilePhoneCalls', '中断时暂停朗读', '页面因通话、切换应用或锁屏进入后台时暂停朗读，返回后自动恢复。', 'boolean', 'live'),
      f('aloud.readAloudWakeLock', '朗读保持唤醒', '朗读期间通过浏览器 Wake Lock 保持屏幕唤醒。', 'boolean', 'live'),
      f('aloud.audioPlayWakeLock', '音频播放保持唤醒', '音频或视频播放期间通过浏览器 Wake Lock 保持屏幕唤醒。', 'boolean', 'live'),
      f('aloud.mediaButtonPerNext', '媒体键切换章节', '开启时上一首/下一首切换章节，关闭时切换朗读段落。', 'boolean', 'live'),
      f('aloud.mediaButtonOnExit', '退出后保留媒体键', '离开阅读页后保留当前朗读和 Media Session 控制；关闭时退出即清理。', 'boolean', 'live'),
      f('aloud.readAloudByMediaButton', '媒体键启动朗读', '允许耳机或系统播放键在阅读页启动浏览器朗读。', 'boolean', 'live'),
      f('aloud.readAloudByPage', '按页朗读', '朗读按钮只读取当前视口内的正文。', 'boolean', 'live'),
      f('aloud.streamReadAloudAudio', '流式分段朗读', '按正文段落逐段送入系统语音，关闭时合并为单个朗读任务。', 'boolean', 'live'),
      f('aloud.ttsFollowSys', '跟随系统语速', '使用浏览器系统语音的默认语速。', 'boolean', 'live'),
      f('aloud.ttsSpeechRate', '朗读语速', '关闭跟随系统后映射为浏览器朗读语速。', 'number', 'live', undefined, 1, 10),
      f('aloud.ttsTimer', '定时停止', '朗读达到指定分钟数后自动停止。', 'number', 'live', undefined, 0, 240),
      f('aloud.ttsEngine', 'TTS 引擎', '按名称、URI 或语言匹配浏览器系统语音；填写 http:<HTTP TTS ID> 使用服务端音频朗读。', 'text', 'live'),
    ],
  },
  {
    id: 'backup',
    title: '备份与 WebDAV',
    subtitle: 'WebDAV、同步进度、导出格式和备份策略。',
    icon: Files,
    fields: [
      f('backup.web_dav_url', 'WebDAV 地址', '连通性测试和远端备份使用的 WebDAV 服务 URL。', 'text', 'live'),
      f('backup.web_dav_account', 'WebDAV 账号', 'WebDAV Basic Auth 登录账号。', 'text', 'live'),
      f('backup.web_dav_password', 'WebDAV 密码', 'WebDAV Basic Auth 密码，保存在服务端数据目录。', 'password', 'live'),
      f('backup.webDavDir', 'WebDAV 子目录', '远端备份使用的第一级目录。', 'text', 'live'),
      f('backup.webDavDeviceName', '设备名称', '远端备份使用的设备目录。', 'text', 'live'),
      f('backup.syncBookProgress', '同步阅读进度', '控制阅读进度是否写入服务端共享书架；本浏览器最近阅读不受影响。', 'boolean', 'live'),
      f('backup.syncBookProgressPlus', '增强同步', '阅读滚动时将服务端进度同步间隔从 60 秒缩短到 5 秒。', 'boolean', 'live'),
      f('backup.backupUri', '本地备份路径', 'Linux 服务端备份目录。', 'text', 'live'),
      f('backup.restoreIgnore', '恢复忽略项', '逗号或换行分隔：books、appSettings.theme、appData.readStyles 等恢复忽略路径。', 'textarea', 'live'),
      f('backup.import_old', '旧版数据导入', '导入备份时兼容旧版单数字段名和 JSON 字符串数组。', 'boolean', 'live'),
      f('backup.onlyLatestBackup', '只保留最新备份', '创建本地服务端备份后自动清理同目录旧备份。', 'boolean', 'live'),
      f('backup.autoCheckNewBackup', '自动检查新备份', 'Web 启动时通过 WebDAV PROPFIND 检查远端最新快照并提示。', 'boolean', 'live'),
      f('backup.exportCharset', '导出编码', '阅读页导出 TXT 时使用的 JVM 字符编码。', 'text', 'live'),
      f('backup.exportUseReplace', '导出使用替换规则', '导出正文前应用已启用的正文替换规则。', 'boolean', 'live'),
      f('backup.exportToWebDav', '导出到 WebDAV', '创建本地服务端快照后自动通过 PUT 上传到 WebDAV。', 'boolean', 'live'),
      f('backup.exportNoChapterName', '导出不含章节名', '阅读页导出 TXT 时省略章节标题。', 'boolean', 'live'),
      f('backup.enableCustomExport', '启用自定义导出', '使用安全占位符生成导出文件名。', 'boolean', 'live'),
      f('backup.exportType', '导出类型', 'TXT 与 EPUB 直接生成；自定义沿用 TXT 和文件名模板。', 'select', 'live', selectOptions.exportType),
      f('backup.exportPictureFile', '导出图片文件', 'EPUB 导出时下载并内嵌章节图片，单图与总量均有限额。', 'boolean', 'live'),
      f('backup.parallelExportBook', '并行导出', '控制台批量导出书架时最多并行处理 8 本书。', 'boolean', 'live'),
      f('backup.bookExportFileName', '书籍导出文件名', '支持 {name}、{author}、{date} 占位符。', 'textarea', 'live'),
      f('backup.episodeExportFileName', '分集导出文件名', '章节 ZIP 导出文件名模板，支持 {index}、{title}、{book}、{author}。', 'textarea', 'live'),
      f('backup.bookImportFileName', '本地导入文件名规则', '使用带 name、author 命名组的正则解析文件名。', 'textarea', 'live'),
      f('backup.defaultBookTreeUri', '书籍保存目录', '本地 TXT 文件在 Linux 服务端的保存目录，支持相对数据目录路径。', 'text', 'live'),
      f('backup.localBookImportSort', '本地书导入排序', '控制控制台批量选择 TXT 后的上传顺序。', 'select', 'live', selectOptions.localBookImportSort),
    ],
  },
  {
    id: 'network',
    title: '网络与源',
    subtitle: 'UA、Hosts、线程数、导入策略、换源策略。',
    icon: Connection,
    fields: [
      f('network.userAgent', 'User-Agent', '服务端图片代理、源检查和 WebDAV 探测请求使用的默认 UA。', 'textarea', 'live'),
      f('network.customHosts', '自定义 Hosts', 'JSON 格式域名到 IP 或 IP 数组映射，保存后写入 JVM Hosts 解析文件。', 'textarea', 'live'),
      f('network.localPassword', '本地访问密码', '为数据 API 启用会话认证；浏览器首次访问会要求输入密码，静态页面和健康检查保持公开。', 'password', 'live'),
      f('network.precisionSearch', '精确搜索', '书架搜索仅显示书名或作者完整匹配的结果。', 'boolean', 'live'),
      f('network.Cronet', 'HTTP/2 网络栈', '开启后 WebDAV、源检查和远程图片优先使用 JVM HTTP/2。', 'boolean', 'live'),
      f('network.antiAlias', '抗锯齿', '启用浏览器字体平滑与可读性优化。', 'boolean', 'live'),
      f('network.threadCount', '并发线程数', '源检查并发请求数，服务端限制在 1 到 32。', 'number', 'live', undefined, 1, 128),
      f('network.sourceEditMaxLine', '源编辑最大行数', '限制规则文本框展开高度，超出后在框内滚动，0 为不限。', 'number', 'live', undefined, 0, 10000),
      f('network.checkSource', '检查源配置', '源检查的默认 JSON：scope、onlyEnabled、timeoutMillis、limit。', 'textarea', 'live'),
      f('network.uploadRule', '直链上传规则', 'JSON 配置 uploadUrl、downloadUrlRule、summary、compress，可在此测试。', 'textarea', 'live'),
      f('network.replaceEnableDefault', '默认启用替换规则', '新规则默认启用。', 'boolean', 'live'),
      f('network.importKeepName', '导入保留名称', '同 URL 源导入时保留本地名称。', 'boolean', 'live'),
      f('network.importKeepGroup', '导入保留分组', '同 URL 源导入时保留本地分组。', 'boolean', 'live'),
      f('network.importKeepEnable', '导入保留启用状态', '同 URL 源导入时保留本地启用状态。', 'boolean', 'live'),
      f('network.importShowComment', '导入显示注释', '导入完成后展示源注释摘要。', 'boolean', 'live'),
      f('network.changeSourceCheckAuthor', '换源校验作者', '手动换源候选必须与原书作者匹配。', 'boolean', 'live'),
      f('network.changeSourceLoadInfo', '换源加载详情', '手动换源时读取候选详情页并同步封面、简介、最新章节和目录地址。', 'boolean', 'live'),
      f('network.changeSourceLoadToc', '换源加载目录', '手动换源成功后立即获取并缓存新书源目录。', 'boolean', 'live'),
      f('network.changeSourceLoadWordCount', '换源加载字数', '换源候选额外读取最新章节并显示字数。', 'boolean', 'live'),
      f('network.batchChangeSourceDelay', '批量换源延迟', '书架批量换源时两本书之间的请求间隔。', 'number', 'live', undefined, 0, 30000, 100),
    ],
  },
  {
    id: 'manga',
    title: '漫画与媒体',
    subtitle: '漫画阅读、滚动、墨水屏、灰度和媒体行为。',
    icon: VideoPlay,
    fields: [
      f('manga.showMangaUi', '显示漫画入口', '控制正文图片是否应用漫画阅读配置。', 'boolean', 'live'),
      f('manga.disableMangaScale', '禁用漫画缩放', '漫画图片限制在阅读区内并只允许纵向触控滚动。', 'boolean', 'live'),
      f('manga.disableMangaPageAnim', '禁用漫画翻页动画', '漫画章节和横向翻页立即跳转。', 'boolean', 'live'),
      f('manga.mangaPreDownloadNum', '漫画预下载数', '图片章节使用独立的后续章节预取数量。', 'number', 'live', undefined, 0, 100),
      f('manga.disableClickScroll', '禁用点击滚动', '图片章节忽略九宫格点击翻页。', 'boolean', 'live'),
      f('manga.mangaAutoPageSpeed', '自动翻页速度', '图片章节自动阅读速度。', 'number', 'live', undefined, 1, 10),
      f('manga.enableMangaHorizontalScroll', '水平滚动漫画', '图片章节使用横向滚动与翻页。', 'boolean', 'live'),
      f('manga.hideMangaTitle', '隐藏漫画内标题', '图片占多数的章节隐藏正文标题。', 'boolean', 'live'),
      f('manga.enableMangaEInk', '漫画墨水屏模式', '漫画图片使用灰度高对比滤镜。', 'boolean', 'live'),
      f('manga.mangaEInkThreshold', '墨水屏阈值', '控制墨水屏滤镜的对比强度。', 'number', 'live', undefined, 0, 255),
      f('manga.disableHorizontalPageSnap', '禁用横向吸附', '横向漫画滚动时关闭分页吸附。', 'boolean', 'live'),
      f('manga.enableMangaGray', '漫画灰度', '漫画图片使用灰度滤镜。', 'boolean', 'live'),
      f('manga.mangaColorFilter', '颜色滤镜', '支持 CSS filter 或亮度、对比度等 JSON 配置。', 'textarea', 'live'),
      f('manga.mangaFooterConfig', '页脚配置', '兼容 Android 字段，控制漫画页码、章节、进度和对齐方式。', 'textarea', 'live'),
    ],
  },
  {
    id: 'maintenance',
    title: '维护与高级',
    subtitle: '缓存、日志、升级渠道、调试与系统集成。',
    icon: Setting,
    fields: [
      f('maintenance.bitmapCacheSize', '图片缓存大小', '服务端图片代理的内存缓存预算，0 为关闭缓存。', 'number', 'live', undefined, 0, 2048),
      f('maintenance.imageRetainNum', '图片保留数量', '图片代理 LRU 保留数量上限，0 表示只受缓存大小限制。', 'number', 'live', undefined, 0, 10000),
      f('maintenance.autoClearExpired', '自动清理过期缓存', '图片代理访问缓存时自动淘汰超过 24 小时的条目。', 'boolean', 'live'),
      f('maintenance.showAddToShelfAlert', '加入书架提示', '搜索结果保存到书架前要求确认。', 'boolean', 'live'),
      f('maintenance.updateToVariant', '升级渠道', '默认版检查 GitHub Releases；其它 Android 分发渠道会明确提示其不提供 Linux 服务端包。', 'select', 'live', selectOptions.updateVariant),
      f('maintenance.autoUpdateVariant', '自动检查更新', 'Web 启动时检查已选择渠道的发布信息；有新版本时在控制台提示。', 'boolean', 'live'),
      f('maintenance.recordLog', '记录调试日志', '将服务端请求记录到数据目录 logs/server.log。', 'boolean', 'live'),
      f('maintenance.recordHeapDump', '记录 Heap Dump', '启用 HotSpot OOM 堆转储并写入服务端数据目录。', 'boolean', 'live'),
      f('maintenance.process_text', '文本菜单集成', '阅读正文选中后可直接创建带书籍和章节信息的书签摘录。', 'boolean', 'live'),
      f('maintenance.cleanCache', '维护时清理缓存', '执行维护时清空服务端图片代理内存缓存。', 'boolean', 'live'),
      f('maintenance.clearWebViewData', '清理浏览器数据', '执行维护时清除当前浏览器的本地、会话、缓存存储和可访问 Cookie。', 'boolean', 'live'),
      f('maintenance.shrinkDatabase', '压缩数据存储', '执行维护时原子压缩服务端 JSON 数据文件。', 'boolean', 'live'),
      f('maintenance.cleanCacheOnSchedule', '定时清理缓存', 'Linux 服务端每小时检查，距上次维护满 24 小时后自动清理。', 'boolean', 'live'),
      f('maintenance.videoSetting', '视频播放配置', 'JSON 控制 HTML5 视频的 controls、preload、倍速、音量、循环和静音。', 'textarea', 'live'),
      f('maintenance.editFontScale', '规则编辑字体大小', '书源与订阅规则编辑器字体大小。', 'number', 'live', undefined, 10, 32),
      f('maintenance.editNonPrintable', '不可见字符显示', '规则文本框聚焦时显示空格、Tab 和换行符预览。', 'select', 'live', selectOptions.editNonPrintable),
      f('maintenance.editAutoWrap', '规则编辑自动换行', '控制规则文本框软换行。', 'boolean', 'live'),
      f('maintenance.editAutoComplete', '规则编辑自动补全', '控制浏览器自动完成与拼写检查。', 'boolean', 'live'),
      f('maintenance.showBoardLine', '编辑器边界线', '0 关闭，1 纵向，2 横向，3 同时显示。', 'number', 'live', undefined, 0, 3),
      f('maintenance.lastMaintenanceAt', '上次维护时间', '服务端最近一次完成维护的 ISO 时间。', 'text', 'live'),
    ],
  },
]

const appEntries: AppEntry[] = [
  { label: '书源管理', summary: '导入、编辑和维护书源', icon: Collection, path: '/bookSource' },
  { label: '书签摘录', summary: '管理阅读书签与摘录', icon: DocumentCopy, path: '/features', query: { kind: 'bookmarks' } },
  { label: '阅读记录', summary: '查看本地阅读进度记录', icon: Reading, path: '/features', query: { kind: 'readRecords' } },
  { label: '替换规则', summary: '维护正文替换与测试规则', icon: DocumentCopy, path: '/server', query: { tab: 'replace' } },
  { label: 'TXT 目录规则', summary: '配置 TXT 章节目录解析', icon: Files, path: '/server', query: { tab: 'toc' } },
  { label: '字典规则', summary: '编辑并执行阅读划词字典规则', icon: Search, path: '/features', query: { kind: 'dictRules' } },
  { label: '源检查', summary: '检查书源的可达性与响应', icon: Search, path: '/server', query: { tab: 'sourceCheck' } },
  { label: '主题设置', summary: '调整颜色、模式和阅读主题', icon: Monitor, path: '/settings', query: { section: 'theme' } },
  { label: '主题方案', summary: '管理可复用的主题配置', icon: Setting, path: '/features', query: { kind: 'themeConfigs' } },
  { label: 'HTTP TTS', summary: '维护 HTTP 朗读服务配置', icon: VideoPlay, path: '/features', query: { kind: 'httpTTS' } },
  { label: '备份与 WebDAV', summary: '配置备份、恢复和同步偏好', icon: Connection, path: '/settings', query: { section: 'backup' } },
]
const parityItems = [
  { name: '书架/阅读', state: 'Web 已有基础阅读，设置项继续接入', icon: Reading },
  { name: '书源/RSS 源', state: '管理 UI 已有，规则执行引擎待抽 JVM core', icon: Collection },
  { name: 'TXT/替换规则', state: 'Web 已可管理并被服务端使用', icon: Files },
  { name: 'App 数据工作台', state: '分组、书签、TTS、Cookie、字典规则等可在 Web 端管理并执行', icon: SetUp },
  { name: '备份/同步', state: '本地快照可用，WebDAV 协议继续接入', icon: Connection },
  { name: 'TTS/媒体键', state: '浏览器系统朗读与 HTTP TTS 音频代理均可用', icon: VideoPlay },
  { name: '漫画/图片处理', state: '配置保留，图像管线待服务化', icon: Monitor },
]

const settings = ref<AppSettings>({})
const router = useRouter()
const route = useRoute()
const activeSection = ref(resolveSettingSection(route.query.section))
const loading = ref(false)
const saving = ref(false)
const testingWebDav = ref(false)
const testingUploadRule = ref(false)
const runningMaintenance = ref(false)
const dirty = ref(false)
const settingsInput = ref<HTMLInputElement>()

const currentSection = computed(
  () => sections.find(section => section.id === activeSection.value) || sections[0],
)

function f(
  path: string,
  label: string,
  summary: string,
  type: FieldType,
  status: FieldStatus,
  options?: FieldOption[],
  min?: number,
  max?: number,
  step?: number,
): SettingField {
  return { path, label, summary, type, status, options, min, max, step }
}

watch(
  () => route.query.section,
  value => {
    const nextSection = resolveSettingSection(value)
    if (nextSection !== activeSection.value) activeSection.value = nextSection
  },
)

watch(activeSection, section => {
  if (route.query.section !== section) {
    void router.replace({ query: { ...route.query, section } })
  }
})

function resolveSettingSection(value: unknown) {
  const section = typeof value === 'string' ? value : ''
  return sections.some(item => item.id === section) ? section : 'main'
}

function openAppEntry(entry: AppEntry) {
  void router.push({ path: entry.path, query: entry.query })
}
function getField(path: string): unknown {
  const [group, key] = path.split('.')
  return settings.value[group]?.[key]
}

function writeField(path: string, value: unknown) {
  const [group, key] = path.split('.')
  settings.value[group] ||= {}
  settings.value[group][key] = value
  if (
    path === 'main.themeMode' ||
    path === 'main.language' ||
    path === 'maintenance.videoSetting' ||
    path.startsWith('theme.')
  ) {
    applyAppSettings(settings.value)
  }
  dirty.value = true
}

function stringValue(path: string) {
  const value = getField(path)
  return value == null ? '' : String(value)
}

function numberValue(path: string) {
  const value = getField(path)
  return typeof value === 'number' ? value : Number(value || 0)
}

function booleanValue(path: string) {
  return Boolean(getField(path))
}

function fieldStatusLabel(status: FieldStatus) {
  return status === 'live'
    ? 'Web 可用'
    : status === 'stored'
      ? '配置保留'
      : 'Linux 需实现'
}

function fieldStatusType(status: FieldStatus) {
  return status === 'live' ? 'success' : status === 'stored' ? 'info' : 'warning'
}

async function loadSettings() {
  loading.value = true
  try {
    settings.value = unwrap(await API.getAppSettings())
    applyAppSettings(settings.value)
    dirty.value = false
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '加载设置失败')
  } finally {
    loading.value = false
  }
}

async function saveSettings() {
  saving.value = true
  try {
    const localPassword = String(settings.value.network?.localPassword || '')
    settings.value = unwrap(await API.saveAppSettings(settings.value))
    if (localPassword) setLocalServerToken(unwrap(await API.authenticate(localPassword)).token)
    else setLocalServerToken('')
    applyAppSettings(settings.value)
    dirty.value = false
    ElMessage.success('设置已保存')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存设置失败')
  } finally {
    saving.value = false
  }
}

async function testWebDavConnection() {
  if (dirty.value) {
    ElMessage.warning('请先保存 WebDAV 配置，再进行连通性测试')
    return
  }
  testingWebDav.value = true
  try {
    const result = unwrap(await API.testWebDav())
    if (result.ok) {
      ElMessage.success(`WebDAV 连接成功${result.statusCode ? `（HTTP ${result.statusCode}）` : ''}`)
    } else {
      ElMessage.error(result.message || 'WebDAV 连接失败')
    }
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : 'WebDAV 测试失败')
  } finally {
    testingWebDav.value = false
  }
}

async function testDirectUploadRule() {
  if (dirty.value) {
    ElMessage.warning('请先保存直链上传规则，再进行测试')
    return
  }
  testingUploadRule.value = true
  try {
    const result = unwrap(await API.testUploadRule())
    await navigator.clipboard?.writeText(result.downloadUrl)
    ElMessage.success(`上传成功，下载链接已复制${result.statusCode ? `（HTTP ${result.statusCode}）` : ''}`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '直链上传测试失败')
  } finally {
    testingUploadRule.value = false
  }
}

async function runMaintenance() {
  if (dirty.value) {
    ElMessage.warning('请先保存维护配置')
    return
  }
  runningMaintenance.value = true
  try {
    const result = unwrap(await API.runMaintenance())
    settings.value.maintenance ||= {}
    settings.value.maintenance.lastMaintenanceAt = result.completedAt
    let browserEntriesCleared = 0
    if (settings.value.maintenance.clearWebViewData === true) {
      browserEntriesCleared += localStorage.length + sessionStorage.length
      localStorage.clear()
      sessionStorage.clear()
      if ('caches' in window) {
        const cacheNames = await caches.keys()
        browserEntriesCleared += cacheNames.length
        await Promise.all(cacheNames.map(name => caches.delete(name)))
      }
      const cookies = document.cookie.split(';').map(item => item.split('=')[0]?.trim()).filter(Boolean)
      browserEntriesCleared += cookies.length
      cookies.forEach(name => {
        document.cookie = `${name}=; Max-Age=0; path=/; SameSite=Lax`
      })
    }
    const compactSummary = result.jsonFilesCompacted > 0
      ? `，压缩 ${result.jsonFilesCompacted} 个数据文件，节省 ${result.jsonBytesSaved} 字节`
      : ''
    const browserSummary = browserEntriesCleared > 0 ? `，清除 ${browserEntriesCleared} 项浏览器数据` : ''
    ElMessage.success(`维护完成，清理 ${result.cacheEntriesCleared} 项图片缓存${compactSummary}${browserSummary}`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '维护失败')
  } finally {
    runningMaintenance.value = false
  }
}
async function resetSettings() {
  try {
    await ElMessageBox.confirm('恢复默认设置会覆盖当前设置中心配置。', '恢复默认', {
      type: 'warning',
      confirmButtonText: '恢复',
      cancelButtonText: '取消',
    })
    settings.value = unwrap(await API.resetAppSettings())
    applyAppSettings(settings.value)
    dirty.value = false
    ElMessage.success('已恢复默认设置')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '恢复默认失败')
    }
  }
}

function exportSettings() {
  const blob = new Blob([JSON.stringify(settings.value, null, 2)], {
    type: 'application/json;charset=utf-8',
  })
  const url = URL.createObjectURL(blob)
  const anchor = document.createElement('a')
  anchor.href = url
  anchor.download = `legado-app-settings-${new Date().toISOString()}.json`.replace(
    /[:.]/g,
    '-',
  )
  anchor.click()
  URL.revokeObjectURL(url)
}

async function importSettings(event: Event) {
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  if (!file) return
  try {
    const data = JSON.parse(await file.text()) as AppSettings
    settings.value = unwrap(await API.saveAppSettings(data))
    applyAppSettings(settings.value)
    dirty.value = false
    ElMessage.success('设置已导入')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '导入设置失败')
  } finally {
    input.value = ''
  }
}

onMounted(() => {
  document.title = '设置中心'
  loadSettings()
})
</script>

<style scoped lang="scss">
.settings-center {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 300px minmax(0, 1fr);
  background: #f5f7fa;
  color: #1f2937;
}

.settings-sidebar {
  position: sticky;
  top: 0;
  height: 100vh;
  padding: 28px 22px;
  box-sizing: border-box;
  background: #101820;
  color: #e7edf3;
  display: flex;
  flex-direction: column;
  gap: 22px;
  overflow: auto;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;

  h1 {
    margin: 0;
    font-size: 20px;
    line-height: 1.2;
    font-weight: 700;
  }

  p {
    margin: 4px 0 0;
    font-size: 12px;
    color: #94a3b8;
  }
}

.brand-mark {
  width: 40px;
  height: 40px;
  display: grid;
  place-items: center;
  border-radius: 8px;
  background: #1aa37a;
  color: #fff;
  font-weight: 800;
}

.main-nav,
.section-list {
  display: grid;
  gap: 8px;
}

.nav-item,
.section-list button {
  min-height: 42px;
  padding: 0 12px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.nav-item {
  color: #cbd5e1;
  text-decoration: none;

  &:hover,
  &.router-link-active {
    color: #fff;
    background: rgba(255, 255, 255, 0.1);
  }
}

.section-list {
  padding-top: 12px;
  border-top: 1px solid rgba(148, 163, 184, 0.18);

  button {
    border: 0;
    background: transparent;
    color: #94a3b8;
    text-align: left;
    cursor: pointer;

    &.active,
    &:hover {
      color: #fff;
      background: rgba(255, 255, 255, 0.1);
    }
  }
}

.nav-item svg,
.section-list svg {
  width: 18px;
  height: 18px;
}

.settings-main {
  min-width: 0;
  padding: 30px;
}

.settings-header,
.settings-panel,
.parity-panel {
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 18px 42px rgba(15, 23, 42, 0.06);
}

.settings-header {
  min-height: 96px;
  padding: 22px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  h2 {
    margin: 0;
    font-size: 26px;
    line-height: 1.2;
    color: #111827;
  }

  p {
    margin: 8px 0 0;
    color: #64748b;
  }
}

.header-actions {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 10px;
}

.settings-layout {
  margin-top: 18px;
  display: grid;
  grid-template-columns: minmax(0, 1fr) 320px;
  gap: 18px;
  align-items: start;
}

.settings-panel,
.parity-panel {
  padding: 20px;
}

.panel-heading {
  display: flex;
  align-items: center;
  gap: 14px;
  padding-bottom: 18px;
  border-bottom: 1px solid #edf1f5;

  h3 {
    margin: 0;
    font-size: 20px;
    color: #111827;
  }

  p {
    margin: 5px 0 0;
    font-size: 13px;
    color: #64748b;
  }
}

.panel-icon {
  width: 42px;
  height: 42px;
  border-radius: 8px;
  display: grid;
  place-items: center;
  background: #e7f7ef;
  color: #12805c;

  svg {
    width: 22px;
    height: 22px;
  }
}

.setting-rows {
  display: grid;
}

.setting-row {
  min-height: 82px;
  padding: 16px 0;
  border-bottom: 1px solid #edf1f5;
  display: grid;
  grid-template-columns: minmax(260px, 1fr) minmax(240px, 360px);
  gap: 24px;
  align-items: center;

  &:last-child {
    border-bottom: 0;
  }
}

.setting-copy {
  min-width: 0;

  span {
    display: block;
    margin-top: 5px;
    color: #64748b;
    font-size: 13px;
    line-height: 1.5;
  }

  code {
    display: inline-block;
    margin-top: 7px;
    padding: 2px 6px;
    border-radius: 4px;
    background: #f1f5f9;
    color: #475569;
    font-size: 12px;
  }
}

.setting-title-line {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;

  strong {
    font-size: 15px;
    color: #111827;
  }
}

.setting-control {
  min-width: 0;

  .el-select,
  .el-input,
  .el-input-number {
    width: 100%;
  }
}

.parity-panel {
  position: sticky;
  top: 24px;

  h3 {
    margin: 0;
    font-size: 18px;
    color: #111827;
  }

  p {
    margin: 8px 0 18px;
    font-size: 13px;
    line-height: 1.55;
    color: #64748b;
  }
}

.parity-list {
  display: grid;
  gap: 10px;
  margin-bottom: 14px;
}

.parity-row {
  display: grid;
  grid-template-columns: 22px minmax(0, 1fr);
  gap: 10px;
  align-items: start;
  padding: 11px;
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #fbfdff;

  svg {
    width: 20px;
    height: 20px;
    color: #0f766e;
  }

  strong {
    display: block;
    color: #111827;
    font-size: 13px;
  }

  span {
    display: block;
    margin-top: 3px;
    color: #64748b;
    font-size: 12px;
    line-height: 1.45;
  }
}

.shortcut-heading {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  gap: 10px;
  margin: 18px 0 10px;

  h4 {
    margin: 0;
    font-size: 13px;
    color: #334155;
  }

  span {
    color: #94a3b8;
    font-size: 11px;
  }
}

.shortcut-list {
  display: grid;
  gap: 7px;
  margin-bottom: 16px;

  button {
    width: 100%;
    min-width: 0;
    padding: 9px 10px;
    border: 1px solid #dfe7ef;
    border-radius: 8px;
    background: #fff;
    color: #334155;
    display: grid;
    grid-template-columns: 18px minmax(0, 1fr);
    gap: 9px;
    align-items: center;
    text-align: left;
    cursor: pointer;
    transition: border-color 0.16s ease, background 0.16s ease, color 0.16s ease;

    &:hover,
    &:focus-visible {
      border-color: #38a169;
      background: #f0fdf4;
      color: #166534;
      outline: none;
    }
  }

  svg {
    width: 18px;
    height: 18px;
    color: #0f766e;
  }

  strong,
  small {
    display: block;
  }

  strong {
    color: #1e293b;
    font-size: 12px;
    line-height: 1.35;
  }

  small {
    margin-top: 2px;
    color: #64748b;
    font-size: 11px;
    line-height: 1.35;
  }
}
.visually-hidden {
  position: fixed;
  width: 1px;
  height: 1px;
  opacity: 0;
  pointer-events: none;
}

@media (max-width: 1180px) {
  .settings-center {
    grid-template-columns: 1fr;
  }

  .settings-sidebar {
    position: static;
    height: auto;
  }

  .main-nav,
  .section-list {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }

  .settings-layout {
    grid-template-columns: 1fr;
  }

  .parity-panel {
    position: static;
  }
}

@media (max-width: 760px) {
  .settings-main,
  .settings-sidebar {
    padding: 18px;
  }

  .settings-header {
    align-items: stretch;
    flex-direction: column;
  }

  .header-actions {
    justify-content: flex-start;
  }

  .main-nav,
  .section-list {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .section-list {
    max-height: 236px;
    overflow: auto;
  }

  .setting-row {
    grid-template-columns: 1fr;
  }

  .setting-row {
    gap: 12px;
  }
}
</style>
