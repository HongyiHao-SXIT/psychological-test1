import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		getWxMindItemListByMindId({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxMindItem/wxMindItemListByMindId", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
}

export default user