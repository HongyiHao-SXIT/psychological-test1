<template>
	<view>
		<psychological-teacher-index ref="psychologicalTeacherIndex" v-if="tabValue===0"></psychological-teacher-index>
		<psychological-reservation-list ref="psychologicalReservationList"
			v-if="tabValue===1"></psychological-reservation-list>

		<u-tabbar :value="tabValue" :fixed="true" :placeholder="false" :safeAreaInsetBottom="true">
			<u-tabbar-item text="心理科普" icon="home" @click="tabClick(0)"></u-tabbar-item>
			<u-tabbar-item text="我的预约" icon="account" @click="tabClick(1)"></u-tabbar-item>
		</u-tabbar>
	</view>
</template>

<script>
	import psychologicalReservationList from '@/components/psychological-reservation-list.vue'
	import psychologicalTeacherIndex from '@/components/psychological-teacher-index.vue'
	export default {
		components: {
			psychologicalReservationList,
			psychologicalTeacherIndex
		},
		data() {
			return {
				tabValue: 0,
			}
		},
		methods: {
			tabClick(value) {
				this.tabValue = value
				if (value === 0) {
					uni.setNavigationBarTitle({
						title: '心理科普'
					})
				}
				if (value === 1) {
					uni.setNavigationBarTitle({
						title: '我的预约'
					})
				}
			}
		}
	}
</script>

<style>

</style>