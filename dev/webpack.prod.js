// 生产构建：产出可直接替换 src/main/resources/web/ 的静态资源
//
// 用法：
//   node node_modules/webpack/bin/webpack.js --config webpack.prod.js
//
// 输出到 ../src/main/resources/web/（覆盖原版产物），
// 使 docker 镜像里的前端等于本仓库 dev/ 源码构建的产物。
const path = require('path')
const { VueLoaderPlugin } = require('vue-loader')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const CopyPlugin = require('copy-webpack-plugin')

const OUT = path.resolve(__dirname, '../src/main/resources/web')

module.exports = {
  mode: 'production',
  entry: path.resolve(__dirname, 'src/main.js'),
  output: {
    path: OUT,
    // 与原版命名风格一致：app.<hash>.js，便于对照
    filename: 'js/[name].[contenthash:8].js',
    chunkFilename: 'js/[name].[contenthash:8].js',
    publicPath: '/',
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      // 生产用 runtime 版（不含模板编译器），体积更小
      vue$: 'vue/dist/vue.runtime.esm.js',
      '@': path.resolve(__dirname, 'src'),
    },
  },
  module: {
    rules: [
      { test: /\.vue$/, loader: 'vue-loader' },
      {
        test: /\.css$/,
        use: [
          // 生产抽成独立 css 文件
          require('mini-css-extract-plugin').loader,
          'css-loader',
        ],
      },
      {
        test: /\.(png|jpe?g|gif|svg|woff2?|ttf|eot)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8192,
              name: 'fonts/[name].[hash:8].[ext]',
              esModule: false,
            },
          },
        ],
      },
    ],
  },
  plugins: [
    new VueLoaderPlugin(),
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'public/index.html'),
      filename: 'index.html',
      inject: true,
      minify: {
        removeComments: true,
        collapseWhitespace: true,
        removeAttributeQuotes: false,
      },
    }),
    new (require('mini-css-extract-plugin'))({
      filename: 'css/[name].[contenthash:8].css',
    }),
    new CleanWebpackPlugin({
      // 只删本工程产出的东西，保留原版的 simple-web / bookSourceDebug / img / fonts
      // 只清本工程产出的：main/chunk-vendors/数字分块 + css
      // 保留原版的第三方库（dash/flv/hls/pear-player/webtorrent）与 simple-web 等
      cleanOnceBeforeBuildPatterns: [
        'js/main.*.js',
        'js/chunk-vendors.*.js',
        'js/[0-9]*.js',
        'css/main.*.css',
        'css/chunk-vendors.*.css',
        'index.html',
      ],
      dangerouslyAllowCleanPatternsOutsideProject: false,
    }),
    new CopyPlugin([
      // 保留原版的静态资源目录（不存在的路径 v5 会警告，故只列确实存在的）
      { from: path.resolve(__dirname, '../src/main/resources/web/simple-web'), to: 'simple-web' },
      { from: path.resolve(__dirname, '../src/main/resources/web/bookSourceDebug'), to: 'bookSourceDebug' },
      { from: path.resolve(__dirname, '../src/main/resources/web/img'), to: 'img' },
      { from: path.resolve(__dirname, '../src/main/resources/web/fonts'), to: 'fonts' },
      { from: path.resolve(__dirname, '../src/main/resources/web/favicon.ico'), to: 'favicon.ico' },
      { from: path.resolve(__dirname, '../src/main/resources/web/manifest.json'), to: 'manifest.json' },
      { from: path.resolve(__dirname, '../src/main/resources/web/service-worker.js'), to: 'service-worker.js' },
      { from: path.resolve(__dirname, '../src/main/resources/web/browsertest.html'), to: 'browsertest.html' },
    ])
  ],
  optimization: {
    splitChunks: {
      cacheGroups: {
        vendors: {
          test: /[\\/]node_modules[\\/]/,
          name: 'chunk-vendors',
          chunks: 'all',
          priority: -10,
        },
      },
    },
    runtimeChunk: false,
  },
  performance: { hints: false },
  stats: 'errors-warnings',
}
