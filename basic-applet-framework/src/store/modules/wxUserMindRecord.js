import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		saveWxUserMindRecord({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxUserMindRecord/saveWxUserMindRecord", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getById({
			commit,
			state,
		}, id) {
			return new Promise((resolve, reject) => {
				request("wxUserMindRecord/" + id, {}, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getMindRecordByUserId({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxUserMindRecord/mindRecordByUserId", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user