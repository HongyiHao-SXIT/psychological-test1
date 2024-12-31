<template>
	<view>
		<view class="card">
			<view style="text-align: center;">
				{{introItem.title}}
			</view>

			<view style="margin:20rpx 0rpx">
				<u-parse :content="introItem.content"></u-parse>
			</view>

			<view style="margin-top:30rpx">
				<u-row>
					<u-col span="3">
						<view style="font-size:28rpx;font-weight: bold;">
							题目数量:
						</view>
					</u-col>
					<u-col span="9">
						<view style="font-size:28rpx">
							{{introItem.itemNum}}题
						</view>
					</u-col>
				</u-row>
			</view>
			<view style="margin-top:20rpx">
				<u-row>
					<u-col span="3">
						<view style="font-size:28rpx;font-weight: bold;">
							说明:
						</view>
					</u-col>
					<u-col span="9">
						<view style="font-size:28rpx">
							{{introItem.intro}}
						</view>
					</u-col>
				</u-row>
			</view>
			<view style="margin:50rpx">
				<u-button @click="toStartTest" size="mini" type="primary" text="开始测试"></u-button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				mindId: '',
				introItem: {}
			}
		},
		onLoad(arg) {
			this.mindId = arg.mindId
			this.getMindIntro()
		},
		methods: {
			toStartTest() {
				if (!this.introItem.itemNum) {
					this.$u.toast('题目数量为0，请选择其他题')
					return
				}
				uni.navigateTo({
					url: '/pages/start-test/start-test?mindId=' + this.mindId
				})
			},
			getMindIntro() {
				this.$store.dispatch('wxMind/getMindIntro', {
					id: this.mindId
				}).then((res) => {
					this.introItem = res || {}
				})
			}
		}
	}
</script>

<style scoped>
	.card {
		padding: 20rpx;
		background-color: #fff;
		border-radius: 20rpx;
		margin: 10rpx
	}
</style>