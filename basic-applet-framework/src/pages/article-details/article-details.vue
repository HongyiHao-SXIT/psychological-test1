<template>
	<view>
		<view style="padding:20rpx">
			<u-parse :content="details.content"></u-parse>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				details: {}
			}
		},
		onLoad(arg) {
			this.id = arg.id
			this.getArticleDetail()
		},
		methods: {
			getArticleDetail() {
				this.$store.dispatch('wxArticle/getArticleDetail', {
					id: this.id
				}).then((res) => {
					this.details = res || {}
				})
			}
		}
	}
</script>

<style>

</style>