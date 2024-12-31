<template>
	<view>
		<view class="card">
			<u-row @click="toCommit">
				<u-col span="4">
					<view>
						<image style="height: 200rpx;width: 200rpx;" src="/static/teacher.png"></image>
					</view>
				</u-col>
				<u-col span="8">
					<view style="text-align: center;">
						心理咨询师
					</view>
				</u-col>
			</u-row>
		</view>
		<view class="card">
			<u-row @click="toIndex">
				<u-col span="4">
					<view>
						<image style="height: 200rpx;width: 200rpx;" src="/static/user-avatar.png"></image>
					</view>
				</u-col>
				<u-col span="8">
					<view style="text-align: center;">
						普通用户
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

			}
		},
		methods: {
			toIndex() {
				uni.redirectTo({
					url: '/pages/index/index'
				})
			},
			toCommit() {
				this.$store.dispatch('wxCounselor/isCounselor').then((res) => {
					if (res) {
						uni.redirectTo({
							url: '/pages/psychologist-index/psychologist-index'
						})
					} else {
						uni.navigateTo({
							url: '/pages/commit-psychology-auth/commit-psychology-auth'
						})
					}
				})



			}
		}
	}
</script>

<style scoped>
	.card {
		padding: 20rpx;
		margin: 20rpx;
		background-color: #fff;
		border-radius: 30rpx;
	}
</style>