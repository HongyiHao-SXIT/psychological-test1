import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		isCounselor({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxCounselor/isCounselor", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		commitCounselor({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxCounselor/commitCounselor", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getWxCounselorList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxCounselor/wxAppointmentList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getCounselorById({
			commit,
			state,
		}, id) {
			return new Promise((resolve, reject) => {
				request("wxCounselor/" + id, {}, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user