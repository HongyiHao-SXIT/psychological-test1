import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {
		userInfo: uni.getStorageSync("userInfo") || {}

	},

	mutations: {
		SET_INFO: (state, userInfo) => {
			state.userInfo = userInfo
			uni.setStorageSync("userInfo", userInfo)
		}
	},

	actions: {
		// 获取openid
		getOpenId({
			commit,
			state
		},{base64Appid,code}) {
			return new Promise((resolve, reject) => {
				request('wx/user/' + base64Appid + "/login", {
					 code: code
				}, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		// 微信登录
		wxLogin({
			commit,
			state
		},data) {
			return new Promise((resolve, reject) => {
				request('wx/user/' + data.base64Appid + "/wxLogin", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		// 获取用户信息
		GetInfo({
			commit,
			state
		}) {
			return new Promise((resolve, reject) => {
				request("wxUser/userInfo", {}, "GET").then(response => {
					console.log("asdasdasdas", response)
					commit('SET_INFO', response)
					// commit('SET_AVATAR', data.icon)
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		editUserInfo({
			commit,
			state
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxUser/editUser", data, "PUT").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		register({
			commit,
			state
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxUser/register", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		login({
			commit,
			state
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxUser/login", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user
