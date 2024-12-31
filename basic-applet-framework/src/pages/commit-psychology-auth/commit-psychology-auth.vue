<template>
	<view>
		<view class="card">
			<u--form labelPosition="left" :model="model" :rules="rules" ref="uForm" labelWidth="130">
				<u-form-item label="头像" prop="info.imageUrl" borderBottom ref="item1">
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="1">
					</u-upload>
				</u-form-item>
				<u-form-item label="详情页轮播图" prop="info.bannerUrl" borderBottom ref="item2">
					<u-upload :fileList="fileList2" @afterRead="afterRead" @delete="deletePic" name="2" multiple
						:maxCount="5">
					</u-upload>
				</u-form-item>
				<u-form-item label="姓名" prop="info.name" borderBottom ref="item1">
					<u--input v-model="model.info.name" placeholder="请填写姓名" border="none"></u--input>
				</u-form-item>
				<u-form-item label="性别" prop="info.gender" borderBottom @click="showGender = true; hideKeyboard()"
					ref="item1">
					<u--input v-model="model.info.genderName" disabled disabledColor="#ffffff" placeholder="请选择性别"
						border="none"></u--input>
					<u-icon slot="right" name="arrow-right"></u-icon>
				</u-form-item>
				<u-form-item label="擅长" prop="info.goodAt" borderBottom ref="item1">
					<u--input v-model="model.info.goodAt" placeholder="请填写擅长" border="none"></u--input>
				</u-form-item>
				<u-form-item label="从业时间" prop="info.workTime" borderBottom ref="item1">
					<u--input v-model="model.info.workTime" border="none" placeholder="请填写从业时间"></u--input>
				</u-form-item>
				<u-form-item label="咨询时间(小时)" prop="info.consultTime" borderBottom ref="item1">
					<u--input type="number" v-model="model.info.consultTime" placeholder="请填写咨询时间"
						border="none"></u--input>
				</u-form-item>
				<u-form-item label="自我介绍" prop="info.intro" borderBottom ref="item1">
					<u--input v-model="model.info.intro" border="none" placeholder="请填写自我介绍"></u--input>
				</u-form-item>
				<u-form-item label="咨询等级" prop="info.levelText" borderBottom ref="item1">
					<u--input v-model="model.info.levelText" border="none" placeholder="请填写咨询等级"></u--input>
				</u-form-item>
			</u--form>
			<view style="margin:30rpx 20rpx">
				<u-button @click="toPsychologist" type="primary" text="提交资质"></u-button>
			</view>
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
				genderList: [],
				showGender: false,
				model: {
					info: {
						gender: '',
						genderName: '',
						imageUrl: '',
						bannerUrl: '',
						name: '',
						gender: '',
						goodAt: '',
						workTime: '',
						consultTime: '',
						intro: '',
						levelText: ''
					}
				},
				fileList1: [],
				fileList2: [],
				rules: {
					'info.imageUrl': {
						type: 'string',
						required: true,
						message: '请上传头像',
						trigger: ['blur', 'change']
					},
					'info.gender': {
						type: 'string',
						required: true,
						message: '请选择性别',
						trigger: ['blur', 'change']
					},
					'info.bannerUrl': {
						type: 'string',
						required: true,
						message: '请上传轮播图',
						trigger: ['blur', 'change']
					},
					'info.name': {
						type: 'string',
						required: true,
						message: '请填写姓名',
						trigger: ['blur', 'change']
					},
					'info.goodAt': {
						type: 'string',
						required: true,
						message: '请填写填写擅长',
						trigger: ['blur', 'change']
					},
					'info.workTime': {
						type: 'string',
						required: true,
						message: '请填写填写从业时间',
						trigger: ['blur', 'change']
					},
					'info.consultTime': {
						type: 'string',
						required: true,
						message: '请填写填写咨询时长',
						trigger: ['blur', 'change']
					},
					'info.intro': {
						type: 'string',
						required: true,
						message: '请填写填写自我介绍',
						trigger: ['blur', 'change']
					},
					'info.levelText': {
						type: 'string',
						required: true,
						message: '请填写填写咨询等级',
						trigger: ['blur', 'change']
					}
				},
			}
		},
		mounted() {
			this.getGenderList()
		},
		methods: {
			toPsychologist() {
				this.model.info.imageUrl = this.fileList1.map((item) => item.url).join(',')
				this.model.info.bannerUrl = this.fileList2.map((item) => item.url).join(',')
				this.$refs.uForm.validate().then((info) => {
					this.$store.dispatch('wxCounselor/commitCounselor', this.model.info).then((res) => {
						this.$u.toast('申请成功，请耐心等待审核')
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 1000)
					})
				}).catch(errors => {

				})




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
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: 'uploading'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					this.$store.dispatch("upload/upload", {
						path: url
					}).then((res) => {
						resolve(res)
					}).catch(err => {
						reject(err)
					})
				})
			},
		}
	}
</script>

<style scoped>
	.card {
		background-color: #fff;
		padding: 20rpx
	}
</style>