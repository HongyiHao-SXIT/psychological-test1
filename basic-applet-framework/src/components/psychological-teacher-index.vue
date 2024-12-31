<template>
	<view>
		<u-sticky bgColor="#fff">
			<u-subsection :list="list" :current="cureentIndex" @change="change"></u-subsection>
		</u-sticky>
		<view v-if="cureentIndex===0" style="background-color: #fff;">
			<u--form labelPosition="left" :model="model" ref="uForm" labelWidth="100" :rules="rules">
				<u-form-item label="科普封面" prop="info.imageUrl" borderBottom ref="item1">
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="1">
					</u-upload>
				</u-form-item>
				<u-form-item label="科普标题" prop="info.title" borderBottom ref="item1">
					<u--input placeholder="请输入科普标题" v-model="model.info.title" border="none"></u--input>
				</u-form-item>
			</u--form>
			<jinEdit height="52vh" placeholder="请输入科普内容" @editOk="editOk" :uploadFileUrl="baseUrl+'/upload/file/upload'"
				:header="header">
			</jinEdit>
		</view>

		<teacher-artile-list ref="teacherArtileList" v-if="cureentIndex===1"></teacher-artile-list>
	</view>
</template>

<script>
	import jinEdit from '@/components/jin-edit/jin-edit.vue';
	import teacherArtileList from "@/components/teacher-artile-list.vue"
	export default {
		name: "psychological-publish",
		components: {
			jinEdit,
			teacherArtileList
		},
		data() {
			return {
				list: ['发布', '已发布'],
				cureentIndex: 0,
				model: {
					info: {
						title: '',
						imageUrl: ''
					}
				},
				fileList1: [],
				rules: {
					'info.title': {
						type: 'string',
						required: true,
						message: '请填写文章标题',
						trigger: ['blur', 'change']
					},
					'info.imageUrl': {
						type: 'string',
						required: true,
						message: '请上传文章封面',
						trigger: ['blur', 'change']
					}
				},
			};
		},
		computed: {
			baseUrl() {
				return this.$baseUrl
			},
			header() {
				return {
					Authorization: 'Bearer ' + uni.getStorageSync('accessToken') || ''
				}
			}
		},
		methods: {
			change(e) {
				this.cureentIndex = e
			},
			editOk(e) {
				this.model.info.imageUrl = this.fileList1.map((item) => item.url).join(',')
				if (e.html === '<p><br></p>') {
					this.$u.toast('文章内容不能为空')
					return
				}
				this.$refs.uForm.validate().then((info) => {
					this.$store.dispatch('wxArticle/commitWxArticle', {
						imageUrl: this.model.info.imageUrl,
						title: this.model.info.title,
						content: e.html
					}).then((res) => {
						this.$u.toast('发布成功')
						this.model.info.imageUrl = ''
						this.fileList1 = []
						this.model.info.title = ''
						setTimeout(() => {
							this.cureentIndex = 1
						}, 1000)
					})
				}).catch(errors => {})
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

<style>

</style>