const path = require('path')
const { VueLoaderPlugin } = require('vue-loader')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
  mode: 'development',
  entry: path.resolve(__dirname, 'src/main.js'),
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'app.js',
    publicPath: '/',
  },
  resolve: {
    extensions: ['.js', '.vue', '.json'],
    alias: {
      vue$: 'vue/dist/vue.runtime.esm.js',
      '@': path.resolve(__dirname, 'src'),
    },
  },
  module: {
    rules: [
      { test: /\.vue$/, loader: 'vue-loader' },
      { test: /\.css$/, use: ['style-loader', 'css-loader'] },
      { test: /\.(png|jpe?g|gif|svg|woff2?|ttf|eot)$/, use: [{ loader: 'url-loader', options: { limit: 8192 } }] },
    ],
  },
  plugins: [
    new VueLoaderPlugin(),
    new HtmlWebpackPlugin({ template: path.resolve(__dirname, 'public/index.html') }),
  ],
  devServer: {
    port: 8930,
    host: '0.0.0.0',
    disableHostCheck: true,
    hot: true,
    historyApiFallback: true,
    proxy: {
      '/reader3': { target: 'http://127.0.0.1:4400', changeOrigin: true },
      '/assets': { target: 'http://127.0.0.1:4400', changeOrigin: true },
    },
  },
  devtool: 'eval-cheap-module-source-map',
}
