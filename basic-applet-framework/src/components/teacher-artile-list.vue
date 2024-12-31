<template>
	<view style="padding-bottom: 180rpx;">
		<view v-for="(item,index) in list" :key="index" class="card">
			<u-row>
				<u-col span="5">
					<image style="height: 200rpx;width: 200rpx;" :src="baseUrl+'/files/'+item.imageUrl"></image>
				</u-col>
				<u-col span="5">
					<view>
						{{item.title}}
					</view>
				</u-col>
				<u-col span="2">
					<u-button @click="deleteById(item)" size="mini" type="error" text="删除"></u-button>
				</u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		name: "teacher-artile-list",
		data() {
			return {
				list: []
			};
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		mounted() {
			this.getCounselorArticles()
		},
		methods: {
			getCounselorArticles() {
				this.$store.dispatch('wxArticle/getCounselorArticles').then((res) => {
					this.list = res || []
				})
			},
			deleteById(item) {
				this.$store.dispatch('wxArticle/deleteById', {
					id: item.id
				}).then((res) => {
					this.$u.toast('删除成功')
					this.getCounselorArticles()
				})
			}
		}
	}
</script>

<style scoped>
	.card {
		background-color: #fff;
		padding: 20rpx;
		margin: 20rpx;
		border-radius: 20rpx;
	}
</style>