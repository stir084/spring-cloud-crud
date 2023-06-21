const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    proxy: {
      '/posts': { // 추후에 api/v1 작업할 필요 있음
        target: 'http://localhost:8080', // 백엔드 API 서버의 주소
        changeOrigin: true,
        // pathRewrite: {
        //   '^/posts': '' // 프록시 경로 수정 (옵션)
        // }
      },
      '/users': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  },
  transpileDependencies: [
    'vuetify'
  ]
})
