import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		getEnum({
			commit,
			state,
		},keyword) {
			return new Promise((resolve, reject) => {
				request("enum/options", {keyword}, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
}

export default user
