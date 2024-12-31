<template>
	<view style="padding-bottom: 180rpx;">
		<view>
			<u-swiper indicator indicatorMode="dot" imgMode="widFill" v-if="details && details.bannerUrl" height="200"
				:list="details.bannerUrl.split(',').map((item)=>baseUrl+'/files/'+item)"></u-swiper>
		</view>
		<view class="card" style="margin-top:0rpx">
			<u-row>
				<u-col span="9">
					<view style="font-size:40rpx;font-weight: bold;">
						{{details.name}}
					</view>
				</u-col>
				<u-col span="3">
					<view style="font-size:24rpx">
						{{details.levelText}}
					</view>
				</u-col>
			</u-row>
			<view style="margin:10rpx 0rpx;font-size:24rpx">
				面询
			</view>
			<view style="margin:10rpx 0rpx">
				<u-line></u-line>
			</view>
			<view>
				<u-row>
					<u-col span="1">
						<view style="margin-top:10rpx">
							<image style="width: 40rpx;height: 40rpx" src="/static/authentication.png"></image>
						</view>
					</u-col>
					<u-col span="10">
						<view style="font-size:24rpx">
							咨询经验
						</view>
					</u-col>
				</u-row>
			</view>
			<view style="margin-top:20rpx">
				<u-row>
					<u-col span="6">
						<view style="line-height: 50rpx;">
							<view style="font-size:24rpx;color:#999;text-align: center;">
								从业时长
							</view>
							<view style="font-size:40rpx;text-align: center;">
								{{details.workTime}}年
							</view>
						</view>
					</u-col>
					<u-col span="6">
						<view style="line-height: 50rpx;">
							<view style="font-size:24rpx;color:#999;text-align: center;">
								咨询经验
							</view>
							<view style="font-size:40rpx;text-align: center;">
								{{details.consultTime}}+小时
							</view>
						</view>
					</u-col>
				</u-row>
			</view>
		</view>
		<view class="card">
			<view style="font-weight: bold;">
				自我介绍:
			</view>
			<view style="margin-top:20rpx;color:#999;font-size:24rpx">
				{{details.intro}}
			</view>
		</view>
		<view class="card">
			<view style="font-weight: bold;">
				擅长领域:
			</view>
			<view style="margin-top:20rpx;color:#999;font-size:24rpx">
				{{details.goodAt}}
			</view>
		</view>
		<view class="fixed-div">
			<u-row>
				<u-col span="3">
					<view @click="toIndex">
						<view style="text-align: center;">
							<image style="height: 60rpx;width: 60rpx" src="/static/home.png"></image>
						</view>
						<view style="text-align: center;color:#999;font-size:24rpx">
							首页
						</view>
					</view>
				</u-col>
				<u-col span="8">
					<view>
						<u-button @click="toChooseDate" text="立即预约"
							color="linear-gradient(to right, #00a3fc, #00d5ac)"></u-button>
					</view>
				</u-col>
			</u-row>
		</view>
		<uni-calendar ref="calendar" :insert="false" :lunar="true" @confirm="confirm" :selected="dateList" />
	</view>
</template>

<script>
	import uniCalendar from "@/components/uni-calendar/uni-calendar"
	export default {
		data() {
			return {
				list1: [
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				counselorId: '',
				details: {},
				dateList: []
			}
		},
		components: {
			uniCalendar
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		onLoad(arg) {
			this.counselorId = arg.counselorId
			this.getCounselorById()
		},
		methods: {
			confirm(e) {
				console.log(e)
				if (e.extraInfo.disable) {
					this.$u.toast('当前日期已被预约')
					return
				}
				uni.navigateTo({
					url: '/pages/reservation-submit/reservation-submit?counselorId=' + this.counselorId +
						'&date=' + e.fulldate + '&teacherName=' + this.details.name
				})
			},
			getCounselorById() {
				this.$store.dispatch('wxCounselor/getCounselorById', this.counselorId).then((res) => {
					this.details = res || {}
				})
			},
			toIndex() {
				uni.redirectTo({
					url: '/pages/index/index'
				})
			},
			toChooseDate() {
				// 获取医生已经被预约的时间列表
				this.$store.dispatch('wxAppointment/getDisableDateList', {
					counselorUserId: this.details.userId
				}).then((res) => {
					this.dateList = (res || []).map((item) => {
						return {
							date: item.applicationDate,
							info: '已预约',
							disable: true
						}
					})
					this.$refs.calendar.open();
				})
			}
		}
	}
</script>

<style scoped>
	.card {
		padding: 30rpx;
		background-color: #fff;
		border-radius: 20rpx;
		margin: 20rpx 0rpx
	}

	.fixed-div {
		position: fixed;
		bottom: 0;
		width: 100%;
		padding: 10rpx;
		background-color: #fff;
	}
</style>