<template>
	<view>
		<u-sticky bgColor="#fff">
			<u-tabs :list="tabList" @change="change"></u-tabs>
		</u-sticky>
		<view v-for="(item,index) in list" :key="index" class="card">
			<u-row>
				<u-col span="3">
					<view>
						<image style="width: 100rpx;height: 100rpx;" src="/static/health-education.png"></image>
					</view>
				</u-col>
				<u-col span="7">
					<view style="line-height: 50rpx;">
						<view style="font-size:30rpx;color:#999">
							用户：{{item.name}}
						</view>
						<view style="font-size:30rpx;color:#999">
							预约时间：{{item.applicationDate}}
						</view>
					</view>
				</u-col>
				<u-col span="2">
					<view style="font-size:24rpx;color:darkred;text-align: center;">
						{{item.statusName}}
					</view>
				</u-col>

			</u-row>
			<view>
				<u-row>
					<u-col offset="1" span="5">
						<view style="margin:10rpx 0rpx">
							<u-button v-if="item.status==='a'" @click="cancel(item)" size="mini" type="error"
								text="取消预约"></u-button>
							
						</view>

					</u-col>
					<u-col offset="1" span="5">
						<view style="margin:10rpx 0rpx">
							<u-button @click="toUserMindResultList(item)" size="mini" type="success"
								text="心理问卷"></u-button>
						</view>
					</u-col>
				</u-row>
				<u-row>
					<u-col offset="1" span="5">
						<u-button v-if="item.status==='a'" @click="finish(item)" size="mini" type="warning"
							text="完成预约"></u-button>
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
				tabList: [{
					name: '全部',
					value: ''
				}, {
					name: '已预约',
					value: 'a'
				}, {
					name: '已取消',
					value: 'b'
				}, {
					name: '已完成',
					value: 'c'
				}],
				status: '',
				list: []
			}
		},
		mounted() {
			this.getCounselorAppointmentList()
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		methods: {
			getCounselorAppointmentList() {
				this.$store.dispatch('wxAppointment/getCounselorAppointmentList', {
					status: this.status
				}).then((res) => {
					this.list = res || []
				})
			},
			change(e) {
				this.status = e.value
				this.getCounselorAppointmentList()
			},
			toUserMindResultList(item) {
				uni.navigateTo({
					url: '/pages/user-mind-result/user-mind-result?userId=' + item.userId
				})
			},
			cancel(item) {
				this.$store.dispatch('wxAppointment/saveOrUpdate', {
					id: item.id,
					status: 'b'
				}).then((res) => {
					this.$u.toast('取消成功')
					this.getCounselorAppointmentList()
				})
			},
			finish(item) {
				this.$store.dispatch('wxAppointment/saveOrUpdate', {
					id: item.id,
					status: 'c'
				}).then((res) => {
					this.$u.toast('完成成功')
					this.getCounselorAppointmentList()
				})
			},
		}
	}
</script>

<style scoped>
	.card {
		padding: 20rpx;
		margin: 20rpx;
		background-color: #fff;
		border-radius: 20rpx;
	}
</style>