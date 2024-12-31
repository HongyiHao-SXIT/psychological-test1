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
				request("wxBanner/allList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
}

export default user