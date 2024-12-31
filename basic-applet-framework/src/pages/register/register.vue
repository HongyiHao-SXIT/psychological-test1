<template>
	<view style="padding:30rpx">
		<view style="font-size:40rpx;color: #419de6">
			嗨，你好
		</view>
		<view style="margin:30rpx 0rpx;color: #419de6">
			欢迎注册愈心园心理服务平台
		</view>
		<view style="color: #419de6;font-size:40rpx">
			新用户注册
		</view>
		<view>
			<u--form labelWidth="80" labelPosition="top" :model="model" :rules="rules" ref="uForm">
				<u-row>
					<u-col span="1">
						<image style="width: 40rpx;height: 40rpx;" src="/static/username.png"></image>
					</u-col>
					<u-col span="11">
						<u-form-item label="账号" prop="info.username" borderBottom ref="item1">
							<u--input placeholder="请输入账号" v-model="model.info.username" border="none">
							</u--input>
						</u-form-item>
					</u-col>
				</u-row>
				<u-row>
					<u-col span="1">
						<image style="width: 40rpx;height: 40rpx;" src="/static/password.png"></image>
					</u-col>
					<u-col span="11">
						<u-form-item label="密码" prop="info.password" borderBottom ref="item1">
							<u--input type="password" placeholder="请输入密码" v-model="model.info.password" border="none">
							</u--input>
						</u-form-item>
					</u-col>
				</u-row>


			</u--form>
		</view>
		<view style="margin:30rpx 50rpx">
			<u-button @click="register" type="primary" text="注册" shape="circle"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				model: {
					info: {
						phone: ''
					}
				},
				rules: {
					'userInfo.phone': {
						type: 'string',
						required: true,
						len: 11,
						message: '请填写手机号',
						trigger: ['blur', 'change']
					},
					'userInfo.password': {
						type: 'string',
						required: true,
						min: 6,
						message: '请填写至少6位密码',
						trigger: ['blur', 'change']
					},

				},
			}
		},
		methods: {
			register() {
				this.$refs.uForm.validate().then(res => {
					this.$store.dispatch('user/register', this.model.info).then((res) => {
						this.$u.toast('注册成功')
						setTimeout(() => {
							uni.redirectTo({
								url: '/pages/login/login'
							})
						}, 1000)
					})
				}).catch(errors => {})
			}
		}
	}
</script>

<style>

</style>
