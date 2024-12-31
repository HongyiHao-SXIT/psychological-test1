<template>
	<view>
		<u-sticky bgColor="#fff">
			<u-tabs :list="tabList" @change="change"></u-tabs>
		</u-sticky>
		<view v-for="(item,index) in orderList" :key="index" class="card">
			<u-row>
				<u-col span="3">
					<view>
						<image style="width: 100rpx;height: 100rpx;" :src="baseUrl+'/files/'+item.wxCounselor.imageUrl">
						</image>
					</view>
				</u-col>
				<u-col span="7">
					<view style="line-height: 50rpx;">
						<view style="font-size:30rpx;color:#999">
							心理咨询师：{{item.wxCounselor.name}}
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
					<view style="margin-top:20rpx">
						<u-button @click="cancel(item)" v-if="item.status==='a'" size="mini" type="error"
							text="取消预约"></u-button>
					</view>
				</u-col>
			</u-row>
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
				orderList: [],
				status: ''
			}
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			}
		},
		onLoad() {
			this.getUserAppointmentList()
		},
		methods: {
			getUserAppointmentList() {
				this.$store.dispatch('wxAppointment/getUserAppointmentList', {
					status: this.status
				}).then((res) => {
					this.orderList = res || []   
				})
			},
			cancel(item) {
				this.$store.dispatch('wxAppointment/saveOrUpdate', {
					id: item.id,
					status: 'b'
				}).then((res) => {
					this.$u.toast('取消成功')
					this.getUserAppointmentList()
				})
			},
			change(e) {
				this.status = e.value
				this.getUserAppointmentList()
			}
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