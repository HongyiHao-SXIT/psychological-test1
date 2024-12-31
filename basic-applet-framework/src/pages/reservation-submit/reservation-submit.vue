<template>
	<view class="body">
		<u--form labelPosition="left" :model="model" :rules="rules" ref="uForm" labelWidth="100">
			<u-form-item label="姓名" prop="info.name" borderBottom ref="item1">
				<u--input placeholder="请输入姓名" v-model="model.info.name" border="none"></u--input>
			</u-form-item>
			<u-form-item label="手机号" prop="info.phone" borderBottom ref="item1">
				<u--input placeholder="请输入手机号" v-model="model.info.phone" border="none"></u--input>
			</u-form-item>
			<u-form-item label="性别" prop="info.gender" borderBottom @click="showGender = true; hideKeyboard()"
				ref="item1">
				<u--input v-model="model.info.genderName" disabled disabledColor="#ffffff" placeholder="请选择性别"
					border="none"></u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>
			<u-form-item label="预约日期" prop="info.applicationDate" borderBottom ref="item1">
				<u--input v-model="model.info.applicationDate" disabled disabledColor="#ffffff"
					border="none"></u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>
			<u-form-item label="预约老师" prop="info.teacherName" borderBottom ref="item1">
				<u--input v-model="model.info.teacherName" disabled disabledColor="#ffffff" border="none"></u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>
		</u--form>
		<view style="margin:30rpx 20rpx">
			<u-button @click="toResult" type="primary" text="确定预约"></u-button>
		</view>
		<u-action-sheet :show="showGender" :actions="genderList" title="请选择性别" @close="showGender = false"
			@select="genderSelect">
		</u-action-sheet>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				model: {
					info: {
						name: '',
						phone: '',
						gender: '',
						genderName: '',
						applicationDate: '',
						teacherName: '',
						counselorId: ''

					}
				},
				rules: {
					'info.name': {
						type: 'string',
						required: true,
						message: '请填写姓名',
						trigger: ['blur', 'change']
					},
					'info.phone': {
						type: 'string',
						required: true,
						message: '请填写手机号',
						trigger: ['blur', 'change']
					},
					'info.gender': {
						type: 'string',
						required: true,
						message: '请选择性别',
						trigger: ['blur', 'change']
					}
				},
				genderList: [],
				showGender: false,
			}
		},
		onLoad(arg) {
			this.model.info.teacherName = arg.teacherName
			this.model.info.counselorId = arg.counselorId
			this.model.info.applicationDate = arg.date
			this.getGenderList()
		},
		methods: {
			toResult() {
				this.$refs.uForm.validate().then((info) => {
					// 提交申请
					this.$store.dispatch('wxAppointment/commitAppointment', this.model.info).then((res) => {
						this.$u.toast('申请成功')
						setTimeout(() => {
							uni.redirectTo({
								url: '/pages/reservation-result/reservation-result'
							})
						}, 1000)
					})

				}).catch(errors => {})


			},
			getGenderList() {
				this.$store.dispatch('commonEnum/getEnum', 'com.macro.mall.tiny.modules.wx.enumeration.GenderEnum').then((
					res) => {
					this.genderList = (res || []).map((item) => {
						return {
							name: item.label,
							value: item.value
						}
					})
				})
			},
			genderSelect(e) {
				this.model.info.gender = e.value
				this.model.info.genderName = e.name
			},
		}
	}
</script>

<style scoped>
	.body {
		background-color: #fff;
		padding: 20rpx;

	}
</style>