module.exports = {
	publicPath: process.env.BASE_URL,
	devServer: {
	  port: process.env.PORT,
	  proxy: process.env.VUE_APP_SERVER
	}
  };