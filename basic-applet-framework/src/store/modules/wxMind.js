import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		getAllList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxMind/allList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getMindIntro({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxMind/mindIntro", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user