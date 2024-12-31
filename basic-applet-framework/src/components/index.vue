<template>
	<view style="padding: 20rpx;padding-bottom: 180rpx;">
		<view>
			<u-swiper indicator indicatorMode="dot" imgMode="widFill" height="200" :list="bannerList"></u-swiper>
		</view>
		<view style="margin:20rpx">
			<u-row>
				<u-col span="4">
					<view @click="emit(1)" class="card">
						<view style="text-align: center;line-height: 50rpx;">
							<view>
								<image style="width: 80rpx;height: 80rpx;" src="/static/appointment-consultation.png">
								</image>
							</view>
							<view style="font-size:30rpx;font-weight: bold;">
								预约咨询
							</view>
							<view style="font-size:26rpx;color:#999">
								倾诉情绪
							</view>
						</view>

					</view>
				</u-col>
				<u-col span="4">
					<view @click="emit(2)" class="card">
						<view style="text-align: center;line-height: 50rpx;">
							<view>
								<image style="width: 80rpx;height: 80rpx;" src="/static/psychological-counseling.png">
								</image>
							</view>
							<view style="font-size:30rpx;font-weight: bold;">
								心理测试
							</view>
							<view style="font-size:26rpx;color:#999">
								了解内心
							</view>
						</view>

					</view>
				</u-col>
				<u-col span="4">
					<view @click="emit(3)" class="card">
						<view style="text-align: center;line-height: 50rpx;">
							<view>
								<image style="width: 80rpx;height: 80rpx;" src="/static/health-education.png"></image>
							</view>
							<view style="font-size:30rpx;font-weight: bold;">
								心理健康
							</view>
							<view style="font-size:26rpx;color:#999">
								了解内心
							</view>
						</view>
					</view>
				</u-col>
			</u-row>
		</view>
		<view style="margin:20rpx 0rpx" class="hot-white-card">
			<view style="margin-bottom: 35rpx;">
				<u-row>
					<u-col span="10">
						<view style="font-size:30rpx;font-weight: bold;">
							热门心理健康
						</view>
					</u-col>
					<u-col span="2">
						<view @click="emit(3)" style="font-size:24rpx;color:#999">
							更多 >
						</view>
					</u-col>
				</u-row>
			</view>
			<view @click="toDetails(item)" v-for="(item,index) in articleList" :key="index" class="hot-card">
				<u-row>
					<u-col span="4">
						<view>
							<image style="height: 100rpx;width: 100rpx;border-radius: 20rpx;"
								:src="baseUrl+'/files/'+item.imageUrl">
							</image>
						</view>
					</u-col>
					<u-col span="8">
						<view style="">
							{{item.title}}
						</view>
					</u-col>
				</u-row>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				bannerList: [],
				articleList: []
			}
		},
		mounted() {
			this.getBannerList()
			this.getHotWxArticleList()
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		methods: {
			getBannerList() {
				this.$store.dispatch('banner/getAllList').then((res) => {
					this.bannerList = (res || []).map((item) => this.$baseUrl + '/files/' + item.imageUrl)
				})
			},
			emit(index) {
				this.$emit('changeIndex', index)
			},
			getHotWxArticleList() {
				this.$store.dispatch('wxArticle/getHotWxArticleList').then((res) => {
					this.articleList = res || []
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
		background-color: #fff;
		padding: 20rpx;
		margin: 0rpx 10rpx;
		border-radius: 20rpx;
	}

	.hot-white-card {
		padding: 20rpx;
		background-color: #fff;
		border-radius: 20rpx;
	}

	.hot-card {
		margin: 20rpx 0rpx
	}
</style>