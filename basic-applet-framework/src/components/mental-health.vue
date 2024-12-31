<template>
	<view style="padding-bottom: 180rpx;">
		<view @click="toDetails(item)" v-for="(item,index) in list" :key="index" class="card">
			<u-row>
				<u-col span="9">
					<view style="font-size:25rpx">
						{{item.title}}
					</view>
				</u-col>
				<u-col span="3">
					<view>
						<image style="width: 150rpx;height: 150rpx;" :src="baseUrl+'/files/'+item.imageUrl"></image>
					</view>
				</u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		name: "mental-health",
		data() {
			return {
				list: []
			};
		},
		mounted() {
			this.getAllList()
		},
		computed:{
			baseUrl(){
				return this.$baseUrl
			}
		},
		methods: {
			getAllList() {
				this.$store.dispatch('wxArticle/getAllList').then((res) => {
					this.list = res || []
				})
			},
			toDetails(item) {
				uni.navigateTo({
					url: '/pages/article-details/article-details?id=' + item.id  
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
	}
</style>