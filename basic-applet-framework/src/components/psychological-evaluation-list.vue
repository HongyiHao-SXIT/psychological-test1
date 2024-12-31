<template>
	<view style="padding-bottom: 180rpx;">
		<view @click="toTestIntro(item)" v-for="(item,index) in list" :key="index" class="card">
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
		name: "psychological-evaluation-list",
		data() {
			return {
				list: []
			};
		},
		mounted() {
			this.getAllList()
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		methods: {
			toTestIntro(item) {
				uni.navigateTo({
					url: '/pages/test-intro/test-intro?mindId=' + item.id
				})
			},
			getAllList() {
				this.$store.dispatch('wxMind/getAllList').then((res) => {
					this.list = res || []
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