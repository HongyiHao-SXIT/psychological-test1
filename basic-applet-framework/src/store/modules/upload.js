import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		upload({
			commit,
			state,
		}, file) {
			return new Promise((resolve, reject) => {
				request("upload/file/upload", file, "UPLOAD").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		}
	}
}

export default user
