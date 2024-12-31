import request from '@/utils/request.js'
const user = {
	namespaced: true,
	state: {

	},

	mutations: {

	},

	actions: {
		commitWxArticle({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/commitWxArticle", data, "POST").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getCounselorArticles({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/counselorArticles", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		deleteById({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/deleteById", data, "DELETE").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getHotWxArticleList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/hotWxArticleList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getArticleDetail({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/articleDetail", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
		getAllList({
			commit,
			state,
		}, data) {
			return new Promise((resolve, reject) => {
				request("wxArticle/allList", data, "GET").then(response => {
					resolve(response)
				}).catch(error => {
					reject(error)
				})
			})
		},
	}
}

export default user