<template>
	<view>
		<view class="card">
			<view style="text-align:center">
				测评结果
			</view>
			<view style="margin:30rpx 0rpx;color:red;text-align: center;">
				{{details.score}}
			</view>
			<view style="text-align:center;font-size:30rpx">
				可能有{{details.result}}
			</view>
			<view style="margin-top:30rpx">
				<view style="font-size:30rpx;font-weight: bold;">
					说明
				</view>
				<view style="font-size:30rpx">
					{{details.wxMindIntro}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				resultId: '',
				details: {}
			}
		},
		onLoad(arg) {
			this.resultId = arg.resultId
			this.getById()
		},
		methods: {
			getById() {
				this.$store.dispatch('wxUserMindRecord/getById', this.resultId).then((res) => {
					this.details = res || {}
				})
			}
		}
	}
</script>

<style scoped>
	.card {
		padding: 30rpx;
		margin: 10rpx;
		background-color: #fff;
		border-radius: 10rpx;
	}
</style>