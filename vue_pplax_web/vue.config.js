module.exports = {
	publicPath: process.env.BASE_URL,
	devServer: {
		port: process.env.PORT,
		proxy: {
			// 代理配置
			['/api']: {
				// 目标服务器地址
				target: process.env.VUE_APP_SERVER,
				// // 将 /api 替换为空字符串，以便匹配到后续路径
				// pathRewrite: {'^/api': ''},
				// 必须设置为 true，否则代理将不会生效
				changeOrigin: true,
				// 只代理以 /api 开头的请求
				secure: false,
			}
		}
	}
}