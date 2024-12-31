<template>
	<view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		},
		onLoad(){
			// #ifdef H5
			let url=this.$baseUrl+"/wx/redirect/"+ new Buffer(this.$appId).toString('base64')+"/redirectMpLogin"
			window.location =url
			// #endif
			
		}
	}
</script>

<style>

</style>
