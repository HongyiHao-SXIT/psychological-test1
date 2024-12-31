<template>
	<view class="body" :style="{backgroundImage:`url(${baseUrl+'/files/login.png'})`}">
		<u-toast ref="uToast"></u-toast>
		<view class="title">
			愈心园心理服务平台
		</view>
		<view class="form">
			<u--form labelPosition="left" :model="form" :rules="rules" ref="form1">
				<u-form-item label="账号" prop="loginInfo.username" borderBottom labelWidth="60" ref="item1">
					<u--input v-model="form.loginInfo.username" placeholder="请输入账号" border="none"></u--input>
				</u-form-item>
				<u-form-item label="密码" prop="loginInfo.password" borderBottom labelWidth="60" ref="item1">
					<u--input v-model="form.loginInfo.password" placeholder="请输入密码" border="none"></u--input>
				</u-form-item>
			</u--form>
		</view>
		<view style="padding:0rpx 80rpx">
			<u-button type="primary" text="登录" @click="login"></u-button>
		</view>
		<view style="float: right;padding-right:100rpx;padding-top:10rpx">
			<u--text @click="toRegister" type="primary" text="注册"></u--text>
		</view>
		<view style="margin-top:130rpx;text-align: center;">
			使用以下三方系统登录
		</view>
		<view style="margin-top:50rpx;text-align: center;">
			<image @click="toWxLogin" style="width: 60rpx;height: 60rpx" src="/static/weixin.png"></image>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				loading: false,
				form: {
					loginInfo: {
						username: "",
						password: '',
					},


				},
				rules: {
					'loginInfo.username': {
						type: 'string',
						required: true,
						message: '请输入账号',
						trigger: ['blur', 'change']
					},
					'loginInfo.password': {
						type: 'string',
						required: true,
						message: '请填写密码',
						trigger: ['blur', 'change']
					}
				},
			}
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		methods: {
			toRegister() {
				uni.navigateTo({
					url: "/pages/register/register"
				})
			},
			login() {
				this.$refs.form1.validate().then((res) => {
					this.$refs.form1.validate().then((info) => {
						uni.showLoading({
							title: '登录中',
							mask: true
						});
						this.$store.dispatch('user/login', this.form.loginInfo).then((res) => {
							uni.setStorageSync('accessToken', res || '')
							uni.$u.toast('登录成功!')

							setTimeout(() => {
								this.$store.dispatch("user/GetInfo").then(res => {
									uni.redirectTo({
										url: '/pages/choose-role/choose-role'
									})
								})

							}, 1000)
						})
					}).catch(errors => {

					})
				}).catch(errors => {
					// uni.$u.toast('校验失败')
				})
			},
			toWxLogin() {
				uni.navigateTo({
					url: '/pages/wx-login/wx-login'
				})
			}

		}
	}
</script>

<style scoped>
	.form {
		padding: 80rpx
	}

	.title {
		text-align: center;
		margin-top: 150rpx;
		font-size: 60rpx
	}

	.body {
		background-size: 100% 100%;
		height: calc(100vh);
	}
</style>