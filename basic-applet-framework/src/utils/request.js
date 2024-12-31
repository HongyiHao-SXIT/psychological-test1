import setting from '../setting';

let devUrl = setting.baseUrl;
let urlPrex = ""

const isDev = process.env.NODE_ENV === 'development';

// #ifdef H5
urlPrex = isDev ? '' : "/api"
// #endif

const baseUrl = devUrl;

const POST = 'POST';
const UPLOAD = 'UPLOAD';
const SUCCESS_CODES = 200;
const RefreshCode = 506;
const LogoutCode = 503;
const NEED_LOGIN = 401;
const ERR_CODE = 500;
const USE_UNICLOUD = false;

const getHeader = () => {
	const accessToken = 'Bearer ' + uni.getStorageSync('accessToken') || '';
	return {
		Authorization: accessToken,
	};
};
const checkLogin = () => {
	uni.setStorageSync("accessToken", null)
	uni.setStorageSync("userInfo", null)
	// 未授权，授权码过去，直接提示登录
	uni.showModal({
		title: '登录过期',
		content: '请前往登录',
		showCancel: false, // 是否显示取消按钮，默认为 true
		confirmText: "确认", // 确认按钮的文字
		success: (res) => {
			if (res.confirm) {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			}
		}
	})
}
/**
 *
 * @param {string} method 请求方法
 * @param {string} url api地址
 * @param {string} data 入参
 */

const request = (url, data, method = POST) =>
	new Promise((resolve, reject) => {
		if (method !== UPLOAD) {
			if (USE_UNICLOUD) {
				// 云函数绕开域名验证模式
				uniCloud.callFunction({
					name: 'request-forwarding',
					data: {
						url: `${baseUrl}/${urlPrex}${url}`,
						method: method,
						data: data,
						header: Object.assign({}, getHeader())
					}
				}).then((res) => {
					// 请求成功
					const data = res.result;
					if (data.code === SUCCESS_CODES) {
						resolve(data.data);
					} else {
						// 其他异常
						console.log(data);
						if (data.code === RefreshCode) {} else if (data.code === LogoutCode) {
							reject(data);
							return;
							// uni.navigateTo({ url: '/pages/login/index' })
						} else if (data.code === NEED_LOGIN) {
							checkLogin()
						} else if (data.code === ERR_CODE) {
							reject(data);
						} else {
							uni.showToast({
								title: data.message,
								icon: 'none'
							});
						}
						uni.showToast({
							title: data.message,
							icon: 'none'
						});
						reject(data);
					}
				}).catch((err) => {
					console.log(222, err)
					// 请求失败
					reject(new Error('请检查网络'));
				})
			} else {
				// 传统模式
				uni.request({
					url: `${baseUrl}/${urlPrex}${url}`,
					method,
					data,
					header: getHeader(),
					success(res) {
						// 请求成功
						const data = res.data;
						if (data.code === SUCCESS_CODES) {
							resolve(data.data);
						} else {
							// 其他异常
							console.log(data);
							if (data.code === RefreshCode) {} else if (data.code === LogoutCode) {
								reject(data);
								return;
								// uni.navigateTo({ url: '/pages/login/index' })
							} else if (data.code === NEED_LOGIN) {
								checkLogin()
							} else {
								uni.showToast({
									title: data.message,
									icon: 'none'
								});
							}
							uni.showToast({
								title: data.message,
								icon: 'none'
							});
							reject(data);
						}
					},

					fail(err) {
						// 请求失败
						reject(new Error('请检查网络'));
					},
				});
			}
		} else {
			if (USE_UNICLOUD) {
				// 云函数上传文件
				uniCloud.uploadFile({
					filePath: data.path, // 必传，要上传图片的临时路径
					// cloudPath: Date.now() + "", // 我最开始这样写的，这样写就会导致没有后缀名
					// cloudPath: Date.now() + ".png", // 这样写所有上传的图片后缀名都会变成png
					cloudPath: Date.now() + "-" + Math.round(Math.random() * 10000) +
					".png", // 这样写上传的图片后缀名就是原本的后缀名（也就是我最终想实现的效果）
					success: (res) => {
						let fileUrl = res.fileID
						uniCloud.callFunction({
							name: 'request-forwarding',
							data: {
								url: `${baseUrl}/${urlPrex}${url}`,
								method: "POST",
								data: data,
								formData: {
									name: 'file',
									file: {
										value: fileUrl,
										options: {}
									}
								},
								header: Object.assign({}, getHeader())
							}
						}).then((resObj) => {
							const res = JSON.parse(resObj.result);
							if (res.code === SUCCESS_CODES) {
								resolve(res.data);
							} else if (res.code === NEED_LOGIN) {
								checkLogin()
							} else {
								// 其他异常
								reject(res.msg);
							}
						}).catch((err) => {
							reject(new Error('上传失败:' + JSON.stringify(err)));
						})
					},
					fail(err) {
						console.log(err)
					},
					complete() {
						uni.hideLoading()
					}
				})







			} else {
				// 传统模式上传文件
				uni.uploadFile({
					// file: data,
					filePath: data.path,
					name: 'file',
					url: `${baseUrl}/${urlPrex}${url}`,
					header: getHeader(method, url, true),
					success: (resObj) => {
						const res = JSON.parse(resObj.data);
						if (res.code === SUCCESS_CODES) {
							resolve(res.data);
						} else if (res.code === NEED_LOGIN) {
							checkLogin()
						} else {
							// 其他异常
							reject(res.msg);
						}
					},
					fail: (err) => {
						reject(new Error('上传失败:' + JSON.stringify(err)));
					},
				});
			}

		}
	});

export default request;
