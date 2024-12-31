<template>
	<view>
		<view style="text-align: center;margin:100rpx 0rpx">
			<image style="width: 200rpx;height: 200rpx;" src="/static/success.png"></image>
			<view style="margin:10rpx 0rpx;font-size:40rpx">
				预约成功
			</view>
			<view style="margin:20rpx 40rpx">
				<u-button @click="toUserReservationList" type="primary" text="前往预约列表"></u-button>
			</view>
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
			toUserReservationList() {
				uni.redirectTo({
					url: '/pages/user-reservation-list/user-reservation-list'
				})
			}
		}
	}
</script>

<style>

</style>