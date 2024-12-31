import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		getDisableDateList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxAppointment/disableDateList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		commitAppointment({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxAppointment/commitAppointment", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getUserAppointmentList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxAppointment/userAppointmentList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		saveOrUpdate({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxAppointment/saveOrUpdate", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getCounselorAppointmentList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxAppointment/counselorAppointmentList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user