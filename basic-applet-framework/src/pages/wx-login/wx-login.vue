<template>
	<view>
		<u-toast ref="uToast"></u-toast>
		<view class="avatar">
			<u-avatar size="120" :mp-avatar="true" src="../../static/un-login.png"></u-avatar>
		</view>

		<view class="login-button">
			<u-button :loading="loading" shape="circle" @click="login" type="primary" text="登录"></u-button>
		</view>

	</view>
</template>

<script>
	import request from '@/utils/request.js'
	export default {
		data() {
			return {
				loading: false
			}
		},
		methods: {
			login() {
				this.loading = true
				uni.getUserProfile({
					desc: '登录',
					success: (res) => {
						console.log(res);
						this.getOpenId(res.userInfo)
					},
					fail: (err) => {
						console.log(err);
						this.loading = false
					}
				})
			},
			getOpenId(userInfo) {
				let that = this
				let base64Appid = new Buffer(this.$appId).toString('base64')
				uni.login({
					provider: 'weixin',
					success: (loginRes) => {
						that.$store.dispatch('user/getOpenId', {
							base64Appid: base64Appid,
							code: loginRes.code
						}).then((res) => {
							userInfo.openId = res.openid
							that.saveInfo(userInfo)
						})
					}
				});
			},
			saveInfo(userInfo) {
				let base64Appid = new Buffer(this.$appId).toString('base64')
				this.$store.dispatch('user/wxLogin', {
					gender: userInfo.gender,
					nickName: userInfo.nickName,
					openId: new Buffer(userInfo.openId).toString('base64'),
					province: userInfo.province,
					avatarUrl: userInfo.avatarUrl,
					base64Appid: base64Appid
				}).then((res) => {
					uni.setStorageSync("accessToken", res)
					this.$store.dispatch("user/GetInfo").then(res => {
						uni.reLaunch({
							url: '/pages/choose-role/choose-role'
						})
					})
				}).catch((err) => {
					uni.showToast({
						icon: "none",
						title: "登录失败"
					})
				}).finally(() => {
					this.loading = false
				})
			},



		}
	}
</script>

<style scoped>
	.avatar {
		margin: 260rpx
	}

	.login-button {
		padding: 0rpx 100rpx
	}
</style>
