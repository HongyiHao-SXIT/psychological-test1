<template>
	<view>
		<view class="card">
			<button type="default" class="btn" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
				<text>获取微信头像</text>
			</button>
			<u--form labelWidth="80" labelPosition="left" :model="model" :rules="rules" ref="uForm">
				<u-form-item label="昵称" prop="userInfo.nickName" borderBottom ref="item1">
					<u--input placeholder="请输入昵称" v-model="model.userInfo.nickName" border="none"></u--input>
				</u-form-item>
				<u-form-item label="头像" prop="userInfo.avatarUrl" borderBottom ref="item1">
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="1">
					</u-upload>
				</u-form-item>
			</u--form>
		</view>
		<view style="margin:10rpx 40rpx">
			<u-button @click="commitEdit" text="确定" type="primary"></u-button>
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				model: {
					userInfo: {
						nickName: '',
						avatarUrl: '',
					}
				},
				fileList1: [],
				rules: {
					'userInfo.nickName': {
						type: 'string',
						required: true,
						message: '请填写昵称',
						trigger: ['blur', 'change']
					},
					'userInfo.avatarUrl': {
						type: 'string',
						required: true,
						message: '请上传头像',
						trigger: ['blur', 'change']
					},
					
				},
			}
		},
		onShow() {


		},
		mounted() {
			this.model.userInfo.nickName = this.userInfo.nickName
			this.model.userInfo.avatarUrl = this.userInfo.avatarUrl
			this.fileList1 = []
			this.fileList1.push(this.model.userInfo.avatarUrl.indexOf('http') > -1 ? {
				url: this.model.userInfo.avatarUrl
			} : {
				url: this.$baseUrl + '/files/' + this.model.userInfo.avatarUrl
			})
		},
		computed: {
			userInfo() {
				return this.$store.state.user.userInfo
			}
		},
		methods: {
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: 'uploading'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					this.$store.dispatch("upload/upload", {
						path: url
					}).then((res) => {
						resolve(res)
					}).catch(err => {
						reject(err)
					})
				})
			},
			commitEdit() {
				this.model.userInfo.avatarUrl = this.fileList1.length > 0 ? this.fileList1[0].url : ''
				console.log(this.model.userInfo)
				this.$refs.uForm.validate().then(res => {
					this.$store.dispatch('user/editUserInfo', this.model.userInfo).then((res) => {
						this.$refs.uToast.show({
							type: 'success',
							title: '成功',
							message: "修改成功",
							iconUrl: 'https://cdn.uviewui.com/uview/demo/toast/success.png',
						})
						this.$store.dispatch("user/GetInfo").then(res => {

						})
					})
				}).catch(errors => {})
			},
			async onChooseAvatar(e) {
				console.log(e)
				const result = await this.uploadFilePromise(e.detail.avatarUrl)
				console.log(33333,result)
				this.fileList1=[{
					url:this.$baseUrl + '/files/' +result
				}]
			}
		}
	}
</script>

<style scoped lang="scss">
	.card {
		padding: 30rpx;
		background-color: #fff;
		border-radius: 30rpx;
	}
</style>
