// 此专门用来作为请求转发
'use strict';
const rp = require("request-promise")
const db = uniCloud.database() // 连接数据库
const fs = require('fs')
const request =require("request")
exports.main = async (event, context) => {
	//event为客户端上传的参数
	console.log('event : ', event)
	let option = {
		uri: event.url,
		body: event.data,
		formData: event.formData,
		method: event.method,
		headers: event.header,
		json: true,
	}
	if (event.method === 'GET') {
		let json = event.data
		// 将参数拼接到url后面
		option.uri = event.url + '?' + Object.keys(json).map(function(key) {
			// body...
			return encodeURIComponent(key) + "=" + encodeURIComponent(json[key]);
		}).join("&");
		event.body = {}
	} else if (event.method === 'POST' || event.method === 'DELETE' || event.method === 'PUT') {
		if (!event.formData) {
			event.header = Object.assign({}, event.header, {
				"Content-Type": "application/json"
			})
		} else {
			option.body = null
			option.json = null
			// option.formData.file.value=fs.createReadStream(event.formData.file.value)
			option.formData.file.value=request(event.formData.file.value)
		}
	}
	return new Promise((resolve, reject) => {
		rp(option)
			.then((repos) => {
				resolve(repos)
			})
			.catch((err) => {
				// API call failed...
				reject(err)
			});
	})





};
