<template>
	<view style="padding-bottom: 100rpx;">
		<view @click="toDetails(item)" v-for="(item,index) in list" :key="index" class="card">
			<u-row>
				<u-col span="3">
					<image style="width: 90%;height: 150rpx;" :src="baseUrl+'/files/'+item.imageUrl"></image>
				</u-col>
				<u-col span="7">
					<view style="line-height: 50rpx;">
						<view style="font-size:36rpx;font-weight: bold;">
							{{item.name}}
						</view>
						<view style="font-size:24rpx">
							{{item.levelText}}
						</view>
						<view style="font-size:24rpx;color:#999">
							擅长：{{item.goodAt}}
						</view>
						<view style="font-size:24rpx;color:#999">
							从业{{item.workTime}}年·咨询经验{{item.consultTime}}+小时
						</view>
					</view>
				</u-col>
				<u-col span="2">
					<view>
						<u--text size="13" type="error" text="可预约"></u--text>
					</view>
				</u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		name: "consultant-list",
		data() {
			return {
				list: []
			};
		},
		mounted() {
			this.getWxCounselorList()
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		methods: {
			toDetails(item) {
				uni.navigateTo({
					url: '/pages/teacher-details/teacher-details?counselorId=' + item.id
				})
			},
			getWxCounselorList() {
				this.$store.dispatch('wxCounselor/getWxCounselorList').then((res) => {
					this.list = res || []
				})
			}
		}
	}
</script>

<style scoped>
	.card {
		padding: 20rpx;
		background-color: #fff;
		margin: 20rpx;
	}
</style>