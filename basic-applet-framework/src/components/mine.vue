<template>
	<view class="body">
		<view class="login-header back-white">
			<u-row customStyle="margin-bottom: 10px">
				<u-col span="2">
					<view>
						<u-avatar v-if="!userInfo.avatarUrl" src="../static/un-login.png"></u-avatar>
						<u-avatar v-else :src="userInfo.avatarUrl.indexOf('http') > -1
            ?userInfo.avatarUrl: baseUrl+'/files/'+userInfo.avatarUrl"></u-avatar>
					</view>
				</u-col>
				<u-col span="8">
					<view class="">
						<u-row>
							<u-col span="6">
								<view @click="toLogin">
									<span v-if="!userInfo.avatarUrl">未登录</span>
									<span v-else>{{userInfo.nickName || userInfo.username}}</span>
								</view>
							</u-col>

						</u-row>
					</view>
				</u-col>
				<u-col offset="1" span="1">
					<view class="">
						<u-icon name="arrow-right"></u-icon>
					</view>
				</u-col>
			</u-row>
		</view>

		<view v-if="userInfo.avatarUrl" @click="toEditInfo" class="back-white card">
			<u-row customStyle="margin-bottom: 10px">
				<u-col span="1">
					<view style="margin-top:3px">
						<u-icon size="25px" name="chat-fill"></u-icon>
					</view>
				</u-col>
				<u-col span="6">
					<view>
						<span>完善信息</span>
					</view>
				</u-col>
				<u-col offset="4" span="1">
					<view>
						<u-icon name="arrow-right"></u-icon>
					</view>
				</u-col>
			</u-row>
		</view>
		<view v-if="userInfo.avatarUrl" @click="toUserReservation" class="back-white card">
			<u-row customStyle="margin-bottom: 10px">
				<u-col span="1">
					<view style="margin-top:3px">
						<u-icon size="25px" name="chat-fill"></u-icon>
					</view>
				</u-col>
				<u-col span="6">
					<view>
						<span>我的预约</span>
					</view>
				</u-col>
				<u-col offset="4" span="1">
					<view>
						<u-icon name="arrow-right"></u-icon>
					</view>
				</u-col>
			</u-row>
		</view>

		<view v-if="userInfo.avatarUrl" @click="toLogin" class="back-white card">
			<u-row customStyle="margin-bottom: 10px">
				<u-col span="1">
					<view style="margin-top:3px">
						<u-icon size="25px" name="chat-fill"></u-icon>
					</view>
				</u-col>
				<u-col span="6">
					<view>
						<span>退出登录</span>
					</view>
				</u-col>
				<u-col offset="4" span="1">
					<view>
						<u-icon name="arrow-right"></u-icon>
					</view>
				</u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				exitShow: false,
				loginShow: false
			}
		},
		components: {},
		computed: {
			userInfo() {
				return this.$store.state.user.userInfo
			},
			baseUrl() {
				return this.$baseUrl
			}
		},
		mounted() {

		},
		methods: {
			toLogin() {
				uni.navigateTo({
					url: '/pages/login/login'
				})
			},
			toEditInfo() {
				uni.navigateTo({
					url: '/pages/edit-user-info/edit-user-info'
				})
			},
			toUserReservation() {
				uni.navigateTo({
					url: '/pages/user-reservation-list/user-reservation-list'
				})
			}
		}
	}
</script>

<style scoped>
	.body {
		background-color: #f5f5f5;
		height: calc(100vh)
	}

	.back-white {
		background-color: #fff;
	}

	.login-header {
		padding: 30rpx;
	}

	.card {
		margin: 10rpx 0rpx;
		padding: 20rpx
	}
</style>