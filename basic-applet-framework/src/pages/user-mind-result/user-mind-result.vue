<template>
	<view>
		<view @click="toTestResult(item)" v-for="(item,index) in list" :key="index" class="card">
			<view style="font-size:30rpx">
				{{item.title}}
			</view>
			<view style="font-size:24rpx:color:#999;text-align: right;padding-right: 30rpx;">
				测试时间：{{item.createdDatetime}}
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userId: '',
				list:[]
			}
		},
		onLoad(arg) {
			this.userId = arg.userId
			this.getMindRecordByUserId()
		},
		methods: {
			getMindRecordByUserId() {
				this.$store.dispatch('wxUserMindRecord/getMindRecordByUserId', {
					userId: this.userId
				}).then((res) => {
					this.list = res || []
				})
			},
			toTestResult(item) {
				uni.navigateTo({
					url: '/pages/test-result/test-result?resultId=' + item.id
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
		border-radius: 20rpx;
	}
</style>