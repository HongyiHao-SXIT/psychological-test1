<template>
	<view>
		<my-index @changeIndex="tabClick" ref="myIndex" v-if="tabValue===0"></my-index>
		<consultant-list ref="consultantList" v-if="tabValue===1"></consultant-list>
		<psychological-evaluation-list ref="psychologicalEvaluationList"
			v-if="tabValue===2"></psychological-evaluation-list>
		<mental-health ref="mentalHealth" v-if="tabValue===3"></mental-health>
		<mine ref="myIndex" v-if="tabValue===4"></mine>
		<u-tabbar :value="tabValue" :fixed="true" :placeholder="false" :safeAreaInsetBottom="true">
			<u-tabbar-item text="首页" icon="home" @click="tabClick(0)"></u-tabbar-item>
			<u-tabbar-item text="心理咨询" icon="red-packet" @click="tabClick(1)"></u-tabbar-item>
			<u-tabbar-item text="心理测评" icon="red-packet" @click="tabClick(2)"></u-tabbar-item>
			<u-tabbar-item text="心理健康" icon="red-packet" @click="tabClick(3)"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account" @click="tabClick(4)"></u-tabbar-item>
		</u-tabbar>
	</view>
</template>

<script>
	import myIndex from "@/components/index.vue"
	import consultantList from "@/components/consultant-list.vue"
	import psychologicalEvaluationList from "@/components/psychological-evaluation-list.vue"
	import mentalHealth from "@/components/mental-health.vue"
	import mine from "@/components/mine.vue"
	export default {
		mixins: [],
		components: {
			myIndex,
			consultantList,
			psychologicalEvaluationList,
			mentalHealth,
			mine
		},
		data() {
			return {
				tabValue: 0,
			};
		},
		onLoad(arg) {
			// #ifdef H5
			let paraString = location.search;
			let wxH5Token = paraString.split('=')[1]
			uni.setStorageSync("accessToken", wxH5Token)
			this.$store.dispatch("user/GetInfo").then(res => {

			})
			// #endif



		},
		onPullDownRefresh() {},
		methods: {
			tabClick(value) {
				this.tabValue = value
				if (value === 0) {
					uni.setNavigationBarTitle({
						title: '首页'
					})
				}
				if (value === 1) {
					uni.setNavigationBarTitle({
						title: '心理咨询'
					})
				}
				if (value === 2) {
					uni.setNavigationBarTitle({
						title: '心理测评'
					})
				}
				if (value === 3) {
					uni.setNavigationBarTitle({
						title: '心理健康'
					})
				}


				if (value === 4) {
					uni.setNavigationBarTitle({
						title: '我的'
					})
				}
			}
		},
	};
</script>

<style lang="scss">
	@import '@/style/variable.scss';

	.ddd {
		color: $theme-color
	}
</style>