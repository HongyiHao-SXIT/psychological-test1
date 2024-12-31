<template>
	<view style="padding:20rpx;background-color: #fff;">
		<view v-if="topicItemList.length>0">
			<view>
				{{(index+1)+'、'}}{{topicItemList[index].title}}
			</view>
			<view @click="choose(topicItemList[index].chooseANum)" class="choose-card">
				{{topicItemList[index].chooseA}}
			</view>
			<view @click="choose(topicItemList[index].chooseBNum)" class="choose-card">
				{{topicItemList[index].chooseB}}
			</view>
			<view @click="choose(topicItemList[index].chooseCNum)" class="choose-card">
				{{topicItemList[index].chooseC}}
			</view>
			<view @click="choose(topicItemList[index].chooseDNum)" class="choose-card">
				{{topicItemList[index].chooseD}}
			</view>
		</view>
	</view>
</template>

<script>
	import {
		setTimeout
	} from 'timers'
	export default {
		data() {
			return {
				mindId: '',
				topicItemList: [],
				index: 0,
				score: 0
			}
		},
		onLoad(arg) {
			this.mindId = arg.mindId
			this.getWxMindItemListByMindId()
		},
		methods: {
			choose(score) {
				if (this.index === this.topicItemList.length - 1) {
					// 已经到最后一题了
					this.$store.dispatch('wxUserMindRecord/saveWxUserMindRecord', {
						mindId: this.mindId,
						score: this.score
					}).then((res) => {
						this.$u.toast('评测成功')
						setTimeout(() => {
							uni.redirectTo({
								url: '/pages/test-result/test-result?resultId=' + res.id
							})
						}, 1000)
					})
					return
				}
				this.score = this.score + score
				this.index = this.index + 1
			},
			getWxMindItemListByMindId() {
				this.$store.dispatch('wxMindItem/getWxMindItemListByMindId', {
					mindId: this.mindId
				}).then((res) => {
					this.topicItemList = res || []
				})
			}
		}
	}
</script>

<style scoped>
	.choose-card {
		padding: 10rpx;
		background-color: #ccc;
		margin: 10rpx
	}
</style>